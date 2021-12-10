package advent_of_code_2021.day8.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day8/part1/input.txt"))
}

internal val SINGULAR_DIGITS_SEGMENTS_COUNT = mapOf(
    1 to 2,
    4 to 4,
    7 to 3,
    8 to 7
)

internal fun resolve(inputSource: String): Int {
    val segments = parseSegments(inputSource)
    return segments
        .map { it.second }
        .flatMap { it.asIterable() }
        .filter { it.length in SINGULAR_DIGITS_SEGMENTS_COUNT.values }
        .count()
}

private fun parseSegments(inputSource: String): Sequence<Pair<List<String>, List<String>>> {
    return parseInput(inputSource)
        .map { line -> line.split(" | ", limit = 2) }
        .map { segments -> segments.map { it.split(" ") } }
        .map { (a, b) -> Pair(a, b) }
}