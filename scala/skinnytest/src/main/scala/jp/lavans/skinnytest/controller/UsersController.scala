package jp.lavans.skinnytest.controller

import jp.lavans.skinnytest.model.User
import skinny._
import skinny.validator._

class UsersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = User
  override def resourcesName = "users"
  override def resourceName = "user"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDateTime("created_at").withDateTime("updated_at")
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("email") is required & maxLength(512),
    paramKey("created_at") is required & dateTimeFormat,
    paramKey("updated_at") is required & dateTimeFormat
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "email" -> ParamType.String,
    "created_at" -> ParamType.DateTime,
    "updated_at" -> ParamType.DateTime
  )

  override def updateParams = Params(params).withDateTime("created_at").withDateTime("updated_at")
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("email") is required & maxLength(512),
    paramKey("created_at") is required & dateTimeFormat,
    paramKey("updated_at") is required & dateTimeFormat
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "email" -> ParamType.String,
    "created_at" -> ParamType.DateTime,
    "updated_at" -> ParamType.DateTime
  )

}
