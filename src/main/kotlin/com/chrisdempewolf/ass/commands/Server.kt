package com.chrisdempewolf.ass.commands

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler

class Server : Command {

    override fun run(args: Array<String>) {
        Build().run(args)
        println("Starting server on http://localhost:8080")
        System.setProperty("org.eclipse.jetty.LEVEL", "INFO")

        val server = Server()
        val connector = ServerConnector(server)
        connector.port = 8080
        server.addConnector(connector)

        val resourceHandler = ResourceHandler()
        val dir = if (args.size >= 3) args[2] else ""
        resourceHandler.isDirectoriesListed = true
        resourceHandler.resourceBase =  dir + "site/"
        resourceHandler.cacheControl = "no-cache"

        val handlerList = HandlerList()
        handlerList.handlers = arrayOf(resourceHandler)
        server.handler = handlerList
        server.start()
        server.join()
    }
}