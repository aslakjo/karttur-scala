package code.api{

import net.liftweb.http.{Req, GetRequest, PostRequest, LiftRules, JsonResponse, PlainTextResponse}
import net.liftweb.common.{Full, Box}
import net.liftweb.http.js.JE._
import main.scala.code.model.Place
import math.BigDecimal
import net.liftweb.json.JsonDSL._
import net.liftweb.mapper.By
import net.liftweb.http.S
import net.liftweb.util.Log 

object Api {

    def dispatch: LiftRules.DispatchPF = {
      case r@Req("api" :: "place" :: Nil, _, PostRequest) => () => Full(find(r.param("id")))
      case r@Req("api" :: "place" :: "new" :: Nil, _, PostRequest) => () => Full(newPoint(r.param("lat"), r.param("lon"), r.param("name"), r.param("comments")))
    }

    def find(id: Box[String]) = {
      Log.info(id)
      val place = Place.find(By(Place.id, (S.param("id") open_!).toInt)) open_!
      JsonResponse(
        JsObj("title" -> place.name.toString, "lat" -> place.lat.toString, "lon" -> place.lon.toString)
      )
    }


    def newPoint(lat: Box[String], lon: Box[String], name: Box[String], comments: Box[String]) = {
      val place = Place.create
              .lat((lat open_!).toDouble)
              .lon((lon openOr "").toDouble)
              .name(name openOr "")
              .comments(comments openOr "")
      place.save

      JsonResponse(("status" -> "saved") ~ ( "id" -> place.id.toString )~("lat" -> place.lat.toString)  ~("lon" -> place.lon.toString))
    }
  }

}