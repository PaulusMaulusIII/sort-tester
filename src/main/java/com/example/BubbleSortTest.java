package com.example;

public class BubbleSortTest extends SortTest implements ArrayFunctions {

    public BubbleSortTest(int[] array) {
        super(array);
    }

    @Override
    public long[] run() {
        bubbleTest(array);
        return super.run();
    }

    private void bubbleTest(int[] array) {
        int[] sort = copyArray(array);
        int n = sort.length;
        int speichern;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (sort[j] > sort[j + 1]) {
                    speichern = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = speichern;
                    swaps++;
                }
                comparisons++;
            }
        }
    }
}
