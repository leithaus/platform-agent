package org.munat.pagent.rest

import ru.circumflex._, core._, web._

class ConnectionRouter extends RequestRouter("/rest/connections") {
  response.contentType("application/json")
  
  get("/?".r) = "[]"
  
}
