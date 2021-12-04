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

// Gamma
internal fun calculateGammaRate(lines: Sequence<String>): Int {
    val columnNumber = lines.firstOrNull()!!.length
    return (0 until columnNumber)
        .map { column -> moreFrequentCharOnColumn(lines, column) }
        .joinToString(separator = "")
        .toInt(radix = 2)
}

private fun moreFrequentCharOnColumn(lines: Sequence<String>, column: Int): Char =
    lines.map { it[column] }.joinToString(separator = "")
        .let(::moreFrequentChar)

internal fun moreFrequentChar(cadena: String): Char {
    return cadena.groupingBy { it }.eachCount()
        .entries
        .maxByOrNull { it.value }!!
        .key
}

// Epsilon
internal fun calculateEpsilonRate(lines: Sequence<String>): Int {
    val columnNumber = lines.firstOrNull()!!.length
    return (0 until columnNumber)
        .map { column -> lessFrequentCharOnColumn(lines, column) }
        .joinToString(separator = "")
        .toInt(radix = 2)
}

private fun lessFrequentCharOnColumn(lines: Sequence<String>, column: Int): Char =
    lines.map { it[column] }.joinToString(separator = "")
        .let(::lessFrequentChar)

internal fun lessFrequentChar(cadena: String): Char {
    return cadena.groupingBy { it }.eachCount()
        .entries
        .minByOrNull { it.value }!!
        .key
}
