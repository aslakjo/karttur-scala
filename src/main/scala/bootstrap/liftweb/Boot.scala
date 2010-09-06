package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import code.model._
import code.api._
import main.scala.code.model.Place


class Boot {
  def boot {
    setupDB

    Schemifier.schemify(true, Schemifier.infoF _, User, Place)

    LiftRules.addToPackages("code")

    val entries = List(
      Menu.i("Karttur") / "index" ,
      Menu("Kart") / "map"
    ) 
    
    LiftRules.dispatch.prepend(Api.dispatch)

    LiftRules.setSiteMap(SiteMap(entries:_*))

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    S.addAround(DB.buildLoanWrapper)
  }

  def setupDB ={
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }
  }
}
