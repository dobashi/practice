package common

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.io.File
import java.util.*

interface DataSource{
    fun status() {
        checkFile()
    }
    fun connect() {
        DataSource.connect()
    }
    companion object DataSource: Logger() {
        private val filename = "database.properties"
//        private val ds = HikariConfig(filename).dataSource

        fun checkFile(){
            val file = File(filename)
            log.info("filename:$filename")
            if (file.isFile) log.info("isFile")
            log.info(file.toString())
            val input = javaClass.classLoader.getResourceAsStream(filename)
            log.info(input.toString())
            val p = Properties()
            p.load(input)
            log.info(p.toString())
        }

        fun connect(): Unit {
            val input = javaClass.classLoader.getResourceAsStream(filename)
            val props = Properties()
            props.load(input)
            input.close()
            log.info("props: $props")
            log.info("url: ${props.get("url")}")
            log.info("driver: ${props.get("driver")}")
            val config = HikariConfig()
            config.jdbcUrl=props.getProperty("url")
            config.driverClassName=props.getProperty("driver")
            val ds = HikariDataSource(config)
            log.info("DataSource:connect $ds")
            Database.connect(ds)
        }
    }
}


