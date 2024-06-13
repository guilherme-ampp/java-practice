package com.patterns;

import com.datastructures.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tree depth-first search is another traversal method that explores the tree by traveling
 * as far as possible along a branch before exploring the other branches. Because it
 * visits each node of the tree exactly once, it guarantees a traversal in O(n) time.
 */
public class DepthFirstSearchTest {

    /**
     * Preorder traversal.
     * We start at the root node and visit it first. Then, we recursively traverse
     * the left subtree, followed by the right subtree.
     * This means we explore nodes in the order of root, left, and right.
     * It’s like starting at the top of a tree and moving down, exploring branches as we go.
     */
    @Test
    public void testPreorderTraversal() {
        final TreeNode root = createTree();
        final StringBuilder builder = new StringBuilder();
        dfsPreorder(root, (node) -> builder.append(node.getValue()));

        assertEquals("ABDECFG", builder.toString());
    }

    /**
     * In order traversal.
     * Starting from the root, we first traverse the left subtree, then visit the current node,
     * and finally explore the right subtree.
     * This order means we’ll visit nodes in ascending order if the tree represents numbers
     * It’s like exploring a tree from the bottom-left corner to the right, moving up gradually.
     */
    @Test
    public void testInorderTraversal() {
        TreeNode root = createTree();
        final StringBuilder builder = new StringBuilder();
        dfsInorder(root, (node) -> builder.append(node.getValue()));

        assertEquals("DBEAFCG", builder.toString());
    }

    /**
     * Post order traversal.
     * Starting from the root, we first traverse the left subtree, then the right subtree, and finally visit
     * the current node. This order means we explore nodes from the bottom up, going from the leaves toward the root.
     * It’s like examining a tree from its outermost branches inward, reaching the trunk last.
     */
    @Test
    public void testPostorderTraversal() {
        TreeNode root = createTree();
        final StringBuilder builder = new StringBuilder();
        dfsPostorder(root, (node) -> builder.append(node.getValue()));

        assertEquals("DEBFGCA", builder.toString());
    }

    private static void dfsPreorder(TreeNode node, Consumer<TreeNode> visitor) {
        if (node == null) {
            return;
        }
        visitor.accept(node);
        dfsPreorder(node.getLeft(), visitor);
        dfsPreorder(node.getRight(), visitor);
    }

    private static void dfsInorder(TreeNode node, Consumer<TreeNode> visitor) {
        if (node == null) {
            return;
        }
        dfsInorder(node.getLeft(), visitor);
        visitor.accept(node);
        dfsInorder(node.getRight(), visitor);
    }

    private static void dfsPostorder(TreeNode node, Consumer<TreeNode> visitor) {
        if (node == null) {
            return;
        }
        dfsPostorder(node.getLeft(), visitor);
        dfsPostorder(node.getRight(), visitor);
        visitor.accept(node);
    }

    /**
     * Creates a simple tree:
     *        A
     *      /  \
     *    B     C
     *  /  \   / \
     * D   E  F   G
     *
     * @return The root of this tree
     */
    private static TreeNode createTree() {
        char[] serialized = "ABD__E__CF__G__".toCharArray();
        return createNode(serialized, new AtomicInteger(0));
    }

    private static TreeNode createNode(char[] values, AtomicInteger index) {
        if (index.get() < values.length && values[index.get()] == '_') {
            index.getAndIncrement();
            return null;
        }
        final TreeNode node = new TreeNode(values[index.getAndIncrement()]);
        node.setLeft(createNode(values, index));
        node.setRight(createNode(values, index));
        return node;
    }

}
