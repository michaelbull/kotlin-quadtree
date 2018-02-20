package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

class LeafNode(
    capacity: Int,
    levels: Int,
    boundingBox: BoundingBox
) : Node(capacity, levels, boundingBox) {

    private val points = mutableListOf<Point>()

    override fun insert(point: Point): Node {
        require(point in boundingBox) { "$point is outside of $this" }

        return when {
            points.size < capacity -> {
                points += point
                this
            }
            levels >= 0 -> split().insert(point)
            else -> error { "$this is at capacity and has no remaining levels to split into" }
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

        val nextLevel = levels - 1

        val branch = BranchNode(capacity, levels, boundingBox,
            quadrant1 = LeafNode(capacity, nextLevel, BoundingBox(
                bottomLeft = Point(x0 + centreX, y0 + centreY),
                topRight = Point(x1, y1)
            )),
            quadrant2 = LeafNode(capacity, nextLevel, BoundingBox(
                bottomLeft = Point(x0, y0 + centreY),
                topRight = Point(x0 + centreX, y1)
            )),
            quadrant3 = LeafNode(capacity, nextLevel, BoundingBox(
                bottomLeft = Point(x0, y0),
                topRight = Point(x0 + centreX, y0 + centreY)
            )),
            quadrant4 = LeafNode(capacity, nextLevel, BoundingBox(
                bottomLeft = Point(x0 + centreX, y0),
                topRight = Point(x1, y0 + centreY)
            ))
        )

        points.forEach { branch.insert(it) }

        return branch
    }

    override fun toString(): String {
        return "LeafNode(capacity=$capacity, levels=$levels, boundingBox=$boundingBox, points=$points)"
    }
}
