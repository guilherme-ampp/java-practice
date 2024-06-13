package com.datastructures;

public class TreeNode {

    private final char value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

}
