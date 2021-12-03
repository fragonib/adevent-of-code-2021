package advent_of_code_2021.day3.part1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExerciseKtTest {

    @Test
    fun moreFrequentChar() {
        val result = moreFrequentChar("0101111010110")
        assertThat(result).isEqualTo('1')
    }

    @Test
    fun calculateGamma() {
        val result = calculateGammaRate(
            listOf(
                "1010101",
                "1010101",
                "1010101",
                "1010101",
                "1010101",
                "1010101",
            ).asSequence()
        )
        assertThat(result).isEqualTo(85)
    }

    @Test
    fun calculateEpsilon() {
        val result = calculateEpsilonRate(
            listOf(
                "1010101",
                "1010101",
                "1010101",
                "1010101",
                "1010101",
                "1010101",
            ).asSequence()
        )
        assertThat(result).isEqualTo(85)
    }

    @Test
    fun resolve() {
        val result = resolve(
            listOf(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010",
            ).asSequence()
        )
        assertThat(result).isEqualTo(198)
    }

}