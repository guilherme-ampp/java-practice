package com.patterns;

/**
 * The modified binary search pattern builds upon the classic binary search, allowing us to:
 * 1/ Solve problems in which input data are modified in a way which affects the search process. e.g. sorted and then
 * rotated around an unknown pivot.
 * 2/ Adapt the comparison logic to accommodate multiple requirements to what we are searching. e.g. finding a target
 * range rather than a single target, or finding the rightmost of leftmost occurrence of a target value.
 *
 * The problem matches this pattern if:
 * - Target value in sorted data
 * - Partially sorted segments
 * The problem does NOT match this pattern if:
 * - Lack of direct addressing
 * - Unsorted or inappropriately sorted data (not sorted according to criteria relevant to the search)
 * - Non-value-based solutions
 */
public class ModifiedBinarySearchTest {
}
