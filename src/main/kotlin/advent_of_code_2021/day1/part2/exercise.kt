package advent_of_code_2021.day1.part2

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions


fun main() {
    val depths = parseInput("day1/part2/input.txt").map { it.toInt() }
    val result = countIncreasesComparingWithNext(depths)
    println(result)
    Assertions.assertThat(result).isEqualTo(1797)
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