package com.lavans.cc.strade.common

object File {
    fun resourceAsStream(filename: String): java.io.InputStream =
            javaClass.classLoader.getResourceAsStream(filename)

    fun resourceAsString(filename: String): String =
            resourceAsStream(filename).use { it.bufferedReader().readText() }
}
