package advent_of_code_2021.day6.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day6/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    var lanternPopulation = parseLanternFish(inputSource)
    for (day in 0 until 80) {
        lanternPopulation = lanternPopulation
            .flatMap { l ->
                when (l) {
                    0 -> listOf(6, 8)
                    else -> listOf(l-1)
                }
            }
    }
    return lanternPopulation.size
}

private fun parseLanternFish(inputSource: String): List<Int> {
    return parseInput(inputSource).first().split(",").map(String::toInt)
}