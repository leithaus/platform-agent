package org.munat.pagent.model

import java.net.URI
import java.util.{UUID,Date}
import UUID._

case class AgentSession(sndId: UUID, rcvId: UUID, uri: URI) {
  def clntSessSndUuid(): String = "id_" + sndId.toString.replace("-","_")
  def clntSessSndLbl(): String = "contentChannel(\"" + clntSessSndUuid + "\")"
  
  def clntSessRcvUuid(): String = "id_" + rcvId.toString.replace("-","_")
  def clntSessRcvLbl(): String = "contentChannel(\"" + clntSessRcvUuid + "\")"
  
  def connectString(): String = "clientSession( sndChan( " + clntSessSndUuid + " ), rcvChan( " + clntSessRcvUuid + " ) )"
  
  def newAgentRequest(): String = "createNewAgent( host( " + uri.getHost + " ), path( " + uri.getPath + " ) )"
  def newAgentCreated(): String = "newAgent( session( " + clntSessSndUuid + " ), host( " + uri.getHost + " ), path( " + uri.getPath + " ) )"
  
  // Mock
  def getLogs(): String = """
    [
      {
        "agentURI":"""" + this.toString + """",
        "timestamp":"""" + new Date() + """",
        "logItem":"Did something cool",
        "logItemDetail":"Detail about just how cool it was."
      },
      {
        "agentURI":"""" + this.toString + """",
        "timestamp":"""" + new Date() + """",
        "logItem":"Messed around a bit",
        "logItemDetail":"Detail about my messing around."
      },
      {
        "agentURI":"""" + this.toString + """",
        "timestamp":"""" + new Date() + """",
        "logItem":"Oh, check this out!",
        "logItemDetail":"Everything you needed to know (or not) about it."
      },
      {
        "agentURI":"""" + this.toString + """",
        "timestamp":"""" + new Date() + """",
        "logItem":"Just goofing off now",
        "logItemDetail":"Are you getting tired of this yet?"
      }
    ]
  """
  
  def getAgents(): String = """
    [
      {
        "agentURI":"255.0.0.0/pathological",
        "name":"red"
      },
      {
        "agentURI":"0.255.0.0/psychopath",
        "name":"green"
      },
      {
        "agentURI":"0.0.255.0/neuropathy",
        "name":"blue"
      }
    ]
  """
  
  def getCnxns(): String = """
    [
      {
        "agentURI":"255.0.0.0/pathological",
        "name":"Jim Bob"
      },
      {
        "agentURI":"0.255.0.0/psychopath",
        "name":"Sue Ellen"
      },
      {
        "agentURI":"0.0.255.0/neuropathy",
        "name":"Patty Jo"
      }
    ]
  """
  
  override def toString(): String = "AgentSession( " + sndId.toString + ", " + rcvId.toString + ", " + uri.toString + " )"
}