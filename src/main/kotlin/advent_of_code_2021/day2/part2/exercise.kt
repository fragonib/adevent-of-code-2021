package advent_of_code_2021.day2.part2

import advent_of_code_2021.shared.parseInput

fun main() {
    println(resolve("day2/part2/input.txt"))
}

internal fun resolve(inputSource: String): Int {
    val commands = parseCommands(inputSource)
    val initialSubmarine = Submarine(position = 0, depth = 0, aim = 0)
    val finalSubmarine = commands.fold(initialSubmarine, Submarine::perform)
    return finalSubmarine.position * finalSubmarine.depth
}

private fun parseCommands(fileName: String): Sequence<Command> {
    return parseInput(fileName)
        .map { it.split(' ') }
        .map { (command, quantity) -> Command(Verb.valueOf(command.uppercase()), quantity.toInt()) }
}

enum class Verb { FORWARD, DOWN, UP }
data class Command(
    val verb: Verb,
    val quantity: Int
)

data class Submarine(
    val position: Int,
    val depth: Int,
    val aim: Int
) {
    fun perform(command: Command): Submarine {
        return when (command.verb) {
            Verb.DOWN -> Submarine(
                position = position,
                depth = depth,
                aim = aim + command.quantity
            )
            Verb.UP -> Submarine(
                position = position,
                depth = depth,
                aim = aim - command.quantity
            )
            Verb.FORWARD -> Submarine(
                position = position + command.quantity,
                depth = depth + (aim * command.quantity),
                aim = aim
            )
        }
    }
}
