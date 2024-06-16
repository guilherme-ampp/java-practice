package com.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkedListTest {

    private LinkedList<String> list;

    @BeforeEach
    public void setup() {
        list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @Test
    public void testAddToList() {
        assertEquals("C", list.getAt(2));
    }

    @Test
    public void testPeekList() {
        assertEquals("A", list.peek());
        assertEquals("A", list.peek());
    }

    @Test
    public void testPollList() {
        assertEquals("A", list.poll());
        assertEquals("B", list.poll());
        assertEquals("C", list.poll());
        assertNull(list.poll());

        list.add("N");
        assertEquals("N", list.peek());
        assertEquals("N", list.getAt(0));
        assertEquals("N", list.poll());
    }

    @Test
    public void testInsertion() {
        list.insertAt(1, "N1");
        assertEquals("A", list.getAt(0));
        assertEquals("N1", list.getAt(1));
        assertEquals("B", list.getAt(2));

        // insert at head
        list.insertAt(0, "N2");
        assertEquals("N2", list.getAt(0));
        assertEquals("A", list.getAt(1));
        assertEquals("N1", list.getAt(2));
        assertEquals("B", list.getAt(3));

        // insert at tail
        list.insertAt(5, "N3");
        assertEquals("C", list.getAt(4));
        assertEquals("N3", list.getAt(5));

        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(10, "invalid"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, "invalid"));
    }

    @Test
    public void testRetrieval() {
        assertEquals("A", list.getAt(0));
        assertEquals("C", list.getAt(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.getAt(6));
        assertThrows(IndexOutOfBoundsException.class, () -> list.getAt(-1));
    }

    @Test
    public void testRemoveValue() {
        assertTrue(list.remove("A"));
        assertEquals("B", list.peek());
    }

    @Test
    public void testRemoveIndex() {
        final String value = list.removeAt(0);
        assertEquals("A", value);
        assertEquals("B", list.peek());
    }

}
