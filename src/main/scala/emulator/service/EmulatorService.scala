package emulator.service

import emulator.simulation.RgripSimulation

class EmulatorService {

  private val simulation = new RgripSimulation()

  def process(command: String)(onResponse: String => Unit): Unit = {

    if (command.startsWith("MOVE")) {

      val target = command
        .replace("MOVE", "")
        .trim
        .toInt

      simulation.moveTo(target) { position =>
        onResponse(position.toString)
      }

    } else {
      onResponse("ERROR: Unknown command")
    }
  }
}