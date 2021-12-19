package advent_of_code_2021.day10.part1

import advent_of_code_2021.shared.parseInput
import org.assertj.core.api.Assertions.assertThat
import arrow.core.*


fun main() {
    val result = resolve("day10/part1/input.txt")
    println(result)
    assertThat(result).isEqualTo(215229)
}

internal fun resolve(inputSource: String): Int {
    return parseInput(inputSource)
        .map { parseLine(it.toList()) }
        .map { (either, _) -> either.swap().orNull() }
        .filter { it is Error.Corrupted }
        .map { score((it as Error.Corrupted).found) }
        .sum()
}

internal var OPENINGS = arrayOf('(', '[', '{', '<')

internal typealias UnparsedLiteral = List<Char>
internal typealias ParsedResult = Either<Error, Expression>
internal typealias Parsing = Pair<ParsedResult, UnparsedLiteral>

internal fun parseLine(unparsedLiteral: UnparsedLiteral): Parsing {

    if (unparsedLiteral.none())
        return Parsing(Expression.Empty.right(), unparsedLiteral)

    val consumed = unparsedLiteral.first()
    val rest = unparsedLiteral.drop(1)

    return when (consumed) {
        '(' -> lookFor(')', rest, Expression::Parenthesis)
        '[' -> lookFor(']', rest, Expression::SquareBrackets)
        '{' -> lookFor('}', rest, Expression::KeyBrackets)
        '<' -> lookFor('>', rest, Expression::AngleBrackets)
        else -> Parsing(Error.Corrupted(expected = OPENINGS, found = consumed).left(), rest)
    }.let { completedParsing ->
        val unconsumed = completedParsing.second
        val pair = when {
            unconsumed.none() -> completedParsing
            unconsumed.first() in OPENINGS -> completedParsing.mergeWith(
                producer = { parseLine(it) },
                acc = { a, b -> Expression.Sequence(a, b) }
            )
            else -> completedParsing
        }
        pair
    }
}

// LINE := EXPR EXPR*
// EXPR :=
// ( LINE ) |
// < LINE > |
// { LINE } |
// [ LINE ] |
internal fun lookFor(
    closerSymbol: Char,
    unconsumed: UnparsedLiteral,
    wrapper: (Expression) -> Expression,
    previousExpression: Expression = Expression.Empty
): Pair<ParsedResult, UnparsedLiteral> {
    return when {
        unconsumed.none() -> Parsing(Error.Incomplete(missing = closerSymbol).left(), unconsumed)
        unconsumed.first() == closerSymbol -> Parsing(wrapper(previousExpression).right(), unconsumed.drop(1))
        unconsumed.first() in OPENINGS -> parseLine(unconsumed).mergeWith(
            producer = { pendingLiteral -> lookFor(closerSymbol, pendingLiteral, wrapper) },
            acc = { a, b -> wrapper(b) }
        )
        else -> Parsing(
            Error.Corrupted(expected = arrayOf(closerSymbol), found = unconsumed.first()).left(),
            unconsumed
        )
    }
}

internal fun Pair<ParsedResult, UnparsedLiteral>.mergeWith(
    producer: (UnparsedLiteral) -> Pair<ParsedResult, UnparsedLiteral>,
    acc: (Expression, Expression) -> Expression
): Pair<Either<Error, Expression>, UnparsedLiteral> {
    val (leftExpr, unparsedLiteral) = this
    if (leftExpr.isLeft()) return this
    val (rightExpr, unconsumed) = producer(unparsedLiteral)
    val combinedParsing = rightExpr.map { parent -> acc(leftExpr.orNull()!!, parent) }
    return Parsing(combinedParsing, unconsumed)
}

internal sealed class Expression {
    internal class Sequence(vararg val expr: Expression) : Expression() { override fun toString() = expr.joinToString("") }
    internal class Parenthesis(val expr: Expression) : Expression() { override fun toString() = "($expr)" }
    internal class AngleBrackets(val expr: Expression) : Expression() { override fun toString() = "<$expr>" }
    internal class SquareBrackets(val expr: Expression) : Expression() { override fun toString() = "[$expr]" }
    internal class KeyBrackets(val expr: Expression) : Expression() { override fun toString() = "{$expr}" }
    internal object Empty : Expression() { override fun toString() = "" }
}

internal sealed class Error {
    internal class Corrupted(val expected: Array<Char>, val found: Char) : Error()
    internal class Incomplete(val missing: Char) : Error()
}

internal fun score(char: Char): Int {
    val scoreTable = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    return scoreTable[char] ?: throw IllegalArgumentException("Illegal char [$char]")
}