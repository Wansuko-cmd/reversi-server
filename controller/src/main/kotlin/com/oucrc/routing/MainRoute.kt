package com.oucrc.routing

import com.oucrc.routing.room.index.roomIndex
import com.oucrc.routing.room.show.roomShow
import io.ktor.server.application.Application
import io.ktor.server.routing.*

fun Application.mainRoute() {
    routing {
        route(Routing.Room.path) {
            roomIndex(Routing.Room.Index.path)
            roomShow(Routing.Room.Show.path)
        }
    }
}