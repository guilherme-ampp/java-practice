package com.datastructures;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedList<T> {

    private final AtomicInteger sizeCounter = new AtomicInteger(0);
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
    }

    /**
     * Adds a new node at the tail of the list.
     *
     * Time O(1)
     * Space O(1)
     */
    public void add(T value) {
        if (head == null) {
            head = new Node<>(value);
            tail = head;
        } else if(head == tail) {
            // H/T
            // H - T
            final Node<T> newTail = new Node<>(value);
            newTail.setPrevious(head);
            head.setNext(newTail);
            tail = newTail;
        } else {
            // H - T
            // H - T - N
            final Node<T> newNode = new Node<>(value);
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        sizeCounter.incrementAndGet();
    }

    /**
     * Peeks at the value of the head of the list.
     *
     * Time O(1)
     * Space O(1)
     */
    public T peek() {
        return head.getValue();
    }

    /**
     * Inserts node at the given position.
     *
     * Time O(1) for head or tail - Time O(i) for middle
     * Space O(1)
     */
    public void insertAt(int index, T value) {
        // A - B - C
        // A - N - B - C
        if (index < 0) {
            throw indexOutOfBounds(index);
        }
        if (index == 0) {
            // insert at head
            final Node<T> newNode = new Node<>(value);
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = newNode;
        } else if (index == sizeCounter.get()) {
            // insert at tail
            final Node<T> newNode = new Node<>(value);
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else {
            // insert in middle
            final Node<T> b = getNodeAt(index);
            if (b == null) {
                throw indexOutOfBounds(index);
            }
            final Node<T> n = new Node<>(value);
            final Node<T> a = b.getPrevious();

            a.setNext(n);
            n.setPrevious(a);
            n.setNext(b);
        }
        sizeCounter.incrementAndGet();
    }

    /**
     * Polls the head of the list out and returns its value.
     *
     * Time O(1)
     * Space O(1)
     */
    public T poll() {
        if (head == null) {
            return null;
        }

        T value = head.value;
        head = head.getNext();
        sizeCounter.decrementAndGet();
        return value;
    }

    /**
     * Returns the value at the given index.
     *
     * Time: O(i)
     * Space: O(1)
     */
    public T getAt(int index) {
        return Optional.ofNullable(getNodeAt(index))
                .map(Node::getValue)
                .orElseThrow(() -> indexOutOfBounds(index));
    }

    /**
     * Time O(i)
     * Space O(1)
     */
    public T removeAt(int index) {
        final Node<T> toBeRemoved = Optional.ofNullable(getNodeAt(index)).orElseThrow(() -> indexOutOfBounds(index));
        removeNode(toBeRemoved);
        sizeCounter.decrementAndGet();
        return toBeRemoved.getValue();
    }

    /**
     * Time O(n)
     * Space O(1)
     */
    public boolean remove(T value) {
        if (head == null) {
            return false;
        }
        Node<T> pointer = head;
        while (pointer != null) {
            if (pointer.getValue() == value) {
                removeNode(pointer);
                return true;
            }
            pointer = pointer.getNext();
        }
        return false;
    }

    /**
     * Removes the given node from the list
     * Time O(1)
     * Space O(1)
     */
    private void removeNode(Node<T> toBeRemoved) {
        if (toBeRemoved == tail) {
            // H - N - T(toBeRemoved)
            // H - N
            tail = tail.getPrevious();
            tail.setNext(null);
        } else if (toBeRemoved == head) {
            // H - N - N
            //     H - N
            head = head.getNext();
            head.setPrevious(null);
        } else {
            // A - B - C
            // A - C
            Node<T> a = toBeRemoved.getPrevious();
            Node<T> c = toBeRemoved.getNext();
            a.setNext(c);
            c.setPrevious(a);
            toBeRemoved.setNext(null);
            toBeRemoved.setPrevious(null);
        }
        sizeCounter.decrementAndGet();
    }

    private IndexOutOfBoundsException indexOutOfBounds(int index) {
        return new IndexOutOfBoundsException(String.format("Index %d is out of bounds!", index));
    }

    /**
     * Traverses to the node at the given index.
     * Time O(i)
     * Space O(1)
     */
    private Node<T> getNodeAt(int index) {
        if (index < 0) {
            throw indexOutOfBounds(index);
        }
        if (head == null) {
            return null;
        }
        Node<T> pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.getNext();
            if (pointer == null) {
                break;
            }
        }
        return pointer;
    }

    /**
     * Internal Node class for the double-linked list.
     */
    private static final class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public Node<T> getNext() {
            return next;
        }
    }

}
