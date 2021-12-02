package advent_of_code_2021.day2.part2

import java.io.File

fun main(args: Array<String>) {
    val commands = parseCommands("src/main/kotlin/advent_of_code_2021/day2/input1.txt")
    val initialSubmarine = Submarine(position = 0, depth = 0, aim = 0)
    val finalSubmarine = commands.fold(initialSubmarine, Submarine::perform)
    println(finalSubmarine.position * finalSubmarine.depth)
}

private fun parseCommands(fileName: String): Sequence<Command> {
    return File(fileName).readLines()
        .asSequence()
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
