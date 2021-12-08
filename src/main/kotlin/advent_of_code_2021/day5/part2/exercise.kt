package advent_of_code_2021.day5.part2

import advent_of_code_2021.day5.part1.Line
import advent_of_code_2021.day5.part1.Point
import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions


fun main() {
    val intersections = resolve("day5/part2/input.txt")
    println(intersections)
    Assertions.assertThat(intersections).isEqualTo(20271)
}

internal fun resolve(inputSource: String): Int {
    val parsedLines = parseHydrothermalVents(inputSource)
    val hydrotermalLines = parsedLines.map(Line::normalize)
    return intersectPoints(hydrotermalLines).count()
}

internal fun parseHydrothermalVents(inputSource: String): Sequence<Line> {
    return parseInput(inputSource).map(Line.Companion::from)
}

internal fun intersectPoints(lines: Sequence<Line>): Set<Point> {
    val head = lines.first()
    val rest = lines.drop(1)
    return when {
        rest.none() -> setOf()
        else -> {
            val intersections = rest
                .flatMap { line -> intersectPoints(head, line) }
                .toSet()
            return intersections + intersectPoints(rest)
        }
    }
}

internal fun intersectPoints(first: Line, second: Line): Set<Point> {
    return first.points().toSet().intersect(second.points().toSet())
}

