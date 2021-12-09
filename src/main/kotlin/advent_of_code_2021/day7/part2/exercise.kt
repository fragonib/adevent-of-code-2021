package advent_of_code_2021.day7.part2

import advent_of_code_2021.shared.parseInput
import kotlin.math.abs


fun main() {
    println(resolve("day7/part2/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val crabsHorizontal = parseCrabs(inputSource)
    val median = crabsHorizontal.sorted()[crabsHorizontal.size / 2]
    return crabsHorizontal.sumOf { abs(it - median) }
}

internal fun parseCrabs(inputSource: String): List<Int> {
    return parseInput(inputSource).first().split(",").map(String::toInt)
}