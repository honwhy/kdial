package com.github.honwhy

import com.xenomachina.argparser.ArgParser
import kotlinx.coroutines.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList

fun main(args: Array<String>) {

    val parsedArgs = ArgParser(args).parseInto(::MyArgs)

    val ports = CopyOnWriteArrayList<Int>()
    runBlocking {

        (parsedArgs.startPort..parsedArgs.endPort).map { port -> CoroutineScope(Dispatchers.Default).async {
            val flag = isOpen(parsedArgs.hostname, port, parsedArgs.timeout)
            if (flag) {
                ports.add(port)
            }
        }
        }.awaitAll()
    }
    println(ports)
}

fun isOpen(host: String, port: Int, timeout: Int): Boolean {
    try {
        Socket().use { socket ->
            socket.connect(InetSocketAddress(host, port), timeout)
            return socket.isConnected
        }
    } catch (e: IOException) { //ignore
        return false
    }
}

