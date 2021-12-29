package advent_of_code_2021.solar.aggregate

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Month.*
import java.time.format.DateTimeFormatter

typealias Wats = Int
typealias WatsOnEachInterval = Triple<Wats, Wats, Wats>

val monthlyConsumption = mutableMapOf<Month, WatsOnEachInterval>()

fun main() {

    csvReader { delimiter = ';' }
        .open("src/main/kotlin/advent_of_code_2021/solar/aggregate/input.csv") {
            readAllAsSequence().drop(1).forEach { row ->
                val (dateLiteral, hourLiteral) = row[1].split(" ")
                val date = LocalDate.parse(dateLiteral, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                val hour = hourLiteral.split(":", limit = 2)[0].toInt()
                val wats = row[3].toInt()
                addConsumption(date, hour, wats)
            }
        }

    printHistogram()
}

private fun addConsumption(date: LocalDate, hour: Wats, wats: Wats) {
    val month = date.month
    when {
        isHoliday(date) -> addLlano(month, wats)
        hour in 1..8 -> addValle(month, wats)
        hour in 11..14 || hour in 17..22 -> addPunta(month, wats)
        else -> addLlano(month, wats)
    }
}

val ZERO_WATS: Triple<Wats, Wats, Wats> = WatsOnEachInterval(0, 0, 0)

private fun addValle(month: Month, wats: Wats) {
    monthlyConsumption.compute(month) { _, v -> (v ?: ZERO_WATS) + WatsOnEachInterval(wats, 0, 0) }
}

private fun addLlano(month: Month, wats: Wats) {
    monthlyConsumption.compute(month) { _, v -> (v ?: ZERO_WATS) + WatsOnEachInterval(0, wats, 0) }
}

private fun addPunta(month: Month, wats: Wats) {
    monthlyConsumption.compute(month) { _, v -> (v ?: ZERO_WATS) + WatsOnEachInterval(0, 0, wats) }
}

fun isHoliday(date: LocalDate): Boolean {
    if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY)
        return true
    val countryHolidaysOn2021 = listOf(
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
    return Pair(date.dayOfMonth, date.month) in countryHolidaysOn2021
}

fun printHistogram() {
    val sortedMap = monthlyConsumption.toSortedMap()
    val kwFormat = DecimalFormat("#,###.###")
    println(Month.values().joinToString(separator = "\t"))
    println(sortedMap.values.joinToString(separator = "\t") { kwFormat.format(it.first / 1000.0) })
    println(sortedMap.values.joinToString(separator = "\t") { kwFormat.format(it.second / 1000.0) })
    println(sortedMap.values.joinToString(separator = "\t") { kwFormat.format(it.third / 1000.0) })
}

operator fun Triple<Wats, Wats, Wats>.plus(other: Triple<Wats, Wats, Wats>): Triple<Wats, Wats, Wats> =
    WatsOnEachInterval(
        this.first + other.first,
        this.second + other.second,
        this.third + other.third
    )