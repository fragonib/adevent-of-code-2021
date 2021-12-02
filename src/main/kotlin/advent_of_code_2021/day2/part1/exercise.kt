package advent_of_code_2021.day2.part1

import org.assertj.core.api.Assertions
import java.io.File

fun main(args: Array<String>) {
    val commands = parseCommands("src/main/kotlin/advent_of_code_2021/day2/part1/input.txt")
    val initial = Submarine(0, 0)
    val finalSubmarine = commands.fold(initial) { sub, command -> sub.perform(command) }
    val result = finalSubmarine.position * finalSubmarine.depth
    println(result)
    Assertions.assertThat(result).isEqualTo(1427868)
}

private fun parseCommands(fileName: String): Sequence<Command> {
    return File(fileName).readLines()
        .asSequence()
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
