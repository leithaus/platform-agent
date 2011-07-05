package org.munat.pagent

import org.munat.pagent.rest._

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
import java.util.UUID
import CCLDSL._
import PersistedMonadicTS._
import com.biosimilarity.lift.lib.SpecialKURIDefaults._
import mTT._

class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  
  var rsp: Option[com.biosimilarity.lift.model.store.usage.PersistedMonadicTS.mTT.Resource] = None
  
  def setRsp(r: Option[com.biosimilarity.lift.model.store.usage.PersistedMonadicTS.mTT.Resource]) {
    rsp = r
  }
  
  implicit def toPattern(
    s : String
  ) : CnxnCtxtLabel[String,String,String] with Factual =
    CXQ.fromCaseClassInstanceString(
      s
    ).getOrElse(
      null
    ).asInstanceOf[CnxnCtxtLabel[String,String,String] with Factual]

  implicit def toValue( s : String ) : mTT.Resource = mTT.Ground( s )
  
  def handleResponse( resp: Option[com.biosimilarity.lift.model.store.usage.PersistedMonadicTS.mTT.Resource] ) = println("***** RESPONSE: " + resp.map(_.toString).getOrElse("Hmmm"))
  
  get("/") = {
/*    Db.termstore match {
      case Some(ts) => reset { for( e <- ts.get( pattern ) ) { println( "***** MESSAGE RECEIVED: " + e ) } }
      case None => println("***** TERMSTORE not found")
    }*/
    
    ftl("index.ftl")
  }
  
  post("/") = {
    response.contentType("application/json")
    
    val mnkrAgntSrvrLbl = "contentChannel(\"monikerAgentServer\")"
    
    val clientSessionUuidSnd = "id_" + UUID.randomUUID.toString.replace("-","_")
    val clntSessSndLbl = "contentChannel(\"" + clientSessionUuidSnd + "\")"
    
    val clientSessionUuidRec = "id_" + UUID.randomUUID.toString.replace("-","_")
    val clntSessRcvLbl = "clientSessionRecvChannel(\"" + clientSessionUuidRec + "\")"
    
    val connectString = "clientSession( sendChan( " + clientSessionUuidSnd + " ), recvChan( " + clientSessionUuidRec + " ) )"
    
    val newAgentString = "createNewAgent( ipAddr( " + param.getOrElse("ipAddress", "localhost") + " ), path( " + param.getOrElse("path", """"/"""") + " ) )"
    
    val queryLogString = "queryLog( session( " + clientSessionUuidSnd + " ), ip( " + param.getOrElse("ipAddress", "localhost") +
      " ), path(" + param.getOrElse("path", """"/"""") + " ) )"
    
    Db.termstore match {
      case Some(ts) => 
        println("+++++ connectString: " + connectString)
        reset { ts.put(  mnkrAgntSrvrLbl, connectString ) }
        
        println("+++++ newAgentString: " + newAgentString)
        reset { ts.put( clntSessSndLbl, newAgentString ) }
        
        println("WAITING FOR GODOT............................")
        reset { for( e <- ts.get( clntSessRcvLbl ) ) { setRsp( e ) } }
        println("STILL WAITING............................")
        
        println("+++++ queryLogString: " + queryLogString)
        reset { ts.put( clntSessRcvLbl, queryLogString ) }
        
      case None => println("+++++ TERMSTORE not found")
    }
    
    val out = rsp match {
      case Some(RBound(Some(Ground(r)),_)) => """{"success":true,"message":"""" + r.replace("<![CDATA[","").replace("]]>","") + """"}"""
      case _ => """{"success":false}"""
    }
    
    out
  }
  
  new ConnectionRouter
  new LogItemRouter
  new AgentRouter

}