package com.patterns;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Use whenever there is a requirements to find two data elements in an array that satisfy a certain condition.
 *
 * The two pointers can be used to traverse the data structure in one or both directions -- depending on the problem.
 */
public class TwoPointersTest {

    @Test
    public void testReverseArrayInPlace() {
        final int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};
        final String expected = "7654321";

        int pointerStart = 0;
        int pointerEnd = array.length - 1;

        while (pointerStart < pointerEnd) {
            int tmp = array[pointerStart];
            array[pointerStart++] = array[pointerEnd];
            array[pointerEnd--] = tmp;
        }

        assertEquals(expected,
                Arrays.stream(array)
                        .mapToObj(String::valueOf)
                        .reduce((i, j) -> String.format("%s%s", i, j))
                        .orElse(""));
    }

}
