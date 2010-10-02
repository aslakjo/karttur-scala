package test.scala.model
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import main.scala.code.model.Place

class PlaceSpecs extends FlatSpec with ShouldMatchers
{
     "Place" should "give correct url" in{
       val place = Place.create
       
       place.url should be ("/map/" + place.id)
     }
  
}