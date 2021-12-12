package advent_of_code_2021.day10.part1

import advent_of_code_2021.shared.parseInput
import arrow.core.Either
import arrow.core.left
import arrow.core.right


fun main() {
    val result = resolve("day10/part1/input.txt")
    println(result)
}

internal fun resolve(inputSource: String): Int {
    return parseInput(inputSource)
        .map { parse(it.toList()) }
        .map { either -> either.swap().orNull() }
        .filter { it is Error.Corrupted }
        .map { score((it as Error.Corrupted).found) }
        .sum()
}

fun parse(line: List<Char>): Either<Error, Expression> {
    return exprParsing(line).first
}

val EMPTY = listOf<Char>()

fun exprParsing(line: List<Char>): Pair<Either<Error, Expression>, List<Char>> {

    if (line.none()) return Pair(Expression.Empty.right(), EMPTY)

    val head = line.first()
    val rest = line.drop(1)

    return when (head) {
        '(' -> lookFor(')', rest)
        '[' -> lookFor(']', rest)
        '{' -> lookFor('}', rest)
        '<' -> lookFor('>', rest)
        else -> Pair(Expression.Empty.right(), line)
    }
}

private fun lookFor(looking: Char, rest: List<Char>): Pair<Either<Error, Expression>, List<Char>> {
    val (parsedExpr: Either<Error, Expression>, unparsed: List<Char>) = exprParsing(rest)
    if (parsedExpr.isLeft()) return Pair(parsedExpr, EMPTY)

    val innerExpr: Expression = parsedExpr.orNull()!!
    val either: Either<Error, Expression> = when {
        unparsed.none() -> Error.Incomplete(missing = looking).left()
        unparsed.first() == looking -> Expression.Parenthesis(innerExpr).right()
        else -> Error.Corrupted(expected = looking, found = unparsed.first()).left()
    }

    return Pair(either, unparsed.drop(1))
}

sealed class Expression {
    class Parenthesis(val expr: Expression) : Expression() { override fun toString() = "($expr)" }
    class AngleBrackets(val expr: Expression) : Expression() { override fun toString() = "<$expr>" }
    class SquareBrackets(val expr: Expression) : Expression() { override fun toString() = "[$expr]" }
    class KeyBrackets(val expr: Expression) : Expression() { override fun toString() = "{$expr}" }
    object Empty : Expression() { override fun toString() = "" }
}

sealed class Error {
    class Corrupted(val expected: Char, val found: Char) : Error()
    class Incomplete(val missing: Char) : Error()
}

fun score(char: Char): Int {
    val scoreTable = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    return scoreTable[char] ?: throw IllegalArgumentException("Illegal char [$char]")
}