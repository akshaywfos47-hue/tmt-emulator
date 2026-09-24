package emulator.server

import emulator.service.EmulatorService
import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.{ServerSocket, Socket}

class TcpServer(port: Int) {

  private val emulatorService = new EmulatorService()

  def start(): Unit = {
    val serverSocket = new ServerSocket(port)

    println(s"Emulator TCP server started on port: $port")

    while (true) {
      println("Waiting for client connection...")

      val clientSocket = serverSocket.accept()

      println(s"Client connected: ${clientSocket.getInetAddress}")

      handleClient(clientSocket)
    }
  }

  private def handleClient(clientSocket: Socket): Unit = {

    val reader =
      new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream)
      )

    val writer =
      new PrintWriter(
        clientSocket.getOutputStream,
        true
      )

    try {

      val command = reader.readLine()

      if (command != null) {

        println(s"Received: $command")

        emulatorService.process(command) { response =>
          writer.println(response)
          println(s"Sent: $response")
        }

        println("Command processing completed")
      }

    } finally {

      println("Closing connection with client")

      clientSocket.close()

      println("Client disconnected")
    }
  }
}