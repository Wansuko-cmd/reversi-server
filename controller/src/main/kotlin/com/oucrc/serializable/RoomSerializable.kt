package com.oucrc.serializable

import room.RoomUseCaseModel

@kotlinx.serialization.Serializable
data class RoomSerializable(
    val id: String,
    val black: UserSerializable,
    val white: UserSerializable,
    val next: UserSerializable?,
    val board: List<List<Int>>,
) {
    companion object {
        fun from(roomUseCaseModel: RoomUseCaseModel) =
            RoomSerializable(
                id = roomUseCaseModel.id.value,
                black = UserSerializable.from(roomUseCaseModel.black),
                white = UserSerializable.from(roomUseCaseModel.white),
                next = roomUseCaseModel.next?.let { UserSerializable.from(it) },
                board = boardSerializable(roomUseCaseModel.board),
            )
    }
}
