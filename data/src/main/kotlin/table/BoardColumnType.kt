package table

import Board
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table

class BoardColumnType : IColumnType {
    override var nullable: Boolean = false
    override fun sqlType(): String = "TEXT"
    override fun valueFromDB(value: Any): Board =
        value.toString()
            .split("|")
            .map { str ->
                str.split(",")
                    .map { it.toInt() }
                    .map { Cell.from(it) }
            }
            .map { Column.reconstruct(it) }
            .let { columns -> Board.reconstruct(columns) }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is Board -> {
            (0 until value.height).joinToString(separator = "|") { row ->
                (0 until value.width).joinToString(separator = ",") { column ->
                    value[value.Coordinate(row, column)]
                        .toInt()
                        .toString()
                }
            }
        }
        else -> throw DomainException.SystemException("convert error.", null)
    }
}

fun Table.board(name: String) = registerColumn<Board>(name, BoardColumnType())
