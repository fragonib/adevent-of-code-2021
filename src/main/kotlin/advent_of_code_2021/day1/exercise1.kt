package advent_of_code_2021.day1

import java.io.File

fun main(args: Array<String>) {

    val fileName = "src/main/kotlin/advent_of_code_2021/day1/input1.txt"
    val lines = File(fileName).readLines()
        .asSequence()
        .map { it.toInt() }

    val result = lines
        .zipWithNext { a, b -> isIncreasing(b, a) }
        .filter { it }
        .count()

    println(result)

}

private fun isIncreasing(b: Int, a: Int) = b > a