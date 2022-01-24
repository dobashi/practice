package jp.lavans.skinnytest.controller

import jp.lavans.skinnytest.controller.api.v1.UsersApiController
import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    users.mount(ctx)
    usersApi.mount (ctx)
    members.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }

  object members extends MembersController with Routes {
  }

  object users extends UsersController with Routes {
  }

  object usersApi extends UsersApiController with Routes {
    val base = s"/api/v1/${resourcesName}"
    val indexUrl = get(base)(toJSONString(index)).as('index)
    val detailUrl = get(base+"/:id")(toPrettyJSONString(detail)).as('detail)

  }


}
