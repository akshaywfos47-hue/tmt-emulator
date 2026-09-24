package emulator.simulation

import com.typesafe.config.ConfigFactory

class RgripSimulation {

  private val config = ConfigFactory.load()
  private val rgrip = config.getConfig("emulator.components.rgrip")

  private val minPosition = rgrip.getInt("range.min")
  private val maxPosition = rgrip.getInt("range.max")

  private var currentPosition =28

  def moveTo(targetPosition: Int)(onPositionUpdate:Int => Unit): Unit = {

    if (targetPosition < minPosition || targetPosition > maxPosition) {
      println(
        s"Invalid target position: $targetPosition. " +
          s"Allowed range: $minPosition to $maxPosition"
      )
      return
    }

    println(s"Moving from $currentPosition to $targetPosition")

    while (currentPosition != targetPosition) {

      if (currentPosition < targetPosition)
        currentPosition = math.min(currentPosition + 1, targetPosition)
      else
        currentPosition = math.max(currentPosition - 1, targetPosition)

      println(s"Current position: $currentPosition")

      onPositionUpdate(currentPosition)

      Thread.sleep(10)
    }

    println(s"Reached target position: $currentPosition")
  }

  def getCurrentPosition: Int = currentPosition
}