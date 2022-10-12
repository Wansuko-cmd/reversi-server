package com.oucrc.routing

import com.oucrc.routing.room.index.roomIndex
import com.oucrc.routing.room.show.roomShow
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.mainRoute() {
    routing {
        route(Routing.Room.path) {
            roomIndex(path = Routing.Room.Index.path)
            roomShow(
                path = Routing.Room.Show.path,
                params = Routing.Room.Show.roomId,
            )
        }
        get("") { call.respond("Hello World") }
    }
}