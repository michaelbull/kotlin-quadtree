package com.github.michaelbull.quadtree.node

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point

class BranchNode(
    capacity: Int,
    levels: Int,
    boundingBox: BoundingBox,
    private var northEast: Node,
    private var northWest: Node,
    private var southEast: Node,
    private var southWest: Node
) : Node(capacity, levels, boundingBox) {

    override fun insert(point: Point): Node {
        require(point in this) { "$point is outside of $this" }

        if (point.x >= northEast.boundingBox.bottomLeft.x) {
            if (point.y >= northEast.boundingBox.bottomLeft.y) {
                northEast = northEast.insert(point)
            } else {
                southEast = southEast.insert(point)
            }
        } else {
            if (point.y >= northWest.boundingBox.bottomLeft.y) {
                northWest = northWest.insert(point)
            } else {
                southWest = southWest.insert(point)
            }
        }

        return this
    }

    override fun intersect(other: BoundingBox): List<Point> {
        return if (boundingBox.intersects(other)) {
            northEast.intersect(other) + northWest.intersect(other) + southEast.intersect(other) + southWest.intersect(other)
        } else {
            emptyList()
        }
    }

    override fun toString(): String {
        return "BranchNode(capacity=$capacity, levels=$levels, boundingBox=$boundingBox)"
    }
}
