package day04

import println
import readInput

fun main() {
    val dirs = arrayOf(-1 to -1, -1 to 0, -1 to 1, 0 to 1, 1 to 1, 1 to 0, 1 to -1, 0 to -1)

    fun remove(grid: Array<CharArray>): Int {
        val n = grid.size
        val m = grid[0].size

        var res = 0
        for (i in 0 until n) {
            for (j in 0 until m) {
                if (grid[i][j] != '@') continue

                var count = 0
                for ((dx, dy) in dirs) {
                    val nx = i + dx
                    val ny = j + dy
                    if (nx !in 0 until n || ny !in 0 until m) continue

                    if (grid[nx][ny] == '@' || grid[nx][ny] == 'x') count++
                    if (count >= 4) break
                }

                if (count < 4) {
                    grid[i][j] = 'x'
                    res++
                }
            }
        }

        for (i in 0 until n) {
            for (j in 0 until m) {
                if (grid[i][j] == 'x') grid[i][j] = '.'
            }
        }
        return res
    }

    fun part1(input: List<String>): Long {
        val n = input.size
        val m = input[0].length
        val grid = Array(n) { i -> CharArray(m) { j -> input[i][j] } }

        return remove(grid).toLong()
    }

    fun part2(input: List<String>): Long {
        val n = input.size
        val m = input[0].length
        val grid = Array(n) { i -> CharArray(m) { j -> input[i][j] } }

        var res = 0L
        while (true) {
            val count = remove(grid)
            res += count

            if (count == 0) break
        }
        return res
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 13L)
    check(part2(testInput) == 43L)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}
