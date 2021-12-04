package advent_of_code_2021.day1.part2

import advent_of_code_2021.day1.part1.resolve
import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions


fun main() {
    println(resolve("day1/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val depths = parseInput(inputSource).map { it.toInt() }
    return countIncreasesComparingWithNext(depths)
}

internal fun countIncreasesComparingWithNext(depths: Sequence<Int>): Int {
    return aggregateByWindowSize3(depths)
        .zipWithNext { previous, next -> isIncreasing(previous, next) }
        .filter { it }
        .count()
}

internal fun aggregateByWindowSize3(depths: Sequence<Int>): Sequence<Int> {
    return depths
        .windowed(size = 3, step = 1)
        .map { it.sum() }
}

internal fun isIncreasing(a: Int, b: Int) = b > a