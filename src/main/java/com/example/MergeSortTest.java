package com.example;

public class MergeSortTest extends SortTest implements ArrayFunctions {
    public MergeSortTest(int[] array) {
        super(array);
    }

    @Override
    public long[] run() {
        mergeTest(array);
        return super.run();
    }

    private void mergeTest(int[] array) {
        mergesort(copyArray(array));
    }

    private int[] mergesort(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        int[] leftArray;
        int[] rightArray;
        leftArray = new int[0];
        rightArray = new int[0];
        for (int i = 0; i < array.length; i++) {
            if (i < array.length / 2) {
                leftArray = addIntToArray(leftArray, array[i]);
            } else {
                rightArray = addIntToArray(rightArray, array[i]);
            }
        }
        leftArray = mergesort(leftArray);
        rightArray = mergesort(rightArray);
        return merge(leftArray, rightArray);
    }

    public int[] merge(int[] leftArray, int[] rightArray) {
        int[] mergedArray = new int[0];
        while ((leftArray.length != 0) && (rightArray.length != 0)) {
            if (leftArray[0] <= rightArray[0]) {
                mergedArray = addIntToArray(mergedArray, leftArray[0]);
                leftArray = removeFirstItemOfArray(leftArray);
            } else {
                mergedArray = addIntToArray(mergedArray, rightArray[0]);
                rightArray = removeFirstItemOfArray(rightArray);
            }
            comparisons++;
            swaps++;
        }
        while (leftArray.length != 0) {
            mergedArray = addIntToArray(mergedArray, leftArray[0]);
            leftArray = removeFirstItemOfArray(leftArray);
            swaps++;
        }
        while (rightArray.length != 0) {
            mergedArray = addIntToArray(mergedArray, rightArray[0]);
            rightArray = removeFirstItemOfArray(rightArray);
            swaps++;
        }
        return mergedArray;
    }
}
