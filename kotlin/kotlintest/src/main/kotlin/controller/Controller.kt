package controller

import spark.Request

interface Controller {
    fun Request.qp(key: String): String = this.queryParams(key)
}