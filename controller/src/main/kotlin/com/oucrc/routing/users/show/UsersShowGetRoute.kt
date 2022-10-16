package com.oucrc.routing.users.show

import com.oucrc.ext.getParameter
import com.oucrc.serializable.ExceptionSerializable
import com.oucrc.serializable.UserSerializable
import com.wsr.result.consume
import com.wsr.result.flatMap
import com.wsr.result.mapBoth
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject
import user.GetUserByIdUseCase
import user.UserId

fun Route.usersShowGet(path: String, param: String) {
    val getUserByIdUseCase by inject<GetUserByIdUseCase>()

    get(path) {
        call.getParameter<String>(param)
            .flatMap { id -> getUserByIdUseCase(UserId(id)) }
            .mapBoth(
                success = { user -> UserSerializable.from(user) },
                failure = { ExceptionSerializable.from(it) },
            )
            .consume(
                success = { user -> call.respond(user) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}
