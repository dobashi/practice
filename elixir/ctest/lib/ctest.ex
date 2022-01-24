defmodule LxFlatMap do
  # @deprecated use chain instead of flat_map
  alias Algae.Either.Right
  alias Algae.Either.Left

  defmacro __using__(opts \\ []) do
    quote do
      import unquote(__MODULE__), unquote(opts)
    end
  end

  def flat_map(%Right{right: x}, f), do: f.(x)
  def flat_map(%Left{left: x}, f), do: x
end
defmodule CTest do
  alias Algae.Either.Right
  alias Algae.Either.Left

  use Witchcraft.Functor
  use LxFlatMap
  use Witchcraft.Chain
  #  use Witchcraft.Foldable

  def main(args \\ []) do
    ok_ng(2)
    ok_flatten(3)
    ok_twice(4)
    ng_twice(5)
    #    ng(3) |> flatten |> twice_throwable
    #    |> flat_map(& twice_throwable(&1))
    #    |> flat_map(& ng(&1))
    |> IO.inspect
  end

  def ok_ng(x) do
    ok(x)
    |> flat_map(&twice_throwable(&1))
    |> IO.inspect
    |> flat_map(&ng(&1))
    |> IO.inspect
  end

  def ok_flatten(x) do
    IO.puts "ok_flatten"
    ok(x)
    |> flatten
    |> IO.inspect
  end

  defp ok_twice(x) do
    IO.puts "ok_twice"
    ok(x)
    |> IO.inspect
    |> map(&twice(&1))
    |> IO.inspect
    |> map(&twice(&1))
    |> IO.inspect
  end
  defp ng_twice(x) do
    IO.puts "ng_twice"
    ng(x)
    |> IO.inspect
    |> map(&twice(&1))
    |> IO.inspect
    |> map(&twice(&1))
    |> IO.inspect
    end

#  defp flat_map(%Right{right: x}, f), do: f.(x)
#  defp flat_map(%Left{left: x}, f), do: x
  def twice_throwable(x), do: Right.new(x * 2)
  def ok(x), do: Right.new(x)
  def ng(x), do: Left.new("error_message")
  def twice(x), do: x * 2
end


