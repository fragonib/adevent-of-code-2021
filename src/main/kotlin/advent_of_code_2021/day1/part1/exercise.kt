package advent_of_code_2021.day1.part1

import org.assertj.core.api.Assertions.assertThat
import java.io.File

fun main(args: Array<String>) {
    val lines = parseNumbers("src/main/kotlin/advent_of_code_2021/day1/part1/input.txt")
    val result = countIncreasesBy2(lines)
    println(result)
    assertThat(result).isEqualTo(1766)
}

private fun countIncreasesBy2(lines: Sequence<Int>): Int {
    return lines
        .windowed(size = 2, step = 1)
        .map { (a, b) -> isIncreasing(a, b) }
        .filter { it }
        .count()
}

private fun parseNumbers(fileName: String): Sequence<Int> {
    return File(fileName).readLines()
        .asSequence()
        .map { it.toInt() }
}

private fun isIncreasing(a: Int, b: Int) = b > a