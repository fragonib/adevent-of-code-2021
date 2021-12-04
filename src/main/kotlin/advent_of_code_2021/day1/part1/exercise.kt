package advent_of_code_2021.day1.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day1/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val depths = parseInput(inputSource).map { it.toInt() }
    return countIncreasesComparingWithNext(depths)
}

internal fun countIncreasesComparingWithNext(depths: Sequence<Int>): Int {
    return depths
        .zipWithNext { previous, next -> isIncreasing(previous, next) }
        .filter { it }
        .count()
}

internal fun isIncreasing(a: Int, b: Int) = b > a