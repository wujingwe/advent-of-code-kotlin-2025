package day09

import println
import readInput
import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Long {
        val points = input.map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }

        var res = Long.MIN_VALUE
        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                val width = abs(points[i].x - points[j].x) + 1
                val height = abs(points[i].y - points[j].y) + 1

                val area = width.toLong() * height
                res = maxOf(res, area)
            }
        }

        return res
    }

    data class VEdge(val x: Int, val y1: Int, val y2: Int)

    fun part2(input: List<String>): Long {
        val points = input.map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }

        val ys = points.map { it.y }.distinct().sorted()

        val vEdges = mutableListOf<VEdge>()
        for (i in points.indices) {
            val a = points[i]
            val b = points[(i + 1) % points.size]
            if (a.x == b.x) {
                val e = VEdge(a.x, minOf(a.y, b.y), maxOf(a.y, b.y))
                vEdges.add(e)
            }
        }

        val spans = mutableMapOf<Int, List<Pair<Int, Int>>>()
        for (y in ys) {
            val xs = vEdges
                .filter { it.y1 <= y && it.y2 > y }
                .map { it.x }
                .sorted()

            val list = ArrayList<Pair<Int, Int>>()
            var i = 0
            while (i + 1 < xs.size) {
                list += xs[i] to xs[i + 1]
                i += 2
            }
            spans[y] = list
        }

        fun inside(y: Int, x1: Int, x2: Int): Boolean {
            val s = spans[y] ?: return false
            var lo = 0
            var hi = s.size - 1
            while (lo <= hi) {
                val mid = (lo + hi) ushr 1
                val (l, r) = s[mid]
                if (x1 < l) hi = mid - 1
                else if (x1 > r) lo = mid + 1
                else return x2 <= r
            }
            return false
        }

        var best = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val a = points[i]
                val b = points[j]
                if (a.x == b.x || a.y == b.y) continue
                val x1 = minOf(a.x, b.x)
                val x2 = maxOf(a.x, b.x)
                val y1 = minOf(a.y, b.y)
                val y2 = maxOf(a.y, b.y)

                var ok = true
                for (y in ys) {
                    if (y < y1 || y >= y2) continue
                    if (!inside(y, x1, x2)) {
                        ok = false
                        break
                    }
                }
                if (!ok) continue

                val area = (x2 - x1 + 1L) * (y2 - y1 + 1L)
                best = maxOf(best, area)
            }
        }
        return best
    }

    // Or read a large test input from the `src/Day09_test.txt` file:
    val testInput = readInput("day09/Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    // Read the input from the `src/Day09.txt` file.
    val input = readInput("day09/Day09")
    part1(input).println()
    part2(input).println()
}
