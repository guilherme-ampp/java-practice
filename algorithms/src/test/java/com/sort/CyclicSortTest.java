package com.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CyclicSortTest {

    @ParameterizedTest
    @MethodSource("cyclicSortData")
    public void testCyclicSort(int[] array, int[] expected) {
        cyclicSort(array);
        System.out.println(Arrays.toString(array));
        assertArrayEquals(expected, array);
    }

    public void cyclicSort(int[] array) {
        int index = 0;
        while (index < array.length) {
            // item `array[index]` should be at position `array[index] - 1`
            //   e.g. item 2 should be at position 2 - 1 = 1
            int correctPosition = array[index] - 1;
            if (correctPosition > array.length) {
                // update the index and keep moving if the correct position is out of bounds of the array
                index++;
            } else {
                // therefore, the correct item expected at array[index] is the value index + 1
                if (array[index] == index + 1 || array[correctPosition] == array[index]) {
                    // update the index:
                    //   if the item is at its correct position; or
                    //   if the item is equal to the element with which it's supposed to be swapped -- there might be dups
                    index++;
                } else {
                    // swap values in the index position with the correct position
                    int aux = array[index];
                    array[index] = array[correctPosition];
                    array[correctPosition] = aux;
                }
            }
        }
    }

    private static Stream<Arguments> cyclicSortData() {
        return Stream.of(
                Arguments.of(new int[]{2, 6, 4, 3, 1, 5}, new int[]{1, 2, 3, 4, 5, 6}),
                Arguments.of(new int[]{3, 1, 2, 5, 3}, new int[]{1, 2, 3, 3, 5}),
                Arguments.of(new int[]{3, 1, 2, 5, 2}, new int[]{1, 2, 3, 2, 5}),
                Arguments.of(new int[]{6, 7, 9, 2, 3, 13, 11}, new int[]{13, 2, 3, 11, 9, 6, 7})
        );
    }

}
