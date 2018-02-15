package com.github.michaelbull.quadtree

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class QuadTreeTest {
    @Test
    fun intersect() {
        val tree = QuadTree(BoundingBox(
            bottomLeft = Point(-1F, -1F),
            topRight = Point(1F, 1F)
        ))

        readDataSet("dataset").forEach(tree::insert)

        val expected = readDataSet("expected")
        val actual = tree.intersect(BoundingBox(
            bottomLeft = Point(-0.1F, -0.1F),
            topRight = Point(0.1F, 0.1F)
        )).sortedWith(comparator)

        assertThat(actual, equalTo(expected))
    }

    private fun readDataSet(name: String): List<Point> {
        return javaClass.classLoader
            .getResourceAsStream("$name.txt")
            .bufferedReader()
            .useLines { it.map(::lineToPoint).toList() }
    }

    private fun lineToPoint(line: String): Point {
        val split = line.split(",")
        val x = split[0].toFloat()
        val y = split[1].toFloat()
        return Point(x, y)
    }

    private val comparator = Comparator<Point> { (x1, y1), (x2, y2) ->
        val v1 = Math.sqrt(((x1 * x1) + (y1 * y1)).toDouble()).toFloat()
        val v2 = Math.sqrt(((x2 * x2) + (y2 * y2)).toDouble()).toFloat()
        v1.compareTo(v2)
    }
}
