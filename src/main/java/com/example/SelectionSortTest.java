package com.example;

public class SelectionSortTest extends SortTest implements ArrayFunctions {
    public SelectionSortTest(int[] array) {
        super(array);
    }

    @Override
    public long[] run() {
        selectionTest(array);
        return super.run();
    }

    private void selectionTest(int[] array) {
        int[] sort = copyArray(array);

        int n = sort.length;
        int speichern;
        int links = 0;
        int min;

        while (links < n) {
            min = links;

            for (int i = links + 1; i < n; i++) {
                if (sort[i] < sort[min]) {
                    min = i;
                }
                comparisons++;
            }

            speichern = sort[min];
            sort[min] = sort[links];
            sort[links] = speichern;
            swaps++;
            comparisons++;

            links = links + 1;
        }
    }
}
