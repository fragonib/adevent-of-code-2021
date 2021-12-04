package advent_of_code_2021.shared

import java.io.File

fun parseInput(inputFileName: String): Sequence<String> {
    return File("src/main/kotlin/advent_of_code_2021/$inputFileName")
        .readLines()
        .asSequence()
}