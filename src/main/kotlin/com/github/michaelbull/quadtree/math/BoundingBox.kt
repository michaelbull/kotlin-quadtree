package com.github.michaelbull.quadtree.math

data class BoundingBox(
    val bottomLeft: Point,
    val topRight: Point
) {
    init {
        require(bottomLeft.x < topRight.x)
        require(bottomLeft.y < topRight.y)
    }

    operator fun contains(point: Point): Boolean {
        return point.x >= bottomLeft.x
            && point.x <= topRight.x
            && point.y >= bottomLeft.y
            && point.y <= topRight.y
    }

    fun intersects(other: BoundingBox): Boolean {
        return other.bottomLeft.x < topRight.x
            && other.topRight.x > bottomLeft.x
            && other.bottomLeft.y < topRight.y
            && other.topRight.y > bottomLeft.y
    }
}
