package day06

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val regex = Regex("\\s+")
        val n = input.size - 1
        val m = input[0].trim().split(regex).size
        val nums = Array(m) { LongArray(n) }

        for (i in 0 until n) {
            input[i].trim().split(regex).forEachIndexed { j, s ->
                nums[j][i] = s.toLong()
            }
        }

        var res = 0L
        input[input.size - 1].trim().split(regex).forEachIndexed { i, s ->
            when (s) {
                "+" -> res += nums[i].fold(0L) { acc, v -> acc + v }
                "*" -> res += nums[i].fold(1L) { acc, v -> acc * v }
            }
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val row = input.size - 1
        val col = input[0].length
        val grid = Array(row) { i ->
            IntArray(col) { j ->
                val c = input[i][j]
                if (c == ' ') -1 else c - '0'
            }
        }

        val regex = Regex("\\s+")
        val ops = ArrayDeque<String>()
        input[input.size - 1].trim().split(regex).forEach { ops.add(it) }

        var i = 0
        var res = 0L
        val temp = mutableListOf<Long>()
        while (i < col) {
            var isGap = true
            var v = 0L
            for (j in 0 until row) {
                if (grid[j][i] != -1) {
                    v = v * 10 + grid[j][i]
                    isGap = false
                }
            }
            if (!isGap) {
                temp.add(v)
            } else {
                when (ops.removeFirst()) {
                    "+" -> res += temp.fold(0L) { acc, v -> acc + v }
                    "*" -> res += temp.fold(1L) { acc, v -> acc * v }
                }
                temp.clear()
            }
            i++
        }

        when (ops.removeFirst()) {
            "+" -> res += temp.fold(0L) { acc, v -> acc + v }
            "*" -> res += temp.fold(1L) { acc, v -> acc * v }
        }
        return res
    }

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("day06/Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}
