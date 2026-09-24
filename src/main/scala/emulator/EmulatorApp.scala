package emulator

import com.typesafe.config.ConfigFactory
import emulator.server.TcpServer

object EmulatorApp {
  def start(): Unit = {
    val config = ConfigFactory.load()
    val port = config
      .getConfig("emulator.tcp")
      .getInt("port")
    val tcpServer = new TcpServer(port)
    tcpServer.start()
  }
}