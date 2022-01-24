import jp.lavans.skinnytest.controller.Controllers
import skinny._

class Bootstrap extends SkinnyLifeCycle {

  // If you prefer more logging, configure this settings
  // http://scalikejdbc.org/documentation/configuration.html
  scalikejdbc.GlobalSettings.loggingSQLAndTime = scalikejdbc.LoggingSQLAndTimeSettings(
    singleLineMode = true
  )

  // simple worker example
  /*
  val sampleWorker = new skinny.worker.SkinnyWorker with Logging {
    def execute = logger.info("sample worker is called!")
  }
   */

  override def initSkinnyApp(ctx: ServletContext): Unit = {
    // http://skinny-framework.org/documentation/worker_jobs.html
    //skinnyWorkerService.everyFixedSeconds(sampleWorker, 3)

    Controllers.mount(ctx)
  }

}
