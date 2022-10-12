package com.oucrc.serializable

import room.BoardUseCaseModel

@kotlinx.serialization.Serializable
class BoardSerializable(
    val board: List<List<Int>>,
) {
    companion object {
        fun from(boardUseCaseModel: BoardUseCaseModel) =
            boardUseCaseModel
                .columns
                .map { row -> row.map { column -> column.toInt() } }
                .let { BoardSerializable(it) }
    }
}