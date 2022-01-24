defmodule UsingEither do
  alias Algae.Either
  alias Algae.Maybe

  def main do
    result = load(1)
  end

  def load(id) do
    Either.left("error")
  end
end