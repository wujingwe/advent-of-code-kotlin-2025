package day10

import println
import readInput
import java.util.PriorityQueue

fun main() {

    fun bfs(target: String, buttons: List<List<Int>>): Int {
        val queue = ArrayDeque<String>()

        val sb = StringBuilder()
        repeat(target.length) { sb.append('.') }
        queue.addLast(sb.toString())

        var count = 0
        val visited = mutableSetOf<String>()
        while (queue.isNotEmpty()) {
            val size = queue.size
            repeat(size) {
                val curr = queue.removeFirst()
                if (curr == target) {
                    return count
                }

                visited.add(curr)

                for (button in buttons) {
                    val sb = StringBuilder(curr)
                    for (index in button) {
                        sb[index] = when (sb[index]) {
                            '#' -> '.'
                            else -> '#'
                        }
                    }
                    val s = sb.toString()
                    if (!visited.contains(s)) {
                        queue.addLast(s)
                    }
                }
            }
            count++
        }
        return count
    }

    fun part1(input: List<String>): Long {
        val res = input.sumOf {
            val regex = Regex("""^\[([.#]+)\]\s+(.*?)\s*\{([\d,]+)\}$""")
            val match = regex.matchEntire(it)!!
            val diagram = match.groupValues[1]
            val buttonString = match.groupValues[2]

            val buttonRegex = Regex("""\((\d+(?:,\d+)*)\)""")
            val buttons = buttonRegex.findAll(buttonString)
                .map { match ->
                    match.groupValues[1] // Get the content inside the parentheses (e.g., "1,3")
                        .split(",")
                        .map { it.toInt() }
                }
                .toList()

            val queue = ArrayDeque<String>()
            queue.add(CharArray(diagram.length) { '.' }.joinToString(""))

            val count = bfs(diagram, buttons)
            count.toLong()
        }
        return res
    }

    class State(val joltage: IntArray, val count: Int)

    fun bfs2(target: IntArray, buttons: List<List<Int>>): Int {
        val queue = ArrayDeque<State>()
        queue.addLast(State(IntArray(target.size), 0))

        var res = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            if (curr.joltage.contentEquals(target)) {
                res = minOf(res, curr.count)
                continue
            }

            for (button in buttons) {
                var minDiff = Int.MAX_VALUE
                var valid = true
                for (index in button) {
                    if (curr.joltage[index] >= target[index]) {
                        valid = false
                        break
                    }
                    minDiff = minOf(minDiff, target[index] - curr.joltage[index])
                }

                if (valid) {
                    val copy = curr.joltage.copyOf()
                    for (index in button) {
                        copy[index] += minDiff
                    }
                    queue.addLast(State(copy, curr.count + minDiff))
                }
            }
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val res = input.sumOf {
            val regex = Regex("""^\[([.#]+)\]\s+(.*?)\s*\{([\d,]+)\}$""")
            val match = regex.matchEntire(it)!!
            val buttonString = match.groupValues[2]
            val joltage = match.groupValues[3].split(",").map { token -> token.toInt() }.toIntArray()

            val buttonRegex = Regex("""\((\d+(?:,\d+)*)\)""")
            val buttons = buttonRegex.findAll(buttonString)
                .map { match ->
                    match.groupValues[1] // Get the content inside the parentheses (e.g., "1,3")
                        .split(",")
                        .map { it.toInt() }
                }
                .toList()

            val count = bfs2(joltage, buttons)
            println("count=$count")
            count
        }
        println("res=$res")
        return res.toLong()
    }

    // Or read a large test input from the `src/Day10_test.txt` file:
    val testInput = readInput("day10/Day10_test")
//    check(part1(testInput) == 7L)
    check(part2(testInput) == 33L)

    // Read the input from the `src/Day10.txt` file.
    val input = readInput("day10/Day10")
//    part1(input).println()
    part2(input).println()
}
