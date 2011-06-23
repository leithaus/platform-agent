package org.munat.pagent.rest

import ru.circumflex._, core._, web._

class LogItemRouter extends RequestRouter("/rest/log_items") {
  response.contentType("application/json")
  
  get("/?".r) = "[]"
  
}
