package day07

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val row = input.size
        val col = input[0].length
        val grid = Array(row)  { i -> CharArray(col) { j -> input[i][j] } }

        val queue = ArrayDeque<Pair<Int, Int>>()
        val s = input[0].indexOf('S')
        queue.add(0 to s)

        var res = 0L
        while (queue.isNotEmpty()) {
            val size = queue.size

            val candidates = mutableSetOf<Pair<Int, Int>>()
            repeat(size) {
                val (i, j) = queue.removeFirst()

                if (i < row - 1 && grid[i + 1][j] == '.') {
                    candidates.add(i + 1 to j)
                } else if (i < row - 1 && grid[i + 1][j] == '^') {
                    if (j > 0) candidates.add(i + 1 to j - 1)
                    if (j < col - 1) candidates.add(i + 1 to j + 1)
                    res++
                }
            }
            candidates.forEach { queue.add(it) }
        }

        return res
    }

    fun dfs(grid: Array<CharArray>, i: Int, j: Int, memo: MutableMap<Pair<Int, Int>, Long>): Long {
        val row = grid.size
        val col = grid[0].size
        if (j < 0 || j >= col) {
            return 0L
        }
        if (i == row - 1) {
            return 1L
        }

        if (memo.containsKey(i to j)) {
            return memo.getValue(i to j)
        }

        val res = if (grid[i][j] == '^') {
            dfs(grid, i + 1, j - 1, memo) + dfs(grid, i + 1, j + 1, memo)
        } else {
            dfs(grid, i + 1, j, memo)
        }

        memo[i to j] = res
        return res
    }

    fun part2(input: List<String>): Long {
        val row = input.size
        val col = input[0].length
        val grid = Array(row)  { i -> CharArray(col) { j -> input[i][j] } }

        val s = input[0].indexOf('S')
        val res = dfs(grid, 0, s, mutableMapOf())
        return res
    }

    // Or read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("day07/Day07_test")
    check(part1(testInput) == 21L)
    check(part2(testInput) == 40L)

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("day07/Day07")
    part1(input).println()
    part2(input).println()
}
