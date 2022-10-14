package com.oucrc.plugins

import RoomRepositoryImpl
import UserRepositoryImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import room.GetRoomByIdUseCase
import room.GetRoomsUseCase
import room.RoomRepository
import room.PlacePieceInRoomUseCase
import user.UserRepository

fun Application.koinPlugins() {
    val module = module {
        /*** UseCase ***/
        single<GetRoomsUseCase> { GetRoomsUseCase(get()) }
        single<GetRoomByIdUseCase> { GetRoomByIdUseCase(get()) }
        single<PlacePieceInRoomUseCase> { PlacePieceInRoomUseCase(get()) }

        /*** Repository ***/
        single<RoomRepository> { RoomRepositoryImpl() }
        single<UserRepository> { UserRepositoryImpl() }
    }

    install(Koin) { modules(module) }
}