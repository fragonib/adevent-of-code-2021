package advent_of_code_2021.day18.part1

import advent_of_code_2021.day18.part1.SnailFishItem.Regular
import advent_of_code_2021.day18.part1.SnailFishItem.SnailFishNumber
import advent_of_code_2021.shared.parseInput
import java.lang.Character.isDigit


fun main() {
    val result = resolve("day18/part1/input.txt")
    println(result)
}


internal fun resolve(inputSource: String): Int {
    val ZERO = SnailFishNumber(Regular(0), Regular(0))
    return parseInput(inputSource)
        .map (::parseLine)
        .fold (ZERO, ::snailFishSum)
        .let { magnitude(it) }
}

sealed class SnailFishItem {
    data class Regular(val num: Int) : SnailFishItem()
    data class SnailFishNumber(val left: SnailFishItem, val right: SnailFishItem) : SnailFishItem()
}

fun snailFishSum(first: SnailFishNumber, second: SnailFishNumber): SnailFishNumber {
    TODO("Not yet implemented")
}

fun magnitude(sf: SnailFishNumber): Int {
    return 3 * magnitude(sf.left) + 2 * magnitude(sf.right)
}

fun magnitude(sf: SnailFishItem): Int {
    return when (sf) {
        is Regular -> sf.num
        is SnailFishNumber -> magnitude(sf)
    }
}


// -- Parsing

typealias Unparsed = String

fun parseLine(literal: Unparsed): SnailFishNumber {
    return parseSnailFishPair(literal).first
}

fun parseSnailFishPair(literal: Unparsed): Pair<SnailFishNumber, Unparsed> {
    val leftLiteral = literal.removePrefix(prefix = "[")
    val (left, unconsumedAfterLeft) = parseSnailFishItem(leftLiteral)
    val rightLiteral = unconsumedAfterLeft.removePrefix(",")
    val (right, unconsumedAfterRight) = parseSnailFishItem(rightLiteral)
    return Pair(SnailFishNumber(left, right), unconsumedAfterRight.removePrefix("]"))
}

fun parseSnailFishItem(literal: Unparsed): Pair<SnailFishItem, Unparsed> = when {
    literal.startsWith(prefix = "[") -> parseSnailFishPair(literal)
    literal.first().isDigit() -> {
        val takeWhile = literal.takeWhile(::isDigit)
        Pair(Regular(takeWhile.toInt()), literal.removePrefix(takeWhile))
    }
    else -> throw IllegalArgumentException(literal)
}
