@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package board

import Board
import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class BoardCoordinateTest {
    private val board = Board.create(8)

    @Test
    fun 特定の座標の左側を取得する() {
        assertThat(board.Coordinate(3, 3).left).isEqualTo(board.Coordinate(3, 2))
        assertThat(board.Coordinate(3, 0).left).isNull()
    }

    @Test
    fun 特定の座標の上側を取得する() {
        assertThat(board.Coordinate(3, 3).up).isEqualTo(board.Coordinate(2, 3))
        assertThat(board.Coordinate(0, 3).up).isNull()
    }

    @Test
    fun 特定の座標の右側を取得する() {
        assertThat(board.Coordinate(3, 3).right).isEqualTo(board.Coordinate(3, 4))
        assertThat(board.Coordinate(3, 7).right).isNull()
    }

    @Test
    fun 特定の座標の下側を取得する() {
        assertThat(board.Coordinate(3, 3).down).isEqualTo(board.Coordinate(4, 3))
        assertThat(board.Coordinate(7, 3).down).isNull()
    }
}
