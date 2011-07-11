package org.munat.pagent

import org.munat.pagent.rest._
import org.munat.pagent.model._

import ru.circumflex._, core._, web._, freemarker._

import com.biosimilarity.lift.model.store._
import com.biosimilarity.lift.model.store.usage._
import com.biosimilarity.lift.model.store.xml._
import com.biosimilarity.lift.model.store.xml.datasets._
import com.biosimilarity.lift.lib._
import scala.util.continuations._
import scala.concurrent.cpsops._
import scala.concurrent.{Channel => Chan, _}
import java.net.URI
import java.util.UUID._
import CCLDSL._
import PersistedMonadicTS._
import com.biosimilarity.lift.lib.SpecialKURIDefaults._
import mTT._

class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  
  get("/") = ftl("index.ftl")
  
  post("/") = {
    response.contentType("application/json")
    
    if (request.body.xhr_?) {
      
      (for {
        ipAddress <- param.get("ipAddress")
        path <- param.get("path")
      } yield {
        try {
          val agentUri = URI.create("http://" + ipAddress + path)
          session.get(agentUri.toString) match {
            case Some(agent) => 
              "{\"success\":\"false\",\"message\":\"Agent already launched at " + agentUri.toString + ".\"}"
            case None =>
              val agentSession = AgentSession(randomUUID, randomUUID, agentUri)
                println("agentSession: " + agentSession.toString)
              
              session(agentUri.toString) = agentSession.sndId
                println("session(agentUri): " + session(agentUri.toString).toString)
              session(agentSession.sndId.toString) = agentSession
                println("session(sndId): " + session(agentSession.sndId.toString).toString)
                
              Db.makeConnection(agentSession)
              Db.launchAgent(agentSession)
              if ( Db.agentCreated_?(agentSession) ) {
                "{\"success\":\"true\",\"sndId\":\"" + agentSession.sndId.toString +
                  "\",\"host\":\"" + agentSession.uri.getHost +
                  "\",\"path\":\"" + agentSession.uri.getPath +
                "\"}"
              } else {
                "{\"success\":\"false\",\"message\":\"Bad response from Db.getAgentLogs(agentSession).\"}"
              }
          }
        } catch {
          case e => println(e.getMessage)
          sendError(500)
        }
      }).getOrElse("{\"success\":false,\"message\":\"Weird and improbable output.\"}")
      
    } else {
      sendError(500)
    }
  }
  
  new ConnectionRouter
  new LogItemRouter
  new AgentRouter

}