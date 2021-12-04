package advent_of_code_2021.day2.part1

import advent_of_code_2021.shared.parseInput


fun main() {
    println(resolve("day2/part1/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val commands = parseCommands(inputSource)
    val initial = Submarine(0, 0)
    val finalSubmarine = commands.fold(initial) { sub, command -> sub.perform(command) }
    return finalSubmarine.position * finalSubmarine.depth
}

internal fun parseCommands(fileName: String): Sequence<Command> {
    return parseInput(fileName)
        .map { it.split(' ') }
        .map { (command, quantity) -> Command(Verb.valueOf(command.uppercase()), quantity.toInt()) }
}

data class Command(
    val verb: Verb,
    val quantity: Int
)

enum class Verb { FORWARD, DOWN, UP }

data class Submarine(
    val position: Int,
    val depth: Int
) {
    fun perform(command: Command): Submarine {
        return when (command.verb) {
            Verb.FORWARD -> Submarine(position + command.quantity, depth)
            Verb.UP -> Submarine(position, depth - command.quantity)
            Verb.DOWN -> Submarine(position, depth + command.quantity)
        }
    }
}
