package com.algorithms.graphs;

import com.algorithms.graphs.dfs.DepthFirstSearch;
import com.algorithms.graphs.model.Node;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Main entry-point for graphs algorithm exercises.
 */
public class GraphsMain {

    public static void main(String[] args) {
        testDFSSerializeBinaryTree();
        testDFSProcessing();
    }

    private static void testDFSSerializeBinaryTree() {
        final String serializedDFSTree = "ABDNNENNCFNNGNN";
        final Node root = DepthFirstSearch.deserializeBinaryTree(serializedDFSTree);

        assert root.value() == 'A';
        assert root.getLeft().value() == 'B';
        assert root.getRight().value() == 'C';
        assert root.getLeft().getLeft().value() == 'D';
        assert root.getLeft().getRight().value() == 'E';
        assert root.getRight().getLeft().value() == 'F';
        assert root.getRight().getRight().value() == 'G';

        final String reSerialized = DepthFirstSearch.serializeBinaryTree(root);

        assert reSerialized.equalsIgnoreCase(serializedDFSTree);
    }

    private static void testDFSProcessing() {
        final String serializedDFSTree = "ABDNNENNCFNNGNN";
        final Node root = DepthFirstSearch.deserializeBinaryTree(serializedDFSTree);

        final LinkedList<Character> builder = new LinkedList<>();
        final Consumer<Node> consumer =
                (node) -> Optional.ofNullable(node).ifPresent((n) -> builder.add(n.value()));

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.PRE);
        System.out.println("PRE:" + builder);
        builder.clear();

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.IN);
        System.out.println("IN:" + builder);
        builder.clear();

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.POST);
        System.out.println("POST:" + builder);
        builder.clear();
    }

}
