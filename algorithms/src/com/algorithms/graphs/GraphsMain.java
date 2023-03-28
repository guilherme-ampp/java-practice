package com.algorithms.graphs;

import com.algorithms.graphs.dfs.DepthFirstSearch;
import com.algorithms.graphs.model.Node;

/**
 * Main entry-point for graphs algorithm exercises.
 */
public class GraphsMain {

    public static void main(String[] args) {
        final String serializedDFSTree = "ABDNNENNCFNNGNN";
        final Node root = DepthFirstSearch.createBinaryTree(serializedDFSTree);

        assert root.value() == 'A';
        assert root.getLeft().value() == 'B';
        assert root.getRight().value() == 'C';
        assert root.getLeft().getLeft().value() == 'D';
        assert root.getLeft().getRight().value() == 'E';
        assert root.getRight().getLeft().value() == 'F';
        assert root.getRight().getRight().value() == 'G';

        final String reSerialized = DepthFirstSearch.serializeTree(root);

        assert reSerialized.equalsIgnoreCase(serializedDFSTree);
    }

}
