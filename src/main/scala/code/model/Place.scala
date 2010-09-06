package main.scala.code.model

import net.liftweb.mapper._

class Place extends LongKeyedMapper[Place] with IdPK {
  def getSingleton = Place

  object name extends MappedString(this, 140)
  object lat extends MappedDouble(this)
  object lon extends MappedDouble(this)
  object comments extends MappedString(this, 512)
}

object Place extends Place with LongKeyedMetaMapper[Place]{

}
