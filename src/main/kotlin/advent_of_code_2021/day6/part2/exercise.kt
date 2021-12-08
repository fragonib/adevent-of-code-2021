package advent_of_code_2021.day6.part2

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions.assertThat


fun main() {
    val result = resolve("day6/part2/input.txt")
    println(result)
    assertThat(result).isEqualTo(1749945484935)
}

internal fun resolve(inputSource: String): Long {
    val lanternFishPopulation = parseLanternFishPopulation(inputSource)
    val populationHistogram = populationHistogram(lanternFishPopulation)
    return (0 until 256)
        .fold(populationHistogram) { currentPopulation, _ -> nextDayPopulation(currentPopulation) }
        .values.sum()
}

private fun populationHistogram(lanternFishPopulation: List<LanternFishState>): PopulationHistogram {
    return lanternFishPopulation
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }
        .withDefault { 0 }
}

typealias LanternFishState = Int
typealias PopulationHistogram = Map<LanternFishState, Long>

/**
 * Population: 0  1  2  3  4  5    6  7  8
 * Quantity:   1 10 20 30 40 50   60 70 80
 * NextDay:   10 20 30 40 50 60 70+1 80  1
 */
fun nextDayPopulation(currentPopulation: PopulationHistogram): PopulationHistogram {
    return (0..8).associate { key ->
        when (key) {
            6 -> Pair(key, currentPopulation.getValue(0) + currentPopulation.getValue(7))
            8 -> Pair(key, currentPopulation.getValue(0))
            else -> Pair(key, currentPopulation.getValue(key + 1))
        }
    }
}

private fun parseLanternFishPopulation(inputSource: String): List<Int> {
    return parseInput(inputSource).first().split(",").map(String::toInt)
}