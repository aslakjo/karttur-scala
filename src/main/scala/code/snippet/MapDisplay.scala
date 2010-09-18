package code.snippet

import _root_.scala.xml.{NodeSeq,Text}
import main.scala.code.model.Place
import net.liftweb.mapper.By
import net.liftweb.http._
import math.BigInt
import _root_.net.liftweb.util._
import Helpers._
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._ 


class MapDisplay {

  def place(in:NodeSeq):NodeSeq={
    val places = Place.findAll

    bind("place", in,
        "list" -> places.flatMap( p => bind("p", chooseTemplate("places", "list", in),
          "name" -> SHtml.a(<span>{p.name}</span>, 
            Run("centerAt(" + p.lat + ", " + p.lon + ");") &
            SetHtml("place",
              <div>{p.name}</div>
              <div>{p.comments}</div>
              <div>{p.lat}  {p.lon}</div>))
            ,
            AttrBindParam("placeId", p.id.toString, "placeId")
          )
      )
    )
  }

}