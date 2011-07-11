package org.munat.pagent.rest

import org.munat.pagent._
import org.munat.pagent.model._
import org.munat.pagent.Helpers._

import ru.circumflex._, core._, web._, freemarker._

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
  response.contentType("text/html")
  
  get(pr("/:uuid")) = {
    'path := "/rest/agents/" + uri(1)
    
    ftl("innerTabs.ftl")
  }
  
  get(pr("/:uuid/logs")) = {
    'logItems := Db.getAgentLogs(session.getOrElse(uri(1), sendError(500)).asInstanceOf[AgentSession])
    
    ftl("logTab.ftl")
  }
  
  get(pr("/:uuid/agents")) = {
    'agents := Db.getAgents(session.getOrElse(uri(1), sendError(500)).asInstanceOf[AgentSession])
    
    ftl("agentsTab.ftl")
  }
  
  get(pr("/:uuid/cnxns")) = {
    'cnxns := Db.getCnxns(session.getOrElse(uri(1), sendError(500)).asInstanceOf[AgentSession])
    
    ftl("cnxnsTab.ftl")
  }
  
}
