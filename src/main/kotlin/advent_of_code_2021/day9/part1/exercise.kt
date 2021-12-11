package advent_of_code_2021.day9.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day9/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val floorHeights = parseFloorHeights(inputSource)
    return calculateLowerPoints(floorHeights)
        .sumOf { floorHeight -> riskLevel(floorHeight) }
}

private fun calculateLowerPoints(floorHeights: List<List<Int>>): Sequence<Int> {
    val size = Coordinate(y = floorHeights.size, x = floorHeights.first().size)
    return floorHeights.scan()
        .map { targetPoint ->
            val adjacentsPoints = adjacentsPoints(targetPoint, size)
            Pair(targetPoint, adjacentsPoints)
        }
        .map { (targetPoint, adjacentsPoints) ->
            val floorHeight = floorHeights.coord(targetPoint)
            val adjacentsHeights = adjacentsPoints.map { floorHeights.coord(it) }
            Pair(floorHeight, adjacentsHeights)
        }
        .filter { (floorHeight, adjacentsHeights) -> adjacentsHeights.minOf { it } > floorHeight }
        .map { it.first }
}

fun riskLevel(height: Int): Int = 1 + height

data class Coordinate(val x: Int, val y: Int)

fun List<List<Int>>.coord(coord: Coordinate): Int {
    return this[coord.y][coord.x]
}

fun List<List<Int>>.scan(): Sequence<Coordinate> {
    val size = Coordinate(x = this.first().size, y = this.size)
    return (0 until size.y).asSequence().flatMap { rowNumber ->
        (0 until size.x).asSequence()
            .map { columnNumber -> Coordinate(x = columnNumber, y = rowNumber) }
    }
}

fun adjacentsPoints(targetPoint: Coordinate, size: Coordinate): Sequence<Coordinate> {
    return sequenceOf(
        targetPoint.copy(x = targetPoint.x + 1),
        targetPoint.copy(x = targetPoint.x - 1),
        targetPoint.copy(y = targetPoint.y + 1),
        targetPoint.copy(y = targetPoint.y - 1),
    )
        .filter { it.x >= 0 && it.y >= 0 }
        .filter { it.x < size.x && it.y < size.y }
}

private fun parseFloorHeights(inputSource: String): List<List<Int>> {
    return parseInput(inputSource)
        .map { line -> line.toList().map { it.digitToInt() } }
        .toList()
}