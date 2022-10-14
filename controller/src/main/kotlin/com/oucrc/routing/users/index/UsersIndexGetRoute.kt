package com.oucrc.routing.users.index

import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.UserSerializable
import com.wsr.result.consume
import com.wsr.result.mapBoth
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import user.GetUsersUseCase

fun Route.usersIndexGet(path: String) {
    val getUsersUseCase by inject<GetUsersUseCase>()

    get(path) {
        getUsersUseCase()
            .mapBoth(
                success = { users -> users.map { UserSerializable.from(it) } },
                failure = { ExceptionSerializable.from(it) }
            )
            .consume(
                success = { users -> call.respond(users) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}