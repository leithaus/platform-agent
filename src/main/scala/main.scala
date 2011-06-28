package org.munat.pagent

import org.munat.pagent.rest._

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


class Main extends RequestRouter {
  val log = new Logger("org.munat.pagent")
  val pattern = "/pagent"
  
  implicit def toPattern(
    s : String
  ) : CnxnCtxtLabel[String,String,String] with Factual =
    CXQ.fromCaseClassInstanceString(
      s
    ).getOrElse(
      null
    ).asInstanceOf[CnxnCtxtLabel[String,String,String] with Factual]

  implicit def toValue( s : String ) : mTT.Resource = mTT.Ground( s )
  
  get("/") = {
    Db.termstore match {
      case Some(ts) => reset { for( e <- ts.get( pattern ) ) { println( "***** MESSAGE RECEIVED: " + e ) } }
      case None => println("***** TERMSTORE not found")
    }
    
    ftl("index.ftl")
  }
  
  post("/") = {
    val payload = param.getOrElse("ipAddress", "localhost") + param.getOrElse("path", "/")
    println("***** PAYLOAD: " + payload)
    Db.termstore match {
      case Some(ts) => reset { ts.put( pattern, payload ) }
      case None => println("+++++ TERMSTORE not found")
    }
    
    redirect("/")
  }
  
  new ConnectionRouter
  new LogItemRouter
  new AgentRouter

}