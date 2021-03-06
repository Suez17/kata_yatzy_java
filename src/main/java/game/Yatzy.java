package game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Yatzy {

    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        return (d1 + d2 + d3 + d4 + d5);
    }

    public static int yatzy(int d1, int d2, int d3, int d4, int d5) {
        return (IntStream.of(d1, d2, d3, d4, d5).distinct().count() == 1) ? 50 : 0;
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(1, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(2, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(3, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int fours(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(4, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int fives(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(5, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int sixes(int d1, int d2, int d3, int d4, int d5) {
        return findSumOfOccurences(6, Arrays.asList(d1, d2, d3, d4, d5));
    }

    public static int pair(int d1, int d2, int d3, int d4, int d5) {
        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        return findMaxValue(findDuplicates(dices)) * 2;
    }

    public static int twoPairs(int d1, int d2, int d3, int d4, int d5) {
        int sumOfTwoPairs = 0;

        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        final Set<Integer> duplicateDices = findDuplicates(dices);

        if (duplicateDices.size() == 2) {
            sumOfTwoPairs = findSumOfAllOccurences(duplicateDices, dices,
                    2, false);
        }
        return sumOfTwoPairs;
    }

    public static int threeOfAKind(int d1, int d2, int d3, int d4, int d5) {
        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        final Set<Integer> duplicateDices = findDuplicates(dices);
        return findSumOfAllOccurences(duplicateDices, dices,
                3, false);
    }

    public static int fourOfAKind(int d1, int d2, int d3, int d4, int d5) {
        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        final Set<Integer> duplicateDices = findDuplicates(dices);
        return findSumOfAllOccurences(duplicateDices, dices,
                4, false);
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        final String smallStraight = "12345";
        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        return smallStraight.equals(createOrderedChain(dices)) ? 15 : 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        final String largeStraight = "23456";
        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        return largeStraight.equals(createOrderedChain(dices)) ? 20 : 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int sumOfFullHouse = 0;

        final List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        final Set<Integer> duplicateDices = findDuplicates(dices);

        if (duplicateDices.size() == 2) {
            sumOfFullHouse = findSumOfAllOccurences(duplicateDices, dices, 3, true);
            if (sumOfFullHouse != 0) {
                sumOfFullHouse += findSumOfAllOccurences(duplicateDices, dices, 2, true);
            }
        }
        return sumOfFullHouse;
    }

    private static int findSumOfAllOccurences(Set<Integer> targetValues,
                                              List<Integer> list, int numberOfOccurences, boolean strictComparison) {
        int sumOfAllOccurences = 0;

        for (int targetValue : targetValues) {
            final long occurencesTargetValue = countOccurences(targetValue, list);
            if ((!strictComparison && occurencesTargetValue >= numberOfOccurences) ||
                    (strictComparison && occurencesTargetValue == numberOfOccurences)) {
                sumOfAllOccurences += targetValue * numberOfOccurences;
            }
        }
        return sumOfAllOccurences;
    }

    private static int findSumOfOccurences(int targetValue, List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .filter(value -> value == targetValue)
                .sum();
    }
    
    private static long countOccurences(int targetValue, List<Integer> list) {
        return list.stream()
                .filter(value -> value == targetValue)
                .count();
    }

    private static int findMaxValue(Set<Integer> set) {
        return set.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }

    private static Set<Integer> findDuplicates(List<Integer> list) {
        final Set<Integer> set = new HashSet<>();
        return list.stream()
                .filter(value -> !set.add(value))
                .collect(Collectors.toSet());
    }

    private static String createOrderedChain(List<Integer> list) {
        return list.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .mapToObj(String::valueOf)
                .reduce("", (acc, value) -> acc += value);
    }
}
