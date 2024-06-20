package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TransposeMatrixTest {

    @ParameterizedTest
    @MethodSource("transposeMatrixData")
    public void testTransposeMatrix(int[][] matrix, int[][] expectedOutput) {
        assertArrayEquals(expectedOutput, transposeMatrix(matrix));
    }

    public int[][] transposeMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return matrix;
        }

        int[][] transposedMatrix = matrix;
        if (isSquare(matrix)) {
            // for square matrices -- make it time O(w * h) and space O(1)
            int startingCol = 0;
            for (int row = 0; row < matrix.length; row++) {
                for (int col = startingCol; col < matrix[row].length; col++) {
                    swap(matrix, col, row, row, col);
                }
                startingCol++;
            }
        } else {
            // for other rectangles -- time O(w * h) and space O(w * h)
            transposedMatrix = new int[matrix[0].length][matrix.length];
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    transposedMatrix[col][row] = matrix[row][col];
                }
            }
        }

        return transposedMatrix;
    }

    private static boolean isSquare(int[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    private static void swap(int[][] matrix, int fromCol, int fromRow, int toCol, int toRow) {
        if (fromCol == fromRow) {
            return;
        }
        int aux = matrix[toRow][toCol];
        matrix[toRow][toCol] = matrix[fromRow][fromCol];
        matrix[fromRow][fromCol] = aux;
    }

    private static Stream<Arguments> transposeMatrixData() {
        return Stream.of(
                Arguments.of(new int[][]{}, new int[][]{}),
                Arguments.of(new int[][]{
                        new int[]{1, 2},
                        new int[]{3, 4},
                        new int[]{5, 6}
                }, new int[][]{
                        new int[]{1, 3, 5},
                        new int[]{2, 4, 6}
                }),
                Arguments.of(new int[][]{
                        new int[]{1, 2, 3},
                        new int[]{4, 5, 6},
                        new int[]{7, 8, 9}
                }, new int[][]{
                        new int[]{1, 4, 7},
                        new int[]{2, 5, 8},
                        new int[]{3, 6, 9}
                })
        );
    }
}
