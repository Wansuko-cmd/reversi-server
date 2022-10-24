package room

import Board
import DomainException
import com.wsr.result.ApiResult
import user.User
import user.UserId
import java.util.UUID

class Room private constructor(
    val id: RoomId,
    val black: User,
    val white: User,
    val board: Board,
    val next: Cell.Piece?,
) {
    fun isNextUser(userId: UserId): Boolean = when (next) {
        is Cell.Piece.Black -> userId == black.id
        is Cell.Piece.White -> userId == white.id
        else -> false
    }

    fun place(row: Int, column: Int): ApiResult<Room, DomainException> {
        if (next == null) {
            return ApiResult.Failure(DomainException.FinishedGameException("This is finished game."))
        }
        val coordinate = board.Coordinate(row, column)
        if (!board.isPlaceable(coordinate, next)) {
            return ApiResult.Failure(
                DomainException.NotPlaceableCoordinateException(
                    message = "row : $row, column : $column isn't placeable.",
                ),
            )
        }

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
        ).let { ApiResult.Success(it) }
    }

    companion object {
        fun create(black: User, white: User) = Room(
            id = RoomId(UUID.randomUUID().toString()),
            black = black,
            white = white,
            next = Cell.Piece.Black,
            board = Board.create(20),
        )

        fun reconstruct(
            id: RoomId,
            black: User,
            white: User,
            next: Cell.Piece?,
            board: Board
        ) = Room(
            id = id,
            black = black,
            white = white,
            next = next,
            board = board,
        )
    }
}

@JvmInline
value class RoomId(val value: String)
