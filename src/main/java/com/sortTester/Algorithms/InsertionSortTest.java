package com.sortTester.Algorithms;

import com.sortTester.Tools.ArrayFunctions;

public class InsertionSortTest extends SortTest implements ArrayFunctions {
    public InsertionSortTest(int[] array) {
        super(array);
    }

    @Override
    public long[] run() {
        insertionTest(array);
        return super.run();
    }

    private void insertionTest(int[] array) {
        int[] sort = copyArray(array);
        int n = sort.length;
        int wert, j;
        for (int i = 1; i < n; i++) {
            wert = sort[i];
            j = i;
            while (j > 0 && sort[j - 1] > wert) {
                sort[j] = sort[j - 1];
                j = j - 1;
                comparisons++;
                swaps++;
            }
            comparisons++;
            sort[j] = wert;
        }
    }
}
