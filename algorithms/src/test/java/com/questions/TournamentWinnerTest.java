package com.questions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TournamentWinnerTest {

    @ParameterizedTest
    @MethodSource("tournamentTestData")
    public void tournamentWinnerTest(String[][] competitions, int[] results, String expectedWinner) {
        if (competitions == null || results == null) {
            throw new IllegalArgumentException("Competitions or results cannot be null");
        }
        if (competitions.length != results.length) {
            throw new IllegalArgumentException("Competitions and results have different lengths");
        }
        // competitions -> [homeTeam, awayTeam]
        //   team name: at most 30 characters
        // results[i] defines the winner of competitions[i]
        //   1 means home team won, 0 means away team won
        //   team gets 3 points each win, 0 points each lost
        // guarantees:
        //   exactly one team will win the tournament
        //   each team competes against the other exactly once
        //   the tournament will always have at least two teams
        final Map<String, Integer> teamsScoreMap = new HashMap<>();
        int highestScore = 0;
        String championTeam = null;
        for (int i=0; i<competitions.length; i++) {
            final String winningTeam = competitions[i][results[i] == 1? 0 : 1];
            int currentScore = teamsScoreMap.compute(winningTeam, (k, v) -> {
                if (v == null) {
                    v = 3;
                } else {
                    v += 3;
                }
                return v;
            });
            if (currentScore > highestScore) {
                highestScore = currentScore;
                championTeam = winningTeam;
            }
        }
        assertEquals(expectedWinner, championTeam);
    }

    private static Stream<Arguments> tournamentTestData() {
        return Stream.of(
                Arguments.of(new String[][]{
                        new String[]{"HTML", "C#"},
                        new String[]{"C#", "Python"},
                        new String[]{"Python", "HTML"}
                        }, new int[]{0, 0, 1},
                        "Python"),
                Arguments.of(new String[][]{
                                new String[]{"HTML", "Java"},
                                new String[]{"Java", "Python"},
                                new String[]{"Python", "HTML"}
                        }, new int[]{0, 1, 1},
                        "Java")
        );
    }

}
