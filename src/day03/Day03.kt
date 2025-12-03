package day03

import println
import readInput
import java.util.Stack

fun main() {
    fun part1(input: List<String>): Long {
        var res = 0L
        input.forEach {
            val array = it.toCharArray()
            val first = array.max()
            val index = array.indexOfFirst { c -> c == first }
            res += if (index == array.size - 1) {
                val c1 = array.dropLast(1).max()
                (c1 - '0') * 10 + (first - '0')
            } else {
                val c2 = array.drop(index + 1).max()
                (first - '0') * 10 + (c2 - '0')
            }
        }
        return res
    }

    fun part2(input: List<String>): Long {
        var res = 0L
        input.forEach {
            val array = it.toCharArray()
            val stack = Stack<Int>()
            var remain = array.size - 12 // Can only drop remain items
            for (c in array) {
                val curr = c - '0'
                if (stack.isEmpty()) {
                    stack.push(curr)
                } else {
                    while (stack.isNotEmpty() && remain > 0 && curr > stack.peek()) {
                        stack.pop()
                        remain--
                    }
                    stack.push(curr)
                }
            }

            var sum = 0L
            for (v in stack.take(12)) {
                sum = sum * 10 + v
            }
            res += sum
        }
        return res
    }

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
