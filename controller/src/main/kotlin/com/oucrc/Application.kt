package com.oucrc

import com.oucrc.plugins.installPlugins
import com.oucrc.routing.mainRoute
import io.ktor.server.application.Application

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.main() {
    installPlugins()

    mainRoute()
}
