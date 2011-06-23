package org.munat.pagent

import ru.circumflex._, core._, web._, freemarker._
import java.util.Date

class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  
  get("/") = ftl("index.ftl")

}