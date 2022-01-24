defmodule Http.MapConverter do
  # もともとStringで与えられた場合はStringのまま(is_binaryは厳密にはStringではないが、これでやるのが定番らしい)
  def to_param_string(params) when is_binary(params) do
    case params do
      "" -> ""
      params -> "?" <> params
    end
  end

  # Map<key,value>から`?key1=value1&key2=value2&`形式へ変換
  def to_param_string(params) when is_map(params) do
    Map.to_list(params)
    |> Enum.filter(fn entry -> elem(entry, 0) != :__struct__ end)
    |> to_string_map()
    |> Enum.reduce(
      "?",
      fn header, acc ->
        acc <> elem(header, 0) <> "=" <> elem(header, 1) <> "&"
      end
    )
  end

  defp to_string_map(map), do: for({k, v} <- map, into: %{}, do: {Atom.to_string(k), v})

  def atomize(target) do
    is_struct = fn s -> is_map(s) && Map.has_key?(s, :__struct__) end
    key = fn k -> if(is_binary(k), do: String.to_atom(k), else: k) end
    value = fn v -> if(is_map(v) || is_list(v), do: atomize(v), else: v) end
    map = fn m -> if(is_struct.(m), do: Map.from_struct(m), else: m) end
    # IO.puts("HttpClient.atomize target: #{inspect(target)}")

    case target do
      nil -> nil
      x when is_list(x) -> Enum.map(x, &atomize(&1))
      x when is_map(x) -> for {k, v} <- map.(x), into: %{}, do: {key.(k), value.(v)}
      x -> x
    end
  end
end
