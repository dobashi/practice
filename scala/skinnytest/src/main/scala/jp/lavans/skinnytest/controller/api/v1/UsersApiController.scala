package jp.lavans.skinnytest.controller.api.v1

import jp.lavans.skinnytest.controller.api.ApiController
import jp.lavans.skinnytest.model.User
import org.joda.time.DateTime
import skinny.micro.contrib.CORSSupport

class UsersApiController extends ApiController with CORSSupport {
  override  def accessControlAllowOriginHeaderValue = "*" // とりあえず

  def model = User
  def resourcesName = "users"
  def resourceName = "user"


  def index():List[User] = {
    val user1 = User(1, "name1", "email1", DateTime.now, DateTime.now)
    val user2 = User(2, "name2", "email2", DateTime.now, DateTime.now)
    List(user1,user2)
  }

  def detail:User = {
    params.getAs[Long]("id").map { id =>
      User(id, "name", "email", DateTime.now, DateTime.now)
    }.getOrElse{ haltWithBody(500) }
  }
}

aaa aaa aaa i aaaaajij
i       aaa aaa aa