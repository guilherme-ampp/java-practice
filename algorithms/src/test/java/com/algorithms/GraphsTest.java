package com.algorithms;

import com.algorithms.graphs.dfs.DepthFirstSearch;
import com.algorithms.graphs.model.Node;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GraphsTest {

    @Test
    public void testDFSSerializeBinaryTree() {
        final String serializedDFSTree = "ABDNNENNCFNNGNN";
        final Node root = DepthFirstSearch.deserializeBinaryTree(serializedDFSTree);

        assertEquals(root.value(), 'A');
        assertEquals(root.getLeft().value(), 'B');
        assertEquals(root.getRight().value(), 'C');
        assertEquals(root.getLeft().getLeft().value(), 'D');
        assertEquals(root.getLeft().getRight().value(), 'E');
        assertEquals(root.getRight().getLeft().value(), 'F');
        assertEquals(root.getRight().getRight().value(), 'G');

        final String reSerialized = DepthFirstSearch.serializeBinaryTree(root);

        assertTrue(reSerialized.equalsIgnoreCase(serializedDFSTree));
    }

    @Test
    public void testDFSProcessing() {
        final String serializedDFSTree = "ABDNNENNCFNNGNN";
        final Node root = DepthFirstSearch.deserializeBinaryTree(serializedDFSTree);

        final LinkedList<Character> builder = new LinkedList<>();
        final Consumer<Node> consumer =
                (node) -> Optional.ofNullable(node).ifPresent((n) -> builder.add(n.value()));

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.PRE);
        System.out.println("PRE:" + builder);
        assertEquals("ABDECFG", listToString(builder));
        builder.clear();

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.IN);
        System.out.println("IN:" + builder);
        assertEquals("DBEAFCG", listToString(builder));
        builder.clear();

        DepthFirstSearch.dfs(root, consumer, DepthFirstSearch.Policy.POST);
        System.out.println("POST:" + builder);
        assertEquals("DEBFGCA", listToString(builder));
        builder.clear();
    }

    private static String listToString(List<Character> characterList) {
        return characterList.stream()
                .map(String::valueOf)
                .reduce((first, second) -> String.format("%s%s", first, second))
                .orElse("");
    }

}
