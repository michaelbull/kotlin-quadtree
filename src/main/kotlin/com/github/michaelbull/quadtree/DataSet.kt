package com.github.michaelbull.quadtree

import com.github.michaelbull.quadtree.math.Point

object DataSet {
    fun read(name: String): List<Point> {
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
}
