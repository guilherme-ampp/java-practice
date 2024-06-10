package com.patterns;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Use whenever there is a requirements to find two data elements in an array that satisfy a certain condition.
 * <p>
 * The two pointers can be used to traverse the data structure in one or both directions -- depending on the problem.
 * <p>
 * The problem matches this pattern if all conditions are fulfilled:
 * - Linear data structure
 * - Process pairs
 * - Dynamic pointer movement
 */
public class TwoPointersTest {

    @Test
    public void testReverseArrayInPlace() {
        final int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};

        int pointerStart = 0;
        int pointerEnd = array.length - 1;

        while (pointerStart < pointerEnd) {
            int tmp = array[pointerStart];
            array[pointerStart++] = array[pointerEnd];
            array[pointerEnd--] = tmp;
        }

        assertEquals("7654321", arrayToString(array));
    }

    @Test
    public void testMoveAllZerosToTheEnd() {
        final int[] sortedArray = new int[]{1, 2, 0, 0, 5, 0, 8, 1};
        int pointerLeft = 0;
        int pointerRight = 0;

        while (pointerRight < sortedArray.length) {
            if (sortedArray[pointerRight] != 0) {
                // swap and move both pointers!
                int tmp = sortedArray[pointerLeft];
                sortedArray[pointerLeft++] = sortedArray[pointerRight];
                sortedArray[pointerRight++] = tmp;
            } else if (sortedArray[pointerRight] == 0) {
                // move right pointer
                pointerRight++;
            }
        }

        System.out.printf("Result: %s%n", Arrays.toString(sortedArray));
        assertEquals("12581000", arrayToString(sortedArray));
    }

    private static String arrayToString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .reduce((i, j) -> String.format("%s%s", i, j))
                .orElse("");
    }

}
