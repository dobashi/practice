package com.lavans.cc.strade.common

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.stream.Collectors

/**
 * HttpClient
 *
 * Usege:
 * val result = HttpClient(url).get();	// just get
 * val result = HttpClient(url).get(query)	// get with Params
 * val result = HttpClient(url).post(body)	// post with data
 *
 * @author dobashi
 */
typealias Params = Map<String, String>

fun Map<String, String>.toParamString(): String {
    return if (isEmpty()) ""
    else entries.fold("?") { s, e -> "$s&${e.key}=${e.value}" }
}

typealias HttpHeader=Pair<String, String>
class HttpClient(
        val url: String,
        val headers: Params = mapOf(userAgent),
        val charset: Charset = Charset.UTF_8) {
    private val log = Logger.getLogger(this)

    companion object {
        val userAgent = HttpHeader("User-Agent", "Lavans Kotlin HttpClient")
    }

    enum class Charset(val value: String) { UTF_8("UTF-8") }
    enum class Method { GET, POST, PUT }

    fun get(params: Params): String {
        return get(params.toParamString())
    }

    /**
     * GET
     * @param query query parameters. ex) "?status=unread&dateGt=20180309"
     */
    fun get(query: String=""): String {
        val uri = url + if (query.startsWith('?')) query else '?' + query
        // debug
        if (log.isDebugEnabled) {
            log.debug("GET $uri\n")
            headers.forEach { k, v -> log.debug("$k: $v") }
        }
        val url = URL(uri)
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = Method.GET.name
        headers.forEach { k, v -> con.setRequestProperty(k, v) }

        return read(con)
    }

    fun post(params: Params): String {
        return post(params.toParamString())
    }
    /**
     * GET
     * @param body post data ex) "{"name":"q", "addr":"outside"}"
     */
    fun post(body: String): String {
        val con = URL(url).openConnection() as HttpURLConnection
        con.requestMethod = Method.POST.name
        headers.forEach { k, v -> con.setRequestProperty(k, v) }

        con.doOutput = true
        val writer = PrintStream(con.outputStream).use {
            it.print(body)
            it.flush()
        }
        return read(con)
    }

    private fun read(con: URLConnection): String{
        val result = BufferedReader(
                InputStreamReader(con.inputStream, charset.value))
                .use { it.lines().collect(Collectors.joining()) }
        log.debug(result)
        return result
    }

}
