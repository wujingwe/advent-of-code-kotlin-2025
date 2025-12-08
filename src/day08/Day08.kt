package day08

import println
import readInput
import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    data class Point(val x: Int, val y: Int, val z: Int)
    data class Distance(val d: Long, val i: Int, val j: Int)
    class UnionFind(n: Int) {
        val parents = IntArray(n) { it }

        fun union(a: Int, b: Int) {
            val pa = find(a)
            val pb = find(b)
            if (pa == pb) return

            if (pa < pb) {
                parents[pb] = pa
            } else {
                parents[pa] = pb
            }
        }

        fun find(x: Int): Int {
            if (parents[x] != x) {
                parents[x] = find(parents[x])
            }
            return parents[x]
        }

        fun pCount(): Int {
            val set = mutableSetOf<Int>()
            for (i in parents.indices) {
                set.add(find(i))
            }
            return set.size
        }
    }

    fun distance(p1: Point, p2: Point): Long {
        val x2 = (p1.x - p2.x).toLong()
        val y2 = (p1.y - p2.y).toLong()
        val z2 = (p1.z - p2.z).toLong()
        return x2 * x2 + y2 * y2 + z2 * z2
    }

    fun part1(input: List<String>, count: Int): Long {
        val points = input.map {
            val (x, y, z) = it.split(",")
            Point(x.toInt(), y.toInt(), z.toInt())
        }

        val pq = PriorityQueue<Distance> { a, b -> a.d.compareTo(b.d) }
        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                if (i == j) continue

                val d = distance(points[i], points[j])
                pq.offer(Distance(d, i, j))
            }
        }

        val uf = UnionFind(points.size)
        var count = count
        while (pq.isNotEmpty()) {
            val (_, i, j) = pq.poll()
            uf.union(i, j)

            if (--count == 0) break
        }


        val map = mutableMapOf<Int, Int>()
        for (i in 0 until points.size) {
            val parent = uf.find(i)
            val c = map.getOrDefault(parent, 0) + 1
            map[parent] = c
        }
        val largest = PriorityQueue<Int> { a, b -> a - b }
        for (v in map.values) {
            largest.offer(v)
            if (largest.size > 3) largest.poll()
        }

        val l1 = largest.poll()
        val l2 = largest.poll()
        val l3 = largest.poll()
        return 1L * l1 * l2 * l3
    }

    fun part2(input: List<String>): Long {
        val points = input.map {
            val (x, y, z) = it.split(",")
            Point(x.toInt(), y.toInt(), z.toInt())
        }

        val pq = PriorityQueue<Distance> { a, b -> a.d.compareTo(b.d) }
        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                if (i == j) continue

                val d = distance(points[i], points[j])
                pq.offer(Distance(d, i, j))
            }
        }

        val uf = UnionFind(points.size)
        var res = -1L
        while (pq.isNotEmpty()) {
            val (_, i, j) = pq.poll()
            uf.union(i, j)

            if (uf.pCount() == 1) {
                res = points[i].x.toLong() * points[j].x
                break
            }
        }

        return res
    }

    // Or read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("day08/Day08_test")
    check(part1(testInput, 10) == 40L)
    check(part2(testInput) == 25272L)

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("day08/Day08")
    part1(input, 1000).println()
    part2(input).println()
}
