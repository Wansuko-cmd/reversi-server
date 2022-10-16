package com.oucrc.routing.users.index

import com.oucrc.ext.getRequest
import com.oucrc.serializable.ExceptionSerializable
import com.wsr.result.consume
import com.wsr.result.flatMap
import com.wsr.result.mapFailure
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.SerialName
import org.koin.ktor.ext.inject
import user.CreateUserUseCase
import user.UserName

fun Route.usersIndexPost(path: String) {
    val createUserUseCase by inject<CreateUserUseCase>()

    post(path) {
        call.getRequest<UsersIndexPostRequest>("Invalid request.")
            .flatMap { request -> createUserUseCase(UserName(request.userName)) }
            .mapFailure { ExceptionSerializable.from(it) }
            .consume(
                success = { user -> call.respond(user) },
                failure = { (message, status) -> call.respond(status, message) }
            )
    }
}

@kotlinx.serialization.Serializable
data class UsersIndexPostRequest(
    @SerialName("user_name") val userName: String,
)
