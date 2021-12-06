package advent_of_code_2021.day4.part2

import advent_of_code_2021.shared.parseInput

private const val BOARD_SIZE = 5
private const val MARKED = -1


fun main() {
    println(resolve("day4/part2/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val (drawnNumbers, initialBoards) = parseBingoGame(inputSource)
    var nextBoards = initialBoards
    for (drawnNumber in drawnNumbers) {
        val markedBoards = nextBoards.map { board -> board.mark(drawnNumber) }
        val nonWinnerBoards = markedBoards.filter { !it.isWinner() }
        if (nonWinnerBoards.none())
            return calculateResult(markedBoards.first(), drawnNumber)
        nextBoards = nonWinnerBoards
    }
    throw IllegalArgumentException("Not all boards have won")
}

internal fun calculateResult(winnerBoard: Board, winnerNumber: Int): Int {
    return winnerBoard.sumOfNotMarked() * winnerNumber
}

internal fun parseBingoGame(inputSource: String): Pair<Sequence<Int>, List<Board>> {
    val lines = parseInput(inputSource)
    val winnerNumbers = lines.first().split(",").map { it.toInt() }.asSequence()
    val boards =
        lines.asSequence().drop(1)
            .filter { line -> line.isNotBlank() }
            .map { boardRow ->
                boardRow
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
            .windowed(size = BOARD_SIZE, step = BOARD_SIZE)
            .map { Board(it) }
            .toList()
    return Pair(winnerNumbers, boards)
}

internal data class Board(
    val board: List<List<Int>>
) {

    fun mark(drawn: Int): Board {
        val board1 = board.map { row ->
            row.map { if (it == drawn) MARKED else it }
        }
        return Board(board1)
    }

    fun isWinner(): Boolean {
        val anyRow =
            board.any { row ->
                row.all { it == MARKED }
            }
        val anyColumn =
            (0 until BOARD_SIZE)
                .any { columnNumber ->
                    board
                        .map { row -> row[columnNumber] }
                        .all { it == MARKED }
                }
        return anyRow || anyColumn
    }

    fun sumOfNotMarked(): Int =
        board.flatten()
            .filter { it != -1 }
            .sum()

    override fun toString(): String {
        return board.joinToString(separator = "\n") { it.joinToString(separator = " ") }
    }

}