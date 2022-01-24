defmodule CtestTest do
  use ExUnit.Case
  doctest Ctest

  test "greets the world" do
    assert Ctest.hello() == :world
  end
end
