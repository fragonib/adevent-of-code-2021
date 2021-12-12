package advent_of_code_2021.day9.part2

import advent_of_code_2021.day9.part1.*
import org.assertj.core.api.Assertions.assertThat


fun main() {
    val result = resolve("day9/part2/input.txt")
    println(result)
    assertThat(result).isEqualTo(1558722)
}

internal fun resolve(inputSource: String): Int {
    val floorMap = parseFloorHeights(inputSource)
    return calculateLowerPoints(floorMap)
        .map { basinSize(it, floorMap) }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, new -> acc * new }
}

internal fun basinSize(lowestPoint: Coordinate, floorMap: FloorMap): Int {
    val floorSize = Coordinate(x = floorMap.first().size, y = floorMap.size)
    val basinPoints = mutableSetOf(lowestPoint)

    val startHeight = floorMap.coord(lowestPoint) + 1
    for (targetHeight in startHeight..8) {
        val discovered = basinPoints
            .flatMap { point ->
                adjacentsPoints(point, floorSize)
                    .filter { floorMap.coord(it) == targetHeight }
            }
            .toSet()
        basinPoints.addAll(discovered)
    }

    return basinPoints.size
}