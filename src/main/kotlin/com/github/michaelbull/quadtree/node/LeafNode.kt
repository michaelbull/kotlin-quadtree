package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

class LeafNode(
    capacity: Int,
    depth: Int,
    boundingBox: BoundingBox
) : Node(capacity, depth, boundingBox) {

    private val points = mutableListOf<Point>()

    override fun insert(point: Point): Node {
        return when {
            point !in this -> this
            points.size < capacity -> {
                points += point
                this
            }
            levels >= 0 -> split().insert(point)
            else -> this
        }
    }

    override fun intersect(other: BoundingBox): List<Point> {
        return if (boundingBox.intersects(other)) {
            points.filter { it in other }
        } else {
            emptyList()
        }
    }

    private fun split(): BranchNode {
        val (bottomLeft, topRight) = boundingBox
        val (x0, y0) = bottomLeft
        val (x1, y1) = topRight

        val centreX = (x1 - x0) / 2
        val centreY = (y1 - y0) / 2

        val newDepth = levels - 1

        val branch = BranchNode(capacity, levels, boundingBox,
            northEast = LeafNode(capacity, newDepth, BoundingBox(
                bottomLeft = Point(x0 + centreX, y0 + centreY),
                topRight = Point(x1, y1)
            )),
            northWest = LeafNode(capacity, newDepth, BoundingBox(
                bottomLeft = Point(x0, y0 + centreY),
                topRight = Point(x0 + centreX, y1)
            )),
            southEast = LeafNode(capacity, newDepth, BoundingBox(
                bottomLeft = Point(x0 + centreX, y0),
                topRight = Point(x1, y0 + centreY)
            )),
            southWest = LeafNode(capacity, newDepth, BoundingBox(
                bottomLeft = Point(x0, y0),
                topRight = Point(x0 + centreX, y0 + centreY)
            ))
        )

        points.forEach { branch.insert(it) }

        return branch
    }
}
