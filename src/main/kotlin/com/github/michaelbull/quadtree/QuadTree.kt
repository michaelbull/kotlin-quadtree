package com.github.michaelbull.quadtree

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point
import com.github.michaelbull.quadtree.node.LeafNode
import com.github.michaelbull.quadtree.node.Node

class QuadTree(boundingBox: BoundingBox) {
    private var root: Node = LeafNode(boundingBox)

    fun insert(point: Point) {
        root = root.insert(point)
    }

    fun intersect(other: BoundingBox): List<Point> {
        return root.intersect(other)
    }
}
