package com.algorithms.graphs.dfs;

import com.algorithms.graphs.model.Node;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Depth first search.
 */
public class DepthFirstSearch {

    public static Node deserializeBinaryTree(final String serializedTree) {
        return dfs(serializedTree.toCharArray(), new AtomicInteger(0));
    }

    public static String serializeBinaryTree(final Node root) {
        final StringBuilder builder = new StringBuilder();
        dfsSerialize(builder, root);
        return builder.toString();
    }

    private static Node dfs(final char[] values, final AtomicInteger index) {
        if (index.get() < values.length && values[index.get()] == 'N') {
            index.getAndIncrement();
            return null;
        }

        Node node = new Node(values[index.getAndIncrement()]);
        node.setLeft(dfs(values, index));
        node.setRight(dfs(values, index));

        return node;
    }

    private static void dfsSerialize(final StringBuilder builder, final Node node) {
        if (node == null) {
            builder.append("N");
            return;
        } else {
            builder.append(node.value());
        }

        dfsSerialize(builder, node.getLeft());
        dfsSerialize(builder, node.getRight());
    }

}
