import controller.UserController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.ExceptionHandler
import spark.Request
import spark.Response
import spark.kotlin.*

fun main(args: Array<String>) {
    port(8080)
    val log: Logger = LoggerFactory.getLogger("Main")
    log.info("start")
    get("/hello") {
        "<h1>Hello Spark!!</h1>+<h2>test</h2>"
    }
    UserController.mount()

    val h = SparkExceptionHandler()

//    spark.Spark.exception<java.lang.Exception>(java.lang.Exception::class.java,  h)
    spark.Spark.exception(Exception::class.java, ExceptionHandler(fun(e, req, res) {
        println(e.printStackTrace())
    }))

}
class SparkExceptionHandler: ExceptionHandler<java.lang.Exception> {
    override fun handle(e: java.lang.Exception, req: Request, res: Response){
       println()
    }
}

