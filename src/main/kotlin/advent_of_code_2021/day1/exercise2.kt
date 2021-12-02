package advent_of_code_2021.day1

import java.io.File

fun main(args: Array<String>) {

    val fileName = "src/main/kotlin/advent_of_code_2021/day1/input2.txt"
    val lines = File(fileName).readLines()
        .asSequence()
        .map { it.toInt() }

    val aggregated = lines
        .windowed(size = 3, step = 1)
        .map { it.sum() }

    val delta = aggregated
        .zipWithNext { previous, next -> isIncreasing(previous, next) }
        .filter { it }
        .count()

    println(delta)

}

private fun isIncreasing(a: Int, b: Int) = b > a