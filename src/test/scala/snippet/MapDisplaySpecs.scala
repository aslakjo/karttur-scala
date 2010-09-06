package test.scala.snippet

import _root_.code.snippet.MapDisplay
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.BeforeAndAfterEach


class MapDisplaySpec extends FlatSpec with ShouldMatchers with BeforeAndAfterEach  {

  "Map" should "display a place given its id" in {
    val mapDisplay = new MapDisplay()
    val xml = <MapDisplay:place/>
    
    val output = mapDisplay.place(xml)
    output should not be (null)
  }

}
