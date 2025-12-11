package day11

import println
import readInput

fun main() {

    data class Node(val name: String) {
        val children = mutableListOf<Node>()

        fun add(node: Node) {
            children.add(node)
        }
    }

    fun dfs(node: Node): Long {
        if (node.name == "out") {
            return 1
        }
        var res = 0L
        for (child in node.children) {
            res += dfs(child)
        }
        return res
    }

    fun part1(input: List<String>): Long {
        val map = mutableMapOf<String, Node>()
        val regex = Regex("""^(.+):\s+(.+)$""")
        input.forEach {
            val match = regex.matchEntire(it)!!
            val name = match.groupValues[1]
            val children = match.groupValues[2].split(" ")

            map.computeIfAbsent(name) { key -> Node(key) }.apply {
                children.forEach { child ->
                    add(map.computeIfAbsent(child) { key -> Node(key) })
                }
            }
        }

        return dfs(map.getValue("you"))
    }

    fun countPaths(from: Node, to: Node, memo: MutableMap<Pair<String, String>, Long>): Long {
        if (from.name == to.name) {
            return 1L
        }
        val key = from.name to to.name
        memo[key]?.let { return it }

        var total = 0L
        for (child in from.children) {
            total += countPaths(child, to, memo)
        }

        memo[key] = total
        return total
    }

    fun part2(input: List<String>): Long {
        val map = mutableMapOf<String, Node>()
        val regex = Regex("""^(.+):\s+(.+)$""")
        input.forEach {
            val match = regex.matchEntire(it)!!
            val name = match.groupValues[1]
            val children = match.groupValues[2].split(" ")

            map.computeIfAbsent(name) { key -> Node(key) }.apply {
                children.forEach { child ->
                    add(map.computeIfAbsent(child) { key -> Node(key) })
                }
            }
        }

        val svr = map.getValue("svr")
        val dac = map.getValue("dac")
        val fft = map.getValue("fft")
        val out = map.getValue("out")
        val memo = mutableMapOf<Pair<String, String>, Long>()
        val r1 = countPaths(svr, dac, memo) * countPaths(dac, fft, memo) * countPaths(fft, out, memo)
        val r2 = countPaths(svr, fft, memo) * countPaths(fft, dac, memo) * countPaths(dac, out, memo)
        return r1 + r2
    }

    // Or read a large test input from the `src/Day11_test.txt` file:
    val testInput = readInput("day11/Day11_test")
    check(part1(testInput) == 5L)
    val testInput2 = readInput("day11/Day11_test2")
    check(part2(testInput2) == 2L)

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("day11/Day11")
    part1(input).println()
    part2(input).println()
}
