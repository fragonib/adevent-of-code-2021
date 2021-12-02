package advent_of_code_2021.day2

import java.io.File

fun main(args: Array<String>) {

    val fileName = "src/main/kotlin/advent_of_code_2021/day2/input1.txt"
    val commands = File(fileName).readLines()
        .asSequence()
        .map { it.split(' ') }
        .map { (command, quantity) -> Command(Verb.valueOf(command.uppercase()), quantity.toInt()) }

    val initial = Submarine(0, 0)

    val finalSubmarine = commands.fold(initial) { sub, command -> sub.perform(command) }
    println(finalSubmarine.position * finalSubmarine.depth)

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
