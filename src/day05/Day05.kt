package day05

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val empty = input.indexOfFirst { it.isEmpty() }
        val freshRange = mutableListOf<LongRange>()
        for (i in 0 until empty) {
            val (start, end) = input[i].split("-")
            freshRange.add(start.toLong()..end.toLong())
        }
        var res = 0L
        for (i in empty + 1 until input.size) {
            val v = input[i].toLong()
            for (r in freshRange) {
                if (v in r) {
                    res++
                    break
                }
            }
        }
        return res
    }

    fun List<LongRange>.merge(new: LongRange): MutableList<LongRange> {
        val res = mutableListOf<LongRange>()
        var solved = false
        for (r in this) {
            if (solved) {
                res.add(r)
            } else if (r == new) {
                res.add(r)
                solved = true
            } else if ((new.first >= r.first && new.first <= r.last) || (new.last >= r.first && new.last <= r.last)) {
                res.add(minOf(new.first, r.first).. maxOf(new.last, r.last))
                solved = true
            } else {
                res.add(r)
            }
        }

        if (!solved) {
            res.add(new)
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val empty = input.indexOfFirst { it.isEmpty() }
        val sorted = input.take(empty).map {
            val (start, end) = it.split("-")
            start.toLong()..end.toLong()
        }.sortedWith(compareBy<LongRange> { it.first }.thenBy { it.last })

        var list = mutableListOf<LongRange>()
        for (r in sorted) {
            list = list.merge(r)
        }

        var res = 0L
        for (r in list) {
            res += (r.last - r.first + 1)
        }
        return res
    }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 3L)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}
