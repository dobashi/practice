package controller

import common.DataSource
import common.JsonObjectMapper
import common.Logger
import model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.transactions.transaction
import spark.kotlin.*

object UserController: Controller, JsonObjectMapper, DataSource, Logger() {
    fun mount(): Unit  {
        val model = "/users"
        val dao = UserDao()
        get(model) {
            println("(none)")
            jacksonObjectMapper().writeValueAsString(dao.users)
        }

        get("$model/") {
            println("/")
        }



        get("$model/email/:email") {
            dao.findByEmail(request.params("email"))?: internalServerError { 500 }
        }

        get("$model/create") { // post
            dao.save(name = request.qp("name"), email = request.qp("email"))
            response.status(201)
            "ok"
        }

        patch("$model/update/:id") {
            dao.update(
                    id = request.params("id").toInt(),
                    name = request.qp("name"),
                    email = request.qp("email")
            )
            "ok"
        }

        delete("$model/delete/:id") {
            dao.delete(request.params("id").toInt())
            "ok"
        }

        get("$model/createTable"){
            DataSource.checkFile()
            log.debug("createTable in")
            status()
            log.info("status done")
            connect()
            log.info("connect done")
            transaction {
                log.info("start transaction")
                //SQLの内容を標準出力に出す
                logger.addLogger(StdOutSqlLogger)
                //テーブルを作成
                SchemaUtils.create(Groups)
            }
        }

        get("$model/:id") {
            connect()

//            dao.findById(request.params("id").toInt())!!
        }

    }
}
