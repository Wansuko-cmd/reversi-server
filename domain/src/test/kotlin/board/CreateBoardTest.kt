@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package board

import Board
import PieceCount
import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CreateBoardTest {
    @Test
    fun Boardのサイズを指定すると駒を2つずつ置いた正方形のBoardを生成する() {
        val board = Board.create(8)
        assertThat(board[board.Coordinate(3, 3)]).isEqualTo(Cell.Piece.White)
        assertThat(board[board.Coordinate(4, 3)]).isEqualTo(Cell.Piece.Black)
        assertThat(board[board.Coordinate(3, 4)]).isEqualTo(Cell.Piece.Black)
        assertThat(board[board.Coordinate(4, 4)]).isEqualTo(Cell.Piece.White)
    }

    @Test
    fun サイズが2未満である場合は駒をおかない() {
        val board = Board.create(1)
        assertThat(board.count()).isEqualTo(PieceCount(black = 0, white = 0))
    }

    @Test
    fun サイズが奇数の場合は中心から右下にズレるように駒をおく() {
        val board = Board.create(9)
        assertThat(board[board.Coordinate(4, 4)]).isEqualTo(Cell.Piece.White)
        assertThat(board[board.Coordinate(4, 5)]).isEqualTo(Cell.Piece.Black)
        assertThat(board[board.Coordinate(5, 4)]).isEqualTo(Cell.Piece.Black)
        assertThat(board[board.Coordinate(5, 5)]).isEqualTo(Cell.Piece.White)
    }
}
