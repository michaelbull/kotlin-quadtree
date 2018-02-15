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
        when (point) {
            in northEast -> northEast = northEast.insert(point)
            in northWest -> northWest = northWest.insert(point)
            in southEast -> southEast = southEast.insert(point)
            in southWest -> southWest = southWest.insert(point)
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
}
