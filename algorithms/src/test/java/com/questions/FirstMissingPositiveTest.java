package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given an unsorted integer array, nums, return the smallest missing positive integer. Create an algorithm that runs
 * with an O(n) time complexity and utilizes a constant amount of space O(1).
 * <p>
 * Note: The smallest missing positive isn’t the first positive number that’s missing in the range of elements in
 * the input, but the first positive number that’s missing if we start from 1
 */
public class FirstMissingPositiveTest {

    @ParameterizedTest
    @MethodSource("firstMissingPositiveData")
    public void testFirstMissingPositive(int[] array, int expectedOutput) {
        cyclicSort(array);
        int firstMissingPositive = array.length + 1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i + 1) {
                firstMissingPositive = i + 1;
                break;
            }
        }
        assertEquals(expectedOutput, firstMissingPositive);
    }

    public void cyclicSort(int[] array) {
        int index = 0;
        while (index < array.length) {
            System.out.printf("index[%d] array[%s]%n", index, Arrays.toString(array));
            int correctPosition = array[index] - 1;
            if (correctPosition < 0 || correctPosition >= array.length) {
                System.out.printf("index[%d] \tOUT OF RANGE%n", index);
                index++;
                continue;
            }
            // if item is in the correct position, increment index
            if (index == correctPosition) {
                index++;
                System.out.printf("index[%d] \tCORRECT%n", index);
            } else {
                int aux = array[correctPosition];
                if (aux == array[index]) {
                    // if the values we are about to swap are the same, just move the index,
                    // otherwise we'll go into an infinite loop
                    System.out.printf("index[%d] \tEQUALS%n", index);
                    index++;
                } else {
                    // swap with the correct position
                    array[correctPosition] = array[index];
                    array[index] = aux;
                    System.out.printf("index[%d] \tSWAP!%n", index);
                }
            }
            System.out.printf("index[%d] array[%s]%n", index, Arrays.toString(array));
        }
    }

    private static Stream<Arguments> firstMissingPositiveData() {
        return Stream.of(
                Arguments.of(new int[]{7, 8, 9, 11, 12}, 1),
                Arguments.of(new int[]{-1, -2, -3, -4, 0}, 1),
                Arguments.of(new int[]{1, 2, 4, 5}, 3),
                Arguments.of(new int[]{3, 4, -1, 2}, 1),
                Arguments.of(new int[]{3, 4, 2, 1}, 5),
                Arguments.of(new int[]{3, 5, 6, 2, 1}, 4),
                Arguments.of(new int[]{3, 4, -1, 1}, 2),
                Arguments.of(new int[]{3, 4, 4, -1, 1}, 2)
        );
    }


}
