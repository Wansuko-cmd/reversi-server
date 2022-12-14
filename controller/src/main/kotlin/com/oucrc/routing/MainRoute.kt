package com.oucrc.routing

import com.oucrc.routing.rooms.index.roomsIndexGet
import com.oucrc.routing.rooms.index.roomsIndexPost
import com.oucrc.routing.rooms.show.roomsShowGet
import com.oucrc.routing.rooms.show.roomsShowPost
import com.oucrc.routing.scores.show.scoreShowGetRoute
import com.oucrc.routing.users.index.usersIndexGet
import com.oucrc.routing.users.index.usersIndexPost
import com.oucrc.routing.users.show.usersShowGet
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.mainRoute() {
    routing {
        route(Routing.Room.path) {
            roomsIndexGet(path = Routing.Room.Index.path)
            roomsIndexPost(path = Routing.Room.Index.path)
            roomsShowGet(
                path = Routing.Room.Show.path,
                param = Routing.Room.Show.roomId,
            )
            roomsShowPost(
                path = Routing.Room.Show.path,
                param = Routing.Room.Show.roomId,
            )
        }
        route(Routing.User.path) {
            usersIndexGet(path = Routing.User.Index.path)
            usersIndexPost(path = Routing.User.Index.path)
            usersShowGet(
                path = Routing.User.Show.path,
                param = Routing.User.Show.userId,
            )
        }
        route(Routing.Score.path) {
            scoreShowGetRoute(
                path = Routing.Score.Show.path,
                param = Routing.Score.Show.userId,
            )
        }
        get("") { call.respond("Hello World") }
    }
}
