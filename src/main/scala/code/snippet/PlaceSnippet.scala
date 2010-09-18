package code.snippet

import _root_.net.liftweb.util._
import Helpers._
import net.liftweb.http.js.JE._
import _root_.scala.xml._
import net.liftweb.http.SHtml 
import main.scala.code.model.Place

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

}