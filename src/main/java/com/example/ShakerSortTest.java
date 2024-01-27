package com.example;

public class ShakerSortTest extends SortTest implements ArrayFunctions {

    public ShakerSortTest(int[] array) {
        super(array);
    }

    @Override
    public long[] run() {
        shakerSort(array);
        return super.run();
    }

    public void shakerSort(int[] array) {
        int start = -1;
        int end = array.length - 2;
        boolean sorted = false;
        sortierschleife: do {
            sorted = true;
            start++;
            for (int i = start; i < end; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                    comparisons++;
                    swaps++;
                }
            }
            if (sorted) {
                break sortierschleife;
            }
            sorted = true;
            end--;
            for (int i = end; i > start; i--) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    sorted = false;
                    comparisons++;
                    swaps++;
                }
            }
        } while (!sorted);
    }
}
