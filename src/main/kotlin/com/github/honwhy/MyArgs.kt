package com.github.honwhy

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default

class MyArgs(parser: ArgParser) {
    val hostname by parser.storing(
        "-H", "--hostname",
        help = "hostname to test"
    ).addValidator { if (value == ""){
        throw SystemExitException("hostname can not null", -1)
    }
    }

    val startPort by parser.storing(
        "-S", "--start-port",
        help = "the port on which the scanning starts"
    ) {
        toInt()
    }.default(80)

    val endPort by parser.storing(
        "-E", "--end-port",
        help = "the port on which the scanning starts"
    ) {
        toInt()
    }.default(100)

    val timeout by parser.storing(
        "-t", "--timeout",
        help = "timeout"

    ) {
        toInt()
    }.default(200)

}