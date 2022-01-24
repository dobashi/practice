defmodule Http.HttpClientLogger do
  def log(req, res) do
    #    IO.puts "HttpClientLogger -- #{inspect res}"
    """
    #{req.method} #{req.url}"
    request:
      headers:
    #{header_to_string(Map.to_list(req.headers))}
      body:
    #{indent(req.body, 4)}
    response:
      status_code: #{res.status_code}
      headers:
    #{header_to_string(res.headers)}
      body:
    #{
      res.body
      |> parse_response_body()
      |> indent(4)
    }
    """
    |> write(req.method <> req.path)
  end

  defp indent(str, level) do
    spaces = String.duplicate(" ", level)

    str
    |> String.split("\n")
    |> Enum.map(fn e -> spaces <> e end)
    |> Enum.join("\n")
  end

  defp header_to_string(list) do
    list
    |> Enum.map(fn e -> "    " <> elem(e, 0) <> ": " <> elem(e, 1) end)
    |> Enum.join("\n")
  end

  defp parse_response_body(body) do
    case Poison.decode(body) do
      {:ok, decoded} -> Poison.encode!(decoded, pretty: true)
      _ -> String.replace(body, "\\n", "\n")
    end
  end

  defp write(str, path) do
    uniq_name = fn path ->
      "#{DateTime.to_iso8601(DateTime.utc_now(), :basic)}" <>
        "_" <> String.replace(path, "/", "_")
    end

    File.mkdir("test/logs")
    name = "test/logs/" <> uniq_name.(path) <> ".log"
    File.open(name, [:write])
    File.write(name, str)
  end
end
