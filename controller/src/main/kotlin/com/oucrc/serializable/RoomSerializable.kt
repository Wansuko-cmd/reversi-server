package com.oucrc.serializable

import room.RoomUseCaseModel

@kotlinx.serialization.Serializable
data class RoomSerializable(
    val id: String,
    val black: String,
    val white: String,
    val next: Int,
    val board: BoardSerializable,
) {
    companion object {
        fun from(roomUseCaseModel: RoomUseCaseModel) =
            RoomSerializable(
                id = roomUseCaseModel.id.value,
                black = roomUseCaseModel.black.id.value,
                white = roomUseCaseModel.white.id.value,
                next = roomUseCaseModel.next?.toInt() ?: 0,
                board = BoardSerializable.from(roomUseCaseModel.board),
            )
    }
}
