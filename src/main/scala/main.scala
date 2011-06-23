package org.munat.pagent

import org.munat.pagent.rest._

import ru.circumflex._, core._, web._, freemarker._

class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  
  get("/") = ftl("index.ftl")
  
  new ConnectionRouter
  new LogItemRouter
  new AgentRouter

}