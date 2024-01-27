package com.sortTester.Tools;

import java.util.Arrays;
import java.util.LinkedList;

public interface ArrayFunctions {

    public default int[] addIntToArray(int[] array, int value) {
        int[] modifiedArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            modifiedArray[i] = array[i];
        }
        modifiedArray[modifiedArray.length - 1] = value;
        return modifiedArray;
    }

    public default int[] removeFirstItemOfArray(int[] array) {
        int[] modifiedArray = new int[array.length - 1];
        for (int i = 1; i < array.length; i++) {
            modifiedArray[i - 1] = array[i];
        }
        return modifiedArray;
    }

    public default int[] generateRandomArray(int length, int max) {
        LinkedList<Integer> used = new LinkedList<Integer>();
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            int randomInt = (int) (Math.random() * max);
            while (used.contains(randomInt)) {
                randomInt = (int) (Math.random() * max);
            }
            used.add(randomInt);
            array[i] = randomInt;
        }
        return array;
    }

    public default int[] generateWorstCaseArray(int length) {
        int[] array = new int[length];
        int j = array.length;
        for (int i = 0; i < array.length; i++) {
            j--;
            array[i] = j;
        }
        return array;
    }

    public default int[] generateBestCaseArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    public default int[] generatePartlySortedArray(int length) {
        int[] array = generateRandomArray(length, length * 5);
        int arrayFilled = 0;
        while (arrayFilled < array.length) {
            int right = (int) (Math.random() * (array.length / 5) + 1);
            int[] subArray = new int[0];
            if ((arrayFilled + right) > array.length) {
                for (int i = arrayFilled; i < array.length; i++) {
                    subArray = addIntToArray(subArray, array[i]);
                }
                Arrays.sort(subArray);
                for (int i = arrayFilled; i < array.length; i++) {
                    array[i] = subArray[i - arrayFilled];
                }
            } else {
                for (int i = arrayFilled; i < right; i++) {
                    subArray = addIntToArray(subArray, array[i]);
                }
                Arrays.sort(subArray);
                for (int i = arrayFilled; i < right; i++) {
                    array[i] = subArray[i - arrayFilled];
                }
            }
            arrayFilled += right;
        }

        return array;
    }

    public default int[] copyArray(int[] array) {
        int[] copy = new int[array.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }

    public default String arrayToString(int[] array) {
        String returnString = "[";
        for (int i = 0; i < array.length - 1; i++) {
            returnString += array[i] + ", ";
        }
        returnString += array[array.length - 1] + "]";
        return returnString;
    }

    public default String arrayToString(long[] array) {
        String returnString = "[";
        for (int i = 0; i < array.length - 1; i++) {
            returnString += array[i] + ", ";
        }
        returnString += array[array.length - 1] + "]";
        return returnString;
    }
}
