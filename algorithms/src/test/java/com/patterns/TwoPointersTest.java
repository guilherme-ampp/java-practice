package com.patterns;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

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

    /**
     * Time: O(n)
     * Space: O(1)
     */
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

    /**
     * Time: O(n)
     * Space: O(1)
     */
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

    /**
     * Time: O(n)
     * Space: O(1)
     */
    @Test
    public void testPairWithGivenSum() {
        final int[] sortedArray = new int[]{2, 3, 5, 7, 11, 13};
        final int findSum = 14;
        int[] result = null;

        int pLeft = 0;
        int pRight = sortedArray.length - 1;

        while (pLeft < pRight) {
            int currentSum = sortedArray[pLeft] + sortedArray[pRight];
            if (currentSum == findSum) {
                result = new int[]{pLeft, pRight};
                break;
            } else if (currentSum < findSum) {
                pLeft++;
            } else {
                pRight--;
            }
        }

        assertNotNull(result);
        // we expect the indexes to the values that match the sum
        assertEquals(1, result[0]);
        assertEquals(4, result[1]);
    }

    /**
     * Time: O(n2) -- from O(nlog(n) + n2)
     * Space: O(log(n))
     */
    @Test
    public void testSumOfThreeValues() {
        final int[] array = new int[]{6, 1, 2, 20, 5, 8};
        final int target = 31;

        Arrays.sort(array);  // time O(nlog(n)) // space O(log(n))

        // the three indexes must be distinct
        // at each iteration, we'll traverse with the low and high pointers to find the triplet that matches the
        // target result
        final Supplier<Boolean> mainLoopFunc = () -> {
            // time O(n2) // space O(1)
            for (int i = 0; i < array.length; i++) {
                int low = i + 1;
                int high = array.length - 1;
                while (low < high) {
                    int currentSum = array[low] + array[high] + array[i];
                    if (currentSum == target) {
                        return true;
                    }
                    if (currentSum < target) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
            return false;
        };

        assertTrue(mainLoopFunc.get());
    }

    private static String arrayToString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(String::valueOf)
                .reduce((i, j) -> String.format("%s%s", i, j))
                .orElse("");
    }

}
