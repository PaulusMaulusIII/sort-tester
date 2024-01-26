package com.example;

public class SortTest {
    int[] array;
    long comparisons;
    long swaps;

    public SortTest(int[] array) {
        this.array = array;
        this.comparisons = 0;
        this.swaps = 0;
    }

    public long[] run() {
        return new long[] { comparisons, swaps };
    }
}
