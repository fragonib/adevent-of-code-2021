package advent_of_code_2021.day1.part2

import org.assertj.core.api.Assertions
import java.io.File

fun main(args: Array<String>) {
    val lines = parseNumbers("src/main/kotlin/advent_of_code_2021/day1/part2/input.txt")
    val result = countIncreasesBy2(lines)
    println(result)
    Assertions.assertThat(result).isEqualTo(1797)
}

private fun countIncreasesBy2(lines: Sequence<Int>): Int {
    val aggregated = lines
        .windowed(size = 3, step = 1)
        .map { it.sum() }
    return aggregated
        .zipWithNext { previous, next -> isIncreasing(previous, next) }
        .filter { it }
        .count()
}

private fun parseNumbers(fileName: String): Sequence<Int> {
    return File(fileName).readLines()
        .asSequence()
        .map { it.toInt() }
}

private fun isIncreasing(a: Int, b: Int) = b > a