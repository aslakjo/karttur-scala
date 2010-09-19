package code.snippet

import _root_.net.liftweb.util._
import Helpers._
import net.liftweb.http.js.JE._
import _root_.scala.xml._
import net.liftweb.http._
import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.mapper._

import main.scala.code.model.Place
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.jquery.JqJsCmds._

class PlaceSnippet
{

  def newPlace(nodes :NodeSeq):NodeSeq={
    var name, comment, lat, lon = ""
    bind("f", nodes,          
      "name" -> SHtml.text("", name=_),
      "comment" -> SHtml.text("", comment=_),
      "lat" -%> SHtml.text("", lat=_),
      "lon" -%> SHtml.text("", lon=_),
      "save" -> SHtml.submit("lagre", ()=> Place.create
              .name(name)
              .comments(comment)
              .lat(lat.toDouble)
              .lon(lon.toDouble)
              .save)
      )
  }

  def place(nodes: NodeSeq):NodeSeq  ={

    val placeId = S.param("place")

    placeId match{
      case Empty => <p>Må sette ide for å finne sted</p>
      case Full(placeId) => {
        val place = Place.find(By(Place.id, placeId.toLong)).open_!
        bind("p", nodes,
          "name" -> place.name,
          "comments" -> place.comments,
          "lat" -> place.lat,
          "lon" -> place.lon,
          "load" -> (Run("DrawPoint(map, \""+ place.id.toString +"\");") &
                  Run("centerAt("+ place.lat +", "+ place.lon +");")).toJsCmd
          )
        
      }
    }
  }

}