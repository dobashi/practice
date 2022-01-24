package model

import org.jetbrains.exposed.sql.Table

object Groups: Table() {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)

