package room

import Board
import user.User
import java.util.UUID

class Room private constructor(
    val id: RoomId,
    val black: User,
    val white: User,
    val board: Board,
    val next: Cell.Piece?,
) {
    fun place(row: Int, column: Int): Room {
        if (next == null) return this
        val coordinate = board.Coordinate(row, column)
        if (!board.isPlaceable(coordinate, next)) return this

        val placedBoard = board.place(coordinate, next)
        val next = when {
            placedBoard.placeableCoordinates(next.reverse()).isNotEmpty() -> next.reverse()
            placedBoard.placeableCoordinates(next).isNotEmpty() -> next
            else -> null
        }
        return Room(
            id = this.id,
            black = black,
            white = white,
            next = next,
            board = placedBoard
        )
    }

    companion object {
        fun create(black: User, white: User) = Room(
            id = RoomId(UUID.randomUUID().toString()),
            black = black,
            white = white,
            next = Cell.Piece.Black,
            board = Board.create(20),
        )
    }
}

@JvmInline
value class RoomId(val value: String)