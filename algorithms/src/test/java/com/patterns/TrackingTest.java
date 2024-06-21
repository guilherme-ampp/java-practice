package com.patterns;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Knowing what to track involves counting the occurrences of elements in a given data structure (e.g. array or string),
 * and using this frequency information to solve the problem efficiently. The pattern is divided into two phases:
 * 1/ Counting: iterate and count the frequency of each element
 * 2/ Utilization: solve the issue:
 *   e.g. find the most frequent element,
 *        identify elements that occur once,
 *        check if two arrays are permutations of each other,
 *        check if the player wins the game
 *
 *  This pattern matches the problem if either are true:
 *  - Frequency tracking
 *  - Pattern recognition
 *  - Fixed set of possibilities: the problem requires choosing the output from a fixes set of
 *    possibilities - yes/no, true/false, valid/invalid
 */
public class TrackingTest {

    @ParameterizedTest
    @MethodSource("getAnagramData")
    public void testAnagram(final String word1, final String word2, final boolean isAnagram) {
        final Supplier<Boolean> isAnagramFunc = () -> {
            if (word1.length() != word2.length()) {
                return false;
            }

            final Map<Character, AtomicInteger> counterMap = new HashMap<>();

            // count the occurrences of letters in word 1
            for (char c : word1.toCharArray()) {
                counterMap.computeIfAbsent(c, (k) -> new AtomicInteger(0)).incrementAndGet();
            }

            // discount the occurrences of letters in word 2
            for (char c : word2.toCharArray()) {
                final AtomicInteger counter = counterMap.get(c);
                if (counter == null) {
                    return false;
                }
                counter.decrementAndGet();
            }

            // if all counters are 0 -- it means the two words are anagrams, because the chars could be rearranged
            // to form the other word
            // for the logic here -- we test that no counter is larger than 0
            return counterMap.values().stream().noneMatch((counter) -> counter.get() > 0);
        };

        assertEquals(isAnagram, isAnagramFunc.get());
    }

    private static Stream<Arguments> getAnagramData() {
        return Stream.of(
                Arguments.of("heart", "earth", true),
                Arguments.of("ram", "car", false),
                Arguments.of("short", "long", false),
                Arguments.of("creative", "reactive", true)
        );
    }

}
