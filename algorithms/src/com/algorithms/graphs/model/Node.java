package com.algorithms.graphs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The node of a tree.
 */
public class Node {

    private final String value;
    private final List<Node> children;

    public Node(final String value) {
        this.value = value;
        children = new ArrayList<>();
    }

    public String value() {
        return value;
    }

    public List<Node> children() {
        return children;
    }

}
