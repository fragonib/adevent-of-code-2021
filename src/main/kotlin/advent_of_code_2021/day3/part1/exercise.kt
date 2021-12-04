package advent_of_code_2021.day3.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day3/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val reportLines = parseInput(inputSource)
    val gammaRate = calculateGammaRate(reportLines)
    val epsilonRate = calculateEpsilonRate(reportLines)
    return gammaRate * epsilonRate
}

internal fun calculateGammaRate(lines: Sequence<String>): Int {
    val columnNumber = lines.firstOrNull()!!.length
    return (0 until columnNumber)
        .map { column -> frequentCharOnColumn(lines, column, Selector.MORE) }
        .joinToString(separator = "")
        .toInt(radix = 2)
}

internal fun calculateEpsilonRate(lines: Sequence<String>): Int {
    val columnNumber = lines.firstOrNull()!!.length
    return (0 until columnNumber)
        .map { column -> frequentCharOnColumn(lines, column, Selector.LESS) }
        .joinToString(separator = "")
        .toInt(radix = 2)
}

enum class Selector(val comparator: Comparator<Map.Entry<Char, Int>>) {
    MORE(compareBy { it.value }),
    LESS(MORE.comparator.reversed())
}

private fun frequentCharOnColumn(lines: Sequence<String>, column: Int, selector: Selector): Char {
    return lines
        .map { it[column] }
        .groupingBy { it }.eachCount()
        .entries
        .maxWithOrNull(selector.comparator)!!
        .key
}
