package com.oucrc.routing.users.index

import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import user.GetUsersUseCase

fun Route.usersIndexGet(path: String) {
    val getUsersUseCase by inject<GetUsersUseCase>()
}