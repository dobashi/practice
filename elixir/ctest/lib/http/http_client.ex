defmodule Http.HttpClient do
  alias Http.HttpClient
  alias Http.HttpClientLogger
  alias Http.MapConverter

  defstruct [:host, :base_url, :headers, :response, :data, :response_origin]

  @ua_mac_safari "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36"

  def setup(host, base_url \\ "") do
    %HttpClient{host: host, base_url: base_url, headers: init_headers(host), response: %{}}
  end

  defp init_headers(host) do
    %{"User-Agent" => @ua_mac_safari}
    |> Map.merge(content_headers())
    |> Map.merge(host_headers(host))
    |> Map.merge(secure_browser_headers())
  end

  defp content_headers() do
    %{
      "accept" => "application/json",
      "Accept" => "*/*",
      # "Accept-Encoding" => "gzip, deflate, br",
      "Accept-Encoding" => "",
      "Accept-Language" => "ja,en-US;q=0.9,en;q=0.8",
      "Content-Type" => "application/json",
      "Connection" => "keep-alive"
    }
  end

  defp host_headers(host) do
    # remove protocol like "http://host:port" -> "host:port"
    bare =
      host
      |> String.trim("/")
      |> String.split("/")
      |> List.last()

    %{
      "Host" => "#{bare}",
      "Origin" => "#{host}",
      "Referer" => "#{host}"
    }
  end

  defp secure_browser_headers() do
    %{
      "Sec-Fetch-Mode" => "cors",
      "Sec-Fetch-Site" => "same-origin",
      "cross-origin-window-policy" => "deny",
      "x-frame-options" => "SAMEORIGIN",
      "x-content-type-options" => "nosniff",
      "x-xss-protection" => "1; mode=block",
      "x-download-options" => "noopen",
      "x-permitted-cross-domain-policies" => "none"
    }
  end

  # headerを追加した新たなhttpclientを返す
  def merge_headers(%HttpClient{} = client, headers) do
    %{client | headers: Map.merge(client.headers, headers)}
  end

  def add_header(%HttpClient{} = client, key, value) do
    case {key, value} do
      {nil, _} -> client
      {_, nil} -> client
      {key, value} -> %{client | headers: Map.put(client.headers, key, value)}
    end
  end

  def remove_header(%HttpClient{} = client, key) do
    {_, map} = Map.pop(client.headers, key)
    #    IO.puts "HttpClient.remove_header #{inspect map}"
    %{client | headers: map}
  end

  def get_response_header(%HttpClient{} = client, key) do
    client.response.headers
    |> Enum.find(&(elem(&1, 0) == key))
    |> elem(1)
  end

  def get(client, path, params \\ "") do
    request(client, "GET", path <> MapConverter.to_param_string(params))
  end

  def post(client, path, params \\ "") do
    request(client, "POST", path, params)
  end

  def put(client, path, params \\ "") do
    request(client, "PUT", path, params)
  end

  def delete(client, path, params \\ "") do
    request(client, "DELETE", path, params)
  end

  def request(client, method, path, params \\ "") do
    #    IO.puts("HttpClient.request --------------------")
    #    IO.puts("host: #{client.host}")
    #    IO.puts("base_url: #{client.base_url}")
    #    IO.puts("path: #{path}")

    request = %{
      method: method,
      url: client.host <> client.base_url <> path,
      headers: client.headers,
      body: Poison.encode!(params, pretty: true),
      path: path
    }

    to_atom = fn m -> String.to_atom(String.downcase(m)) end

    result = HTTPoison.request(to_atom.(method), request.url, request.body, request.headers)
    client = update_client(client, result, path)
    HttpClientLogger.log(request, client.response)
    client
  end

  # update headers for next req
  defp update_client(client, result, path) do
    list_to_map = fn
      list ->
        Enum.reduce(
          list,
          %{},
          fn entry, acc ->
            Map.merge(acc, %{elem(entry, 0) => elem(entry, 1)})
          end
        )
    end

    cookie = fn headers -> Map.get(list_to_map.(headers), "set-cookie") end
    client = add_header(client, "Referer", client.host <> path)

    case result do
      {:ok, response} ->
        #        IO.puts("==== response: #{inspect response}")
        data = parse_data(response)
        #        IO.puts("==== data: #{inspect data}")
        token = parse_csrf_token(data)

        client
        |> add_header("Cookie", cookie.(response.headers))
        |> add_header("X-CSRF-TOKEN", token)
        |> Map.merge(%{response: response})
        |> Map.merge(%{data: data})
        |> Map.merge(%{response_origin: result})

      {:error, message} ->
        Mix.raise(message)
    end
  end

  defp parse_data(response) do
    case Jason.decode(response.body) do
      {:ok, %{"data" => data}} -> MapConverter.atomize(data)
      _ -> nil
    end
  end

  @spec parse_csrf_token(%{}) :: String.t() | nil
  defp parse_csrf_token(data) do
    case is_map(data) && Map.has_key?(data, :csrf_token) && byte_size(data.csrf_token) > 0 do
      true -> data.csrf_token
      false -> nil
    end
  end

  def expect(client, path, expected_code), do: expect(client, "GET", path, %{}, expected_code)

  def expect(client, method, path, params, expected_code) do
    client = HttpClient.request(client, method, path, params)

    if client.response.status_code != expected_code do
      Mix.raise("status_code(#{client.response.status_code} != expected(#{expected_code})")
    end

    client
  end
end
