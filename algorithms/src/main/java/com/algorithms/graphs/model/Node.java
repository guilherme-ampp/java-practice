package com.algorithms.graphs.model;

/**
 * The node of a binary tree -- An acyclic connected graph where each node has at most two child nodes.
 */
public class Node {

    private final char value;
    private Node left;
    private Node right;

    public Node(final char value) {
        this.value = value;
    }

    public void setLeft(final Node left) {
        this.left = left;
    }

    public void setRight(final Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public char value() {
        return value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
