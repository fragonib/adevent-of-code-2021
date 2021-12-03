package advent_of_code_2021.day3.part1

import java.io.File

fun main(args: Array<String>) {
    val lines = parseNumbers("src/main/kotlin/advent_of_code_2021/day3/part1/input.txt")
    val result = resolve(lines)
    println(result)
}

private fun parseNumbers(fileName: String): Sequence<String> {
    return File(fileName).readLines()
        .asSequence()
}

internal fun resolve(lines: Sequence<String>): Int {
    val gammaRate = calculateGammaRate(lines)
    val epsilonRate = calculateEpsilonRate(lines)
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
