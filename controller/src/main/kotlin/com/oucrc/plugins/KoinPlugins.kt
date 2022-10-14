package com.oucrc.plugins

import RoomRepositoryImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import room.GetRoomByIdUseCase
import room.GetRoomsUseCase
import room.RoomRepository
import room.UpdateRoomUseCase

fun Application.koinPlugins() {
    val module = module {
        /*** UseCase ***/
        single<GetRoomsUseCase> { GetRoomsUseCase(get()) }
        single<GetRoomByIdUseCase> { GetRoomByIdUseCase(get()) }
        single<UpdateRoomUseCase> { UpdateRoomUseCase(get()) }

        /*** Repository ***/
        single<RoomRepository> { RoomRepositoryImpl() }
    }

    install(Koin) { modules(module) }
}