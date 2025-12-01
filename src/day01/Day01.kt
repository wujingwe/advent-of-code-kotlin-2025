package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var pos = 50
        var count = 0
        input.forEach {
            val direction = it[0]
            val distance = it.substring(1).toInt()

            if (direction == 'L') {
                pos = (pos - distance % 100 + 100) % 100
            } else {
                pos = (pos + distance % 100) % 100
            }
            if (pos == 0) count++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var pos = 50
        var count = 0
        input.forEach {
            val direction = it[0]
            var distance = it.substring(1).toInt()

            count += distance / 100
            distance %= 100

            if (direction == 'L') {
                if (pos == 0) pos = 100

                pos -= distance
                if (pos <= 0) {
                    count++
                    pos += 100
                }
            } else {
                if (pos == 100) pos = 0

                pos += distance
                if (pos >= 100) {
                    count++
                    pos -= 100
                }
            }
        }
        return count
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
