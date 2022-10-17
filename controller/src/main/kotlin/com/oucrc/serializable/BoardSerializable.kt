package com.oucrc.serializable

import room.BoardUseCaseModel

typealias BoardSerializable = List<List<Int>>

fun boardSerializable(board: BoardUseCaseModel): BoardSerializable =
    board
        .columns
        .map { row -> row.map { column -> column.toInt() } }
