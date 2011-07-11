package org.munat.pagent

import javax.servlet._

class DbListener extends ServletContextListener {
  private var context: ServletContext = null

  def contextInitialized(event: ServletContextEvent) {
    this.context = event.getServletContext()
    Db.runSetup()
  }

  def contextDestroyed(event: ServletContextEvent) {}
}
