package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

abstract class Node(
    val capacity: Int,
    val levels: Int,
    val boundingBox: BoundingBox
) {
    abstract fun insert(point: Point): Node
    abstract fun intersect(other: BoundingBox): List<Point>

    operator fun contains(point: Point): Boolean = boundingBox.contains(point)
}
