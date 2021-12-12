package advent_of_code_2021.day9.part2

import advent_of_code_2021.day9.part1.*
import org.assertj.core.api.Assertions.assertThat


fun main() {
    val result = resolve("day9/part2/input.txt")
    println(result)
    assertThat(result).isEqualTo(1156884)
}

internal fun resolve(inputSource: String): Int {
    val floorHeights = parseFloorHeights(inputSource)
    return calculateLowerPoints(floorHeights)
        .map { basinSize(it, floorHeights) }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, new -> acc * new }
}

internal fun basinSize(lowestPoint: Coordinate, floorHeights: List<List<Int>>): Int {
    val floorSize = Coordinate(x = floorHeights.first().size, y = floorHeights.size)
    val basinPoints = mutableListOf(lowestPoint)

    var nextPoints = setOf(lowestPoint)
    var targetHeight: Int
    do {
        targetHeight = floorHeights.coord(nextPoints.first()) + 1
        if (targetHeight == 9) break

        nextPoints = nextPoints
            .flatMap { point ->
                adjacentsPoints(point, floorSize)
                    .filter { floorHeights.coord(it) == targetHeight }
            }
            .toSet()

        basinPoints.addAll(nextPoints)

    } while (nextPoints.isNotEmpty())

    return basinPoints.size
}