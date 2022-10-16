package com.oucrc.plugins

import RoomRepositoryImpl
import UserRepositoryImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import room.GetRoomByIdUseCase
import room.GetRoomsUseCase
import room.PlacePieceInRoomUseCase
import room.RoomRepository
import user.CreateUserUseCase
import user.GetUserByIdUseCase
import user.GetUsersUseCase
import user.UserRepository

fun Application.koinPlugins() {
    val module = module {
        /*** UseCase ***/
        // Room
        single<GetRoomsUseCase> { GetRoomsUseCase(get()) }
        single<GetRoomByIdUseCase> { GetRoomByIdUseCase(get()) }
        single<PlacePieceInRoomUseCase> { PlacePieceInRoomUseCase(get()) }

        // User
        single<GetUsersUseCase> { GetUsersUseCase(get()) }
        single<GetUserByIdUseCase> { GetUserByIdUseCase(get()) }
        single<CreateUserUseCase> { CreateUserUseCase(get()) }

        /*** Repository ***/
        single<RoomRepository> { RoomRepositoryImpl() }
        single<UserRepository> { UserRepositoryImpl() }
    }

    install(Koin) { modules(module) }
}
