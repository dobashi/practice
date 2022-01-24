package common

import org.slf4j.Logger

open class Logger {
    val log: Logger = org.slf4j.LoggerFactory.getLogger(this.javaClass.canonicalName)
}