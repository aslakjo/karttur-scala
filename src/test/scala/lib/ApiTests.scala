package test.scala.lib

import _root_.code.api.Api
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.BeforeAndAfterEach
import net.liftweb.common.{Full, Box}
import main.scala.code.model.Place
import bootstrap.liftweb.{Boot}
import net.liftweb._
import mapper._
import util._
import Helpers._
import net.liftweb.http._

class ApiSpec extends FlatSpec with ShouldMatchers with BeforeAndAfterEach  {

  "Api" should "save a place when called" in {
    val result = Api.newPoint(Full("10.2"), Full("60.0"), Full("Perfekt place"), Full("merknad"))
    Place.findAll should not be ('empty)
  }

  it should "return the database id" in {
    val result = Api.newPoint(Full("10.2"), Full("60.0"), Full("navn"), Full("kommentar"))

    val json= result.toResponse.toString()
    json should include ("status")
    json should include ("id")

    val place = Place.findAll.last
    json should include (place.id.toString)
  }

  

  override def beforeEach {
    setupDb
  }

  def setupDb={
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 	new StandardDBVendor(
        "org.h2.Driver",
        "jdbc:h2:mem:test;AUTO_SERVER=TRUE",
        Props.get("db.user"), Props.get("db.password")
      )

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }
    Schemifier.schemify(true, Schemifier.infoF _, Place)
  }
}
