package com.github.michaelbull.quadtree

import com.github.michaelbull.quadtree.math.BoundingBox
import com.github.michaelbull.quadtree.math.Point
import com.github.michaelbull.quadtree.node.BranchNode
import com.github.michaelbull.quadtree.node.LeafNode
import com.github.michaelbull.quadtree.node.Node

/**
 * A quadtree is a tree data structure in which each internal node has exactly four children.
 *
 * @param boundingBox The [BoundingBox] in which [points][Point] may lie in this quadtree.
 * @param capacity The maximum amount of [points][Point] a [LeafNode] may store before [splitting][LeafNode.split] into a [BranchNode].
 * @param levels The amount of levels the quadtree may grow in depth.
 */
class QuadTree(
    boundingBox: BoundingBox,
    capacity: Int = 200,
    levels: Int = 10
) {

    private var root: Node = LeafNode(capacity, levels, boundingBox)

    fun insert(point: Point) {
        root = root.insert(point)
    }

    fun intersect(other: BoundingBox): List<Point> {
        return root.intersect(other)
    }

    override fun toString(): String {
        return "QuadTree(root=$root)"
    }
}
