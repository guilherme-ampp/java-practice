package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindTheKthLargestElementTest {

    static class BST {
        public int value;
        public BST left = null;
        public BST right = null;

        public BST(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "BST{" +
                    "value=" + value +
                    '}';
        }
    }

    @ParameterizedTest
    @MethodSource("treeTestData")
    public void testFindKthLargestValueInBst(BST tree, int k, int expectedValue) {
        assertEquals(expectedValue, findKthLargestValueInBst(tree, k));
        assertEquals(expectedValue, findKthLargestValueInBst_DFS(tree, k));
        assertEquals(expectedValue, findKthLargestValueInBst_Best(tree, k));
    }

    /**
     * The best solution doing a reverse in-order search
     * Time: O(h + k) -- where h is the height of the tree
     */
    public int findKthLargestValueInBst_Best(BST tree, int k) {
        final AtomicReference<BST> nodeRef = new AtomicReference<>();
        reverseOrderSearch(tree, new AtomicInteger(k), nodeRef);
        return nodeRef.get().value;
    }

    /**
     * Naive solution with in order depth-first search and a linked list.
     * -- This is not so bad, we used the fact that we have a Binary Search Tree
     * Time: O(n)
     * Space: O(n)
     */
    public int findKthLargestValueInBst_DFS(BST tree, int k) {
        final LinkedList<BST> orderedNodes = new LinkedList<>();
        inOrderTraversal(tree, orderedNodes::add);

        BST kthElement = null;
        for (int i=0; i<k; i++) {
            kthElement = orderedNodes.pollLast();
        }
        return Optional.ofNullable(kthElement).map((e) -> e.value).orElse(-1);
    }

    /**
     * Naive solution using Breadth First Search a maxHeap
     * -- this was bad because we didn't use the fact that the tree is a Binary Search Tree!
     * Time: O(n)
     * Space: O(n)
     */
    public int findKthLargestValueInBst(BST tree, int k) {
        final PriorityQueue<BST> maxHeap = new PriorityQueue<>(Comparator.comparingInt((b) -> ((BST) b).value).reversed());

        final Queue<BST> queue = new LinkedList<>();
        queue.add(tree);
        while (!queue.isEmpty()) {
            final BST node = queue.poll();
            maxHeap.add(node);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        BST kthElement = null;
        for (int i=0; i<k; i++) {
            kthElement = maxHeap.poll();
        }
        return Optional.ofNullable(kthElement).map((e) -> e.value).orElse(-1);
    }

    private void inOrderTraversal(BST node, Consumer<BST> visitor) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, visitor);
        visitor.accept(node);
        inOrderTraversal(node.right, visitor);
    }

    private void reverseOrderSearch(BST node, AtomicInteger kCountdown, AtomicReference<BST> nodeRef) {
        if (node == null || nodeRef.get() != null) {
            return;
        }
        reverseOrderSearch(node.right, kCountdown, nodeRef);
        if (kCountdown.decrementAndGet() == 0) {
            nodeRef.set(node);
        }
        reverseOrderSearch(node.left, kCountdown, nodeRef);
    }

    private static Stream<Arguments> treeTestData() {
        return Stream.of(
                Arguments.of(createTree("15,5,2,1,_,_,3,_,_,5,_,_,20,17,_,_,22,_,_"), 3, 17)
        );
    }

    /**
     * Creates a BST
     *
     * @return The root of this tree
     */
    private static BST createTree(final String serializedTree) {
        String[] serializedTokens = serializedTree.split(",");
        return createNode(serializedTokens, new AtomicInteger(0));
    }

    private static BST createNode(String[] values, AtomicInteger index) {
        if (index.get() < values.length && values[index.get()].equals("_")) {
            index.getAndIncrement();
            return null;
        }
        final BST node = new BST(Integer.parseInt(values[index.getAndIncrement()]));
        node.left = createNode(values, index);
        node.right = createNode(values, index);
        return node;
    }
}
