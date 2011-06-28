package org.munat.pagent

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
}

class DbListener extends ServletContextListener {
  private var context: ServletContext = null

  def contextInitialized(event: ServletContextEvent) {
    this.context = event.getServletContext()
    runSetup()
  }

  def contextDestroyed(event: ServletContextEvent) {}
  
  def runSetup() {
    implicit def toPattern(
      s : String
    ) : CnxnCtxtLabel[String,String,String] with Factual =
      CXQ.fromCaseClassInstanceString(
        s
      ).getOrElse(
        null
      ).asInstanceOf[CnxnCtxtLabel[String,String,String] with Factual]

    implicit def toValue( s : String ) : mTT.Resource = mTT.Ground( s )

    val pimgJunq = ptToPt( "Pagent", "MacOficina-4", "MacOficina-4" )
    val atps = pimgJunq.agentTwistedPairs
    
    Db.termstore = Some(pimgJunq)
  }
}
