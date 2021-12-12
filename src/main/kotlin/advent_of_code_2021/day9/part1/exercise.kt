package advent_of_code_2021.day9.part1

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions.assertThat


fun main() {
    val result = resolve("day9/part1/input.txt")
    println(result)
    assertThat(result).isEqualTo(504)
}

internal fun resolve(inputSource: String): Int {
    val floorMap = parseFloorHeights(inputSource)
    return calculateLowerPoints(floorMap)
        .map { floorMap.coord(it) }
        .sumOf { floorHeight -> riskLevel(floorHeight) }
}

internal fun calculateLowerPoints(floorMap: FloorMap): Sequence<Coordinate> {
    return floorMap.scan()
        .map { targetPoint ->
            val size = Coordinate(y = floorMap.size, x = floorMap.first().size)
            val adjacentsPoints = adjacentsPoints(targetPoint, size)
            Pair(targetPoint, adjacentsPoints)
        }
        .filter { (targetPoint, adjacentsPoints) ->
            val floorHeight = floorMap.coord(targetPoint)
            val adjacentsHeights = adjacentsPoints.map { floorMap.coord(it) }
            adjacentsHeights.minOf { it } > floorHeight
        }
        .map { it.first }
}

internal fun riskLevel(height: Int): Int = 1 + height

internal data class Coordinate(val x: Int, val y: Int)

internal fun FloorMap.coord(coord: Coordinate): Int {
    return this[coord.y][coord.x]
}

internal typealias FloorMap = List<List<Int>>

internal fun FloorMap.scan(): Sequence<Coordinate> {
    val size = Coordinate(x = this.first().size, y = this.size)
    return (0 until size.y).asSequence().flatMap { rowNumber ->
        (0 until size.x).asSequence()
            .map { columnNumber -> Coordinate(x = columnNumber, y = rowNumber) }
    }
}

internal fun adjacentsPoints(targetPoint: Coordinate, boardSize: Coordinate): Sequence<Coordinate> {
    return sequenceOf(
        targetPoint.copy(x = targetPoint.x + 1),
        targetPoint.copy(x = targetPoint.x - 1),
        targetPoint.copy(y = targetPoint.y + 1),
        targetPoint.copy(y = targetPoint.y - 1),
    )
        .filter { it.x >= 0 && it.y >= 0 }
        .filter { it.x < boardSize.x && it.y < boardSize.y }
}

internal fun parseFloorHeights(inputSource: String): FloorMap {
    return parseInput(inputSource)
        .map { line -> line.toList().map { it.digitToInt() } }
        .toList()
}