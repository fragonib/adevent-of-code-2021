package advent_of_code_2021.day6.part2

import org.assertj.core.api.Assertions.assertThat


fun main() {
    val result = resolve("day6/part2/test_input.txt")
    println(result)
    assertThat(result).isEqualTo(26984457539)
}