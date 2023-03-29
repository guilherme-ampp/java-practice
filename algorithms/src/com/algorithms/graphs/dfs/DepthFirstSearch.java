package com.algorithms.graphs.dfs;

import com.algorithms.graphs.model.Node;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Depth first search.
 * 1) Go deep to the left until a base case is met
 * 2) Goo deep to the right until a base case is met.
 * 3) Return result.
 *
 * The implementation will always use a Stack, either the Call stack or our own Stack (LIFO).
 *
 * Sample of perfect binary tree:
 *      A
 *    /  \
 *   B    C
 *  / \  / \
 * D  E F  G
 *
 */
public class DepthFirstSearch {

    public enum Policy {
        PRE, IN, POST;
    }

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

    public static void dfs(final Node node, final Consumer<Node> consumer, final Policy policy) {
        if (node == null) return;

        if (Policy.PRE.equals(policy)) {
            consumer.accept(node);
        }
        dfs(node.getLeft(), consumer, policy);
        if (Policy.IN.equals(policy)) {
            consumer.accept(node);
        }
        dfs(node.getRight(), consumer, policy);
        if (Policy.POST.equals(policy)) {
            consumer.accept(node);
        }
    }
}
