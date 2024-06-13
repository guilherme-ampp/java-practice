package com.patterns;

import com.datastructures.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BreadthFirstSearchTest {


    @Test
    public void testBFSPreorder() {
        final TreeNode<Integer> root = createTree();
        final StringBuilder builder = new StringBuilder();
        bfsPreorder(root, (node) -> builder.append(node.getValue()));
        assertEquals("12345678", builder.toString());
    }

    private static void bfsPreorder(TreeNode<Integer> node, Consumer<TreeNode<Integer>> visitor) {
        final Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            final TreeNode<Integer> currentNode = queue.poll();
            if (currentNode == null) {
                continue;
            }
            visitor.accept(currentNode);
            queue.add(currentNode.getLeft());
            queue.add(currentNode.getRight());
        }
    }


    /**
     * Creates a simple tree:
     *          1
     *        /  \
     *      2     3
     *    /  \   / \
     *   4   5  6   7
     *  /
     * 8
     *
     * @return The root of this tree
     */
    private static TreeNode<Integer> createTree() {
        Integer[] serialized = new Integer[]{1, 2, 4, 8, null, null, null, 5, null, null, 3, 6, null, null, 7, null, null};
        return createNode(serialized, new AtomicInteger(0));
    }

    private static TreeNode<Integer> createNode(Integer[] values, AtomicInteger index) {
        if (index.get() < values.length && values[index.get()] == null) {
            index.getAndIncrement();
            return null;
        }
        final TreeNode<Integer> node = new TreeNode<>(values[index.getAndIncrement()]);
        node.setLeft(createNode(values, index));
        node.setRight(createNode(values, index));
        return node;
    }

}
