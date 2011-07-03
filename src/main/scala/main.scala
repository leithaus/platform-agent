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

class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  
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
    val clientSessionUuidSnd = "id_" + UUID.randomUUID.toString.replace("-","_")
    val clientSessionUuidRec = "id_" + UUID.randomUUID.toString.replace("-","_")
    val connectString = "clientSession( sendChan( " + clientSessionUuidSnd + " ), recvChan( " + clientSessionUuidRec + " ) )"
    val newAgentString = "createNewAgent( ipAddr( " + param.getOrElse("ipAddress", "localhost") + " ), path( " + param.getOrElse("path", "/") + " ) )"
    val queryLogString = "queryLog( session( " + clientSessionUuidSnd + " ), ip( " + param.getOrElse("ipAddress", "localhost") +
      " ), path(" + param.getOrElse("path", "/") + " ) )"
    
    Db.termstore match {
      case Some(ts) => 
        println("+++++ connectString: " + connectString)
        reset { ts.put(  "monikerAgentServer", connectString ) }
        
        println("+++++ newAgentString: " + newAgentString)
        reset { ts.put( clientSessionUuidSnd, newAgentString ) }
        
        println("WAITING FOR GODOT............................")
        reset { for( e <- ts.get( clientSessionUuidRec ) ) { handleResponse( e ) } }
        println("STILL WAITING............................")
        
        println("+++++ queryLogString: " + queryLogString)
        reset { ts.put( clientSessionUuidRec, queryLogString ) }
        
      case None => println("+++++ TERMSTORE not found")
    }
    
    redirect("/")
  }
  
  new ConnectionRouter
  new LogItemRouter
  new AgentRouter

}