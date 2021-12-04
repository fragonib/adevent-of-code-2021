package advent_of_code_2021.day3.part2

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day3/part2/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val reportLines = parseInput(inputSource)
    val oxygenGeneratorRating = calculateOxygenGeneratorRating(reportLines)
    val co2ScrubberRating = calculateCo2ScrubberRating(reportLines)
    return oxygenGeneratorRating * co2ScrubberRating
}

internal fun calculateOxygenGeneratorRating(reportLines: Sequence<String>): Int {
    return findReportLines(reportLines, Selector.MORE).toInt(radix = 2)
}

internal fun calculateCo2ScrubberRating(reportLines: Sequence<String>): Int {
    return findReportLines(reportLines, Selector.LESS).toInt(radix = 2)
}

internal fun findReportLines(report: Sequence<String>, selector: Selector, column: Int = 0): String {
    val bitCriteria = buildBitCriteria(report, selector, column)
    val filtered = report.filter(bitCriteria)

    val head = filtered.first()
    val rest = filtered.drop(1)
    return when {
        rest.none() -> head
        else -> findReportLines(filtered, selector, column + 1)
    }
}

internal fun buildBitCriteria(report: Sequence<String>, selector: Selector, column: Int): (String) -> Boolean {
    val chars = report.map { it[column] }
    val frequentChar = frequentChar(chars, selector)
    return { s: String -> s[column] == frequentChar }
}

internal enum class Selector(val comparator: Comparator <Int>) {
    MORE(compareBy { it }),
    LESS(MORE.comparator.reversed())
}

internal fun frequentChar(chars: Sequence<Char>, selector: Selector): Char {
    val histogram = chars
        .groupingBy { it }
        .eachCount()
        .withDefault { 0 }
    val zeros = histogram.getValue('0')
    val ones = histogram.getValue('1')
    val comparison = selector.comparator.compare(ones, zeros)
    return when {
        comparison < 0 -> '0'
        comparison > 0 -> '1'
        else -> if (selector == Selector.MORE) '1' else '0'
    }
}
