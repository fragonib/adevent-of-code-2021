package advent_of_code_2021.day5.part1

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions.assertThat
import java.lang.Integer.max
import java.lang.Integer.min


fun main() {
    val intersections = resolve("day5/part1/input.txt")
    println(intersections)
    assertThat(intersections).isEqualTo(7085)
}

internal fun resolve(inputSource: String): Int {
    val lines = parseHydrothermalVents(inputSource)
    val straightLines = lines
        .filter(Line::isStraight)
        .map(Line::normalize)
    return intersectPoints(straightLines).count()
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

    val bothHorizontal = sequenceOf(first, second).all { it.isHorizontal() }
    val sameRow = first.start.y == second.start.y
    val bothVertical = sequenceOf(first, second).all { it.isVertical() }
    val sameColumn = first.start.x == second.start.x

    return when {
        bothHorizontal && sameRow ->
            IntRange(max(first.start.x, second.start.x), min(first.end.x, second.end.x))
                .map { Point(it, first.start.y) }
                .toSet()

        bothVertical && sameColumn ->
            IntRange(max(first.start.y, second.start.y), min(first.end.y, second.end.y))
                .map { Point(first.start.x, it) }
                .toSet()

        !bothVertical && !bothHorizontal -> {
            val (vLine, hLine) = if (first.isVertical()) Pair(first, second) else Pair(second, first)
            val intersect = IntRange(vLine.start.y, vLine.end.y).contains(hLine.start.y) &&
                    IntRange(hLine.start.x, hLine.end.x).contains(vLine.start.x)
            return if (intersect) setOf(Point(vLine.start.x, hLine.start.y)) else setOf()
        }

        else -> setOf()
    }
}

internal data class Point(val x: Int, val y: Int) {
    companion object {
        fun from(pointLiteral: String): Point {
            val coordinates = pointLiteral.split(",")
            return Point(coordinates[0].toInt(), coordinates[1].toInt())
        }
    }

    override fun toString(): String {
        return "$x,$y"
    }
}

internal data class Line(val start: Point, val end: Point) {
    companion object {
        fun from(lineLiteral: String): Line {
            val points = lineLiteral.split(" -> ").map(Point.Companion::from)
            return Line(start = points[0], end = points[1])
        }
    }

    fun isStraight(): Boolean = isHorizontal() || isVertical()
    fun isHorizontal(): Boolean = start.y == end.y
    fun isVertical(): Boolean = start.x == end.x

    fun normalize(): Line = when {
        start.x > end.x -> Line(this.end, this.start)
        start.x == end.x && start.y > end.y -> Line(this.end, this.start)
        else -> this
    }

    fun points(): Sequence<Point> {
        return generateSequence(start) { p ->
                val xStep = when {
                    start.x == end.x -> 0
                    start.x > end.x -> -1
                    else -> 1
                }
                val yStep = when {
                    start.y == end.y -> 0
                    start.y > end.y -> -1
                    else -> 1
                }
                Point(p.x + xStep, p.y + yStep)
            }
            .takeWhile { it != end } + sequenceOf(end)
    }

    override fun toString(): String {
        return "$start -> $end"
    }

}
