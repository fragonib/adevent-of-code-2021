package advent_of_code_2021.solar.aggregate

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Month.*
import java.time.format.DateTimeFormatter

val ZERO = Triple(0, 0, 0)
val histogram = mutableMapOf<Month, Triple<Int, Int, Int>>()

fun main() {

    csvReader { delimiter = ';' }
        .open("src/main/kotlin/advent_of_code_2021/solar/aggregate/input.csv") {
            readAllAsSequence().drop(1).forEach { row ->
                val date = LocalDate.parse(row[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val hour = row[2].toInt()
                val wat = row[3].split(",", limit = 2)[1].toInt()
                addToHistogram(date, hour, wat)
            }
        }

    printHistogram()
}

private fun addToHistogram(date: LocalDate, hour: Int, wat: Int) {
    val month = date.month
    when {
        isHoliday(date) -> addLlano(month, wat)
        hour in 1..8 -> addValle(month, wat)
        hour in 11..14 || hour in 17..22 -> addPunta(month, wat)
        else -> addLlano(month, wat)
    }
}

fun isHoliday(date: LocalDate): Boolean {
    if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY)
        return true
    val holiday2021 = listOf(
        1 to JANUARY,
        6 to JANUARY,
        2 to APRIL,
        1 to MAY,
        12 to OCTOBER,
        1 to NOVEMBER,
        6 to DECEMBER,
        8 to DECEMBER,
        25 to DECEMBER
    )
    return Pair(date.dayOfMonth, date.month) in holiday2021
}

private fun addValle(month: Month, wat: Int) {
    histogram.compute(month) { _, v -> (v ?: ZERO) + Triple(wat, 0, 0) }
}

private fun addLlano(month: Month, wat: Int) {
    histogram.compute(month) { _, v -> (v ?: ZERO) + Triple(0, wat, 0) }
}

private fun addPunta(month: Month, wat: Int) {
    histogram.compute(month) { _, v -> (v ?: ZERO) + Triple(0, 0, wat) }
}

fun printHistogram() {
    val sortedMap = histogram.toSortedMap()
    println(Month.values().joinToString(separator = "\t"))
    sortedMap.forEach { _, (valle, _, _) -> print("%.3f\t".format(valle / 1000.0)) }
    println()
    sortedMap.forEach { _, (_, llano, _) -> print("%.3f\t".format(llano / 1000.0)) }
    println()
    sortedMap.forEach { _, (_, _, punta) -> print("%.3f\t".format(punta / 1000.0)) }
}

operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>): Triple<Int, Int, Int> =
    Triple(
        this.first + other.first,
        this.second + other.second,
        this.third + other.third
    )