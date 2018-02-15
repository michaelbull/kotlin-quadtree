package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

class LeafNode(boundingBox: BoundingBox) : Node(boundingBox) {
    private val points = mutableListOf<Point>()

    override fun insert(point: Point): Node {
        return when {
            point !in this -> this
            points.size >= CAPACITY -> split().insert(point)
            else -> {
                points += point
                this
            }
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

        return BranchNode(
            boundingBox,
            points,
            northEast = LeafNode(BoundingBox(
                bottomLeft = Point(x0 + centreX, y0 + centreY),
                topRight = Point(x1, y1)
            )),
            northWest = LeafNode(BoundingBox(
                bottomLeft = Point(x0, y0 + centreY),
                topRight = Point(x0 + centreX, y1)
            )),
            southEast = LeafNode(BoundingBox(
                bottomLeft = Point(x0 + centreX, y0),
                topRight = Point(x1, y0 + centreY)
            )),
            southWest = LeafNode(BoundingBox(
                bottomLeft = Point(x0, y0),
                topRight = Point(x0 + centreX, y0 + centreY)
            ))
        )
    }

    private companion object {
        private const val CAPACITY = 10
    }
}
