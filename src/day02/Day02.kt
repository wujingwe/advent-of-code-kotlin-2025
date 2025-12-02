package day02

import println
import readInput

fun main() {
    val cache = mutableMapOf<Long, Boolean>()
    fun isInvalid(n: Long): Boolean {
        if (cache.containsKey(n)) {
            return cache.getValue(n)
        }
        val s = n.toString()
        val len = s.length
        if (len % 2 == 1) {
            cache[n] = false
            return false
        }

        for (i in 0 until len / 2) {
            if (s[i] != s[len / 2 + i]) {
                cache[n] = false
                return false
            }
        }
        cache[n] = true
        return true
    }

    fun part1(input: List<String>): Long {
        val tokens = input[0].split(",")

        var sum = 0L
        tokens.forEach { token ->
            val (start, end) = token.split("-")
            val s = start.toLong()
            val e = end.toLong()

            for (n in s..e) {
                if (isInvalid(n)) sum += n
            }
        }
        return sum
    }

    fun isInvalid2(n: Long): Boolean {
        val s = n.toString()
        val len = s.length
        val half = len / 2
        for (i in 1..half) {
            if (len % i != 0) continue

            val count = len / i
            val sb = StringBuilder()
            repeat(count) {
                sb.append(s.take(i))
            }
            if (sb.toString() == s) {
                cache[n] = true
                return true
            }
        }

        cache[n] = false
        return false
    }

    fun part2(input: List<String>): Long {
        val tokens = input[0].split(",")

        var sum = 0L
        tokens.forEach { token ->
            val (start, end) = token.split("-")
            val s = start.toLong()
            val e = end.toLong()

            for (n in s..e) {
                if (isInvalid(n) || isInvalid2(n)) sum += n
            }
        }
        return sum
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
