package advent_of_code_2021.day1.part1

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions.assertThat


fun main() {
    val depths = parseInput("day1/part1/input.txt").map { it.toInt() }
    val result = countIncreasesComparingWithNext(depths)
    println(result)
    assertThat(result).isEqualTo(1766)
}

internal fun countIncreasesComparingWithNext(depths: Sequence<Int>): Int {
    return depths
        .zipWithNext { previous, next -> isIncreasing(previous, next) }
        .filter { it }
        .count()
}

internal fun isIncreasing(a: Int, b: Int) = b > a