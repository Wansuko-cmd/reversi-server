package com.oucrc.routing

import com.oucrc.routing.rooms.index.roomsIndexGet
import com.oucrc.routing.rooms.show.roomsShowGet
import com.oucrc.routing.rooms.show.roomsShowPost
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.mainRoute() {
    routing {
        route(Routing.Room.path) {
            roomsIndexGet(path = Routing.Room.Index.path)
            roomsShowGet(
                path = Routing.Room.Show.path,
                params = Routing.Room.Show.roomId,
            )
            roomsShowPost(
                path = Routing.Room.Show.path,
                params = Routing.Room.Show.roomId
            )
        }
        get("") { call.respond("Hello World") }
    }
}