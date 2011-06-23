package org.munat.pagent.rest

import ru.circumflex._, core._, web._

class AgentRouter extends RequestRouter("/rest/agents") {
  response.contentType("application/json")
  
  get("/?".r) = "[]"
  
}
