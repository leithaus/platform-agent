package org.munat.pagent

import org.munat.pagent.rest._
import org.munat.pagent.model._

import ru.circumflex._, core._, web._

import java.util.UUID._

object Helpers {
  // substitutes a UUID regex for :uuid in paths
  def pr(path: String) = {
    path.replaceAll(
      ":uuid",
      "(?i)([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})"
    ).r
  }
  
  def getRange(): Option[(Int,Int)] = {
    val fltr = """items=(\d+)-(\d+)""".r
    headers.getOrElse("Range", "") match {
      case fltr(f, l) => println("***** FOUND: f = " + f.toString + "; l = " + l.toString)
        try {
          Some((f.toInt,l.toInt))
        } catch {
          case _ => None
        }
      case x => println("-----FOUND: " + x)
        None
    }
  }
  
  def agentRespons(agentSession: AgentSession): String = {
    "newAgent( host(  ), path(  ) )"
  }
  
  def fakeLogs(): String = {
    val xml = <logs/>
    
    "<![CDATA[ " + xml.toString + " ]]>"
  }
}
