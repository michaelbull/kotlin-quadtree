package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

class BranchNode(
    capacity: Int,
    levels: Int,
    boundingBox: BoundingBox,
    private var quadrant1: Node,
    private var quadrant2: Node,
    private var quadrant3: Node,
    private var quadrant4: Node
) : Node(capacity, levels, boundingBox) {

    override fun insert(point: Point): Node {
        require(point in this) { "$point is outside of $this" }

        if (point.y >= quadrant1.boundingBox.bottomLeft.y) {
            if (point.x >= quadrant1.boundingBox.bottomLeft.x) {
                quadrant1 = quadrant1.insert(point)
            } else {
                quadrant2 = quadrant2.insert(point)
            }
        } else {
            if (point.x >= quadrant4.boundingBox.bottomLeft.x) {
                quadrant4 = quadrant4.insert(point)
            } else {
                quadrant3 = quadrant3.insert(point)
            }
        }

        return this
    }

    override fun intersect(other: BoundingBox): List<Point> {
        return if (boundingBox.intersects(other)) {
            quadrant1.intersect(other) + quadrant2.intersect(other) + quadrant4.intersect(other) + quadrant3.intersect(other)
        } else {
            emptyList()
        }
    }

    override fun toString(): String {
        return "BranchNode(capacity=$capacity, levels=$levels, boundingBox=$boundingBox)"
    }
}
