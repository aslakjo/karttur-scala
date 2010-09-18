package code.snippet

import _root_.scala.xml.{NodeSeq,Text}
import main.scala.code.model.Place
import net.liftweb.mapper.By
import net.liftweb.http.S
import math.BigInt
import _root_.net.liftweb.util._
import Helpers._
import net.liftweb.http.js.JE._


class MapDisplay {

  def place(in:NodeSeq):NodeSeq={
    val places = Place.findAll

    bind("place", in,
        "list" -> places.flatMap( p => bind("p", chooseTemplate("places", "list", in),
          "name" -> p.name,
          "id" -> p.id,
          AttrBindParam("placeId", p.id.toString, "placeId")
        )
      )
    )
  }

}