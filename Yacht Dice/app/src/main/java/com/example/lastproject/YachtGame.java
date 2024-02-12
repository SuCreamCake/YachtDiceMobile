package com.example.lastproject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YachtGame {

    private final List<List<Integer>> smalls = Arrays.asList(Arrays.asList(1, 2, 3, 4), Arrays.asList(2, 3, 4, 5), Arrays.asList(3, 4, 5, 6));
    private final List<List<Integer>> larges = Arrays.asList(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(2, 3, 4, 5, 6));

    public Board getScore(Integer[] dices) {

        Map<Integer, Integer> numberCnt = new HashMap<>();
        for (Integer dice : dices) {
            numberCnt.put(dice, numberCnt.getOrDefault(dice, 0) + 1);
        }

        int[] ranks = getRankScore(dices, numberCnt);

        int[] result = new int[15];

        for (Map.Entry<Integer, Integer> entry : numberCnt.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            result[key - 1] = value * key;
        }

        result[8] = ranks[0];  // choice
        result[9] = ranks[1];  // fourCard
        result[10] = ranks[2]; // fullHouse
        result[11] = ranks[3]; // s.straight
        result[12] = ranks[4]; // l.straight
        result[13] = ranks[5]; // yacht

        // idx 6 = sum , idx 7 = bonus , idx 14 = total

        return new Board(result);
    }

    private int[] getRankScore(Integer[] dices, Map<Integer, Integer> numberCnt) {
        int[] ranks = new int[6];

        // 1. choice
        ranks[0] = Arrays.stream(dices).mapToInt(Integer::intValue).sum();

        // 2. 4 of a kind
        int kind = numberCnt.entrySet().stream()
                .filter(entry -> entry.getValue() >= 4)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(0);

        if (kind > 0) {
            int notKind = Arrays.stream(dices)
                    .filter(value -> value != kind)
                    .findFirst()
                    .orElse(kind);
            ranks[1] = (kind * 4) + notKind;
        } else {
            ranks[1] = 0;
        }

        // 3. full house
        int triple = numberCnt.entrySet().stream()
                .filter(entry -> entry.getValue() == 3)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(0);

        int twoPair = numberCnt.entrySet().stream()
                .filter(entry -> entry.getValue() == 2)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(0);

        ranks[2] = (triple > 0 && twoPair > 0) ? (triple * 3) + (twoPair * 2) : 0;

        // 4. small straight
        boolean s = Arrays.stream(dices)
                .sorted()
                .allMatch(value -> smalls.stream().anyMatch(list -> Arrays.asList(dices).containsAll(list)));

        ranks[3] = s ? 15 : 0;

        // 5. large straight
        boolean l = Arrays.stream(dices)
                .sorted()
                .allMatch(value -> larges.stream().anyMatch(list -> Arrays.asList(dices).containsAll(list)));

        ranks[4] = l ? 30 : 0;

        // 6. yacht
        boolean yacht = numberCnt.entrySet().stream()
                .anyMatch(entry -> entry.getValue() == 5);

        ranks[5] = yacht ? 50 : 0;

        return ranks;
    }

    public class Board {
        private final int[] scores;

        public Board(int[] scores) {
            this.scores = scores;
        }

        public int getOnes() {
            return scores[0];
        }

        public int getTwos() {
            return scores[1];
        }

        public int getThrees() {
            return scores[2];
        }

        public int getFours() {
            return scores[3];
        }

        public int getFives() {
            return scores[4];
        }

        public int getSixes() {
            return scores[5];
        }

        public int getChoice() {
            return scores[8];
        }

        public int getFourCard() {
            return scores[9];
        }

        public int getFullHouse() {
            return scores[10];
        }

        public int getSmallStraight() {
            return scores[11];
        }

        public int getLargeStraight() {
            return scores[12];
        }

        public int getYacht() {
            return scores[13];
        }
    }
}
