package room

import Board
import Cell
import user.UserUseCaseModel

data class RoomUseCaseModel(
    val id: RoomId,
    val black: UserUseCaseModel,
    val white: UserUseCaseModel,
    val next: Cell.Piece?,
    val board: BoardUseCaseModel,
) {
    companion object {
        fun from(room: Room): RoomUseCaseModel =
            RoomUseCaseModel(
                id = room.id,
                black = UserUseCaseModel.from(room.black),
                white = UserUseCaseModel.from(room.white),
                next = room.next,
                board = BoardUseCaseModel.from(room.board),
            )
    }
}

data class BoardUseCaseModel(
    val columns: List<List<Cell>>
) {
    companion object {
        fun from(board: Board): BoardUseCaseModel =
            (0 until board.height).map { row ->
                (0 until board.width).map { column ->
                    board[board.Coordinate(row, column)]
                }
            }.let { BoardUseCaseModel(it) }
    }
}
