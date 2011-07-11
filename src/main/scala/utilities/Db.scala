package org.munat.pagent

import org.munat.pagent.model._
import org.munat.pagent.Helpers._

import javax.servlet._

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

object Db {
  var termstore: Option[PersistedtedStringMGJ] = None
  var rsp: Option[mTT.Resource] = None
  
  val mnkrAgntSrvrLbl = "contentChannel(\"monikerAgentServer\")"
  
  implicit def toPattern(
    s : String
  ) : CnxnCtxtLabel[String,String,String] with Factual =
    CXQ.fromCaseClassInstanceString(
      s
    ).getOrElse(
      null
    ).asInstanceOf[CnxnCtxtLabel[String,String,String] with Factual]

  implicit def toValue( s : String ) : mTT.Resource = mTT.Ground( s )
  
  def setRsp(r: Option[mTT.Resource]) { rsp = r }
  def popRsp(): Option[mTT.Resource] = {
    Db.rsp match {
      case Some(r) => setRsp(None)
        Some(r)
      case None => None
    }
  }
  
  def handleResponse( resp: Option[mTT.Resource] ) =
    println("***** RESPONSE: " + resp.map(_.toString).getOrElse("Hmmm"))
  
  def runSetup() {

    val pimgJunq = ptToPt( "Pagent", "MacOficina-4", "MacOficina-4" )
    val atps = pimgJunq.agentTwistedPairs

    Db.termstore = Some(pimgJunq)
  }
  
  def makeId(uuid: UUID): String = "id_" + uuid.toString.replace("-","_")
  
  def makeConnection(agentSession: AgentSession) {
    Db.termstore.map(ts => {
      reset { ts.put(  mnkrAgntSrvrLbl, agentSession.connectString ) }
    })
  }
  
  def launchAgent(agentSession: AgentSession) {
    Db.termstore.map(ts => {
      reset { ts.put( agentSession.clntSessSndLbl, agentSession.newAgentRequest ) }
      reset { ts.put( agentSession.clntSessRcvLbl, agentSession.newAgentCreated ) }                             // REMOVE THIS (mock agent response: agent creation)
    })
  }
  
  def agentCreated_?(agentSession: AgentSession): Boolean = {
    Db.termstore.map(ts => {
      reset { for( e <- ts.get( agentSession.clntSessRcvLbl ) ) { setRsp( e ) } }
    })
    Db.popRsp() match {
      case Some(r) => println(r.toString)
        true
      case _ => false
    }
  }
  
  def getAgentLogs(agentSession: AgentSession): Option[mTT.Resource] = {
    Db.termstore.map(ts => {
      reset { ts.put( agentSession.clntSessRcvLbl, agentSession.getLogs ) }                                     // REMOVE THIS (mock agent logs)
      reset { for( e <- ts.get( agentSession.clntSessRcvLbl ) ) { setRsp( e ) } }
    })
    Db.popRsp()
  }
  
  def getAgents(agentSession: AgentSession): Option[mTT.Resource] = {
    Db.termstore.map(ts => {
      reset { ts.put( agentSession.clntSessRcvLbl, agentSession.getAgents ) }                                   // REMOVE THIS (mock agents)
      reset { for( e <- ts.get( agentSession.clntSessRcvLbl ) ) { setRsp( e ) } }
    })
    Db.popRsp()
  }
  
  def getCnxns(agentSession: AgentSession): Option[mTT.Resource] = {
    Db.termstore.map(ts => {
      reset { ts.put( agentSession.clntSessRcvLbl, agentSession.getCnxns ) }                                    // REMOVE THIS (mock cnxns)
      reset { for( e <- ts.get( agentSession.clntSessRcvLbl ) ) { setRsp( e ) } }
    })
    Db.popRsp()
  }
}