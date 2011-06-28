package org.munat.pagent.rest

import ru.circumflex._, core._, web._

import com.biosimilarity.lift.model.store._
import com.biosimilarity.lift.model.store.usage._
import com.biosimilarity.lift.model.store.xml._
import com.biosimilarity.lift.model.store.xml.datasets._
import com.biosimilarity.lift.lib._
import scala.util.continuations._ 
import java.net.URI
import java.util.UUID
import CCLDSL._
import PersistedMonadicTS._
import com.biosimilarity.lift.lib.SpecialKURIDefaults._

class AgentRouter extends RequestRouter("/rest/agents") {
  response.contentType("application/json")
  
  get("/?".r) = "[]"
  
}
