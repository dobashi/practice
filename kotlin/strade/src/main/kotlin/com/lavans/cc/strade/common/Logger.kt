package com.lavans.cc.strade.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    fun getLogger(v: String):Logger = LoggerFactory.getLogger(v)
    fun getLogger(o: Any):Logger = getLogger(o.javaClass.canonicalName)
}