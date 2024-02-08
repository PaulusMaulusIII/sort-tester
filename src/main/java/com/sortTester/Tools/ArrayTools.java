package com.sortTester.Tools;

import java.util.Arrays;
import java.util.LinkedList;

public interface ArrayTools {

    /**
     * Appends {@code int} to the end of {@code int[]}
     * 
     * @param array {@code int[]} Array to append to
     * @param value {@code int} Value to be added
     * @return Copy of input {@code int[]} with {@code int} added to the end of it
     */
    public default int[] addIntToArray(int[] array, int value) {
        int[] modifiedArray = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            modifiedArray[i] = array[i];
        }
        modifiedArray[modifiedArray.length - 1] = value;
        return modifiedArray;
    }

    /**
     * Removes the first entry of {@code int[]}
     * 
     * @param array {@code int[]} to remove the value from
     * @return Copy of input {@code int[]} without the first value
     */
    public default int[] removeFirstItemOfArray(int[] array) {
        int[] modifiedArray = new int[array.length - 1];
        for (int i = 1; i < array.length; i++) {
            modifiedArray[i - 1] = array[i];
        }
        return modifiedArray;
    }

    /**
     * Generates random {@code int[]} using {@code Math.random()}
     * 
     * @param length {@code int}
     * @param max    {@code int} maximum value generated
     * 
     *               <pre>
     *               (int) (Math.random() * max)
     *               </pre>
     * 
     *               <b> Important: </b>
     *               When {@code strict}, {@code max} has to be at least
     *               {@code length}
     * 
     * @param strict {@code boolean}, when {@code true} allows each value only once
     * @return Randomly generated {@code int[]}
     */
    public default int[] generateRandomArray(int length, int max, boolean strict) {
        LinkedList<Integer> used = new LinkedList<Integer>();
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            int randomInt = (int) (Math.random() * max);
            if (strict) {
                while (used.contains(randomInt)) {
                    randomInt = (int) (Math.random() * max);
                }
            }
            used.add(randomInt);
            array[i] = randomInt;
        }
        return array;
    }

    /**
     * Generates worst-case {@code int[]}
     * 
     * @param length {@code int} length of the array generated
     * @return Worst-case {@code int[]}
     */
    public default int[] generateWorstCaseArray(int length) {
        int[] array = new int[length];
        int j = array.length;
        for (int i = 0; i < array.length; i++) {
            j--;
            array[i] = j;
        }
        return array;
    }

    /**
     * Generates best-case {@code int[]}
     * 
     * @param length {@code int} length of the array generated
     * @return Best-case {@code int[]}
     */
    public default int[] generateBestCaseArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    /**
     * Generates partly-sorted {@code int[]}
     * 
     * @param length {@code int} length of the array generated
     * @return Partly-sorted {@code int[]}
     */
    public default int[] generatePartlySortedArray(int length) {
        int[] array = generateRandomArray(length, length * 5, false);
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

    /**
     * Copies an {@code int[]}
     * 
     * @param array {@code int[]} to be copied
     * @return Copy of {@code int[]}
     */
    public default int[] copyArray(int[] array) {
        int[] copy = new int[array.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }

    /**
     * Converts an {@code int[]} to {@code String}
     * 
     * @param array {@code int[]} to be converted
     * @return {@code int[]} as {@code String}
     */
    public default String arrayToString(int[] array) {
        String returnString = "[";
        for (int i = 0; i < array.length - 1; i++) {
            returnString += array[i] + ", ";
        }
        returnString += array[array.length - 1] + "]";
        return returnString;
    }

    /**
     * Converts an {@code long[]} to {@code String}
     * 
     * @param array {@code long[]} to be converted
     * @return {@code long[]} as {@code String}
     */
    public default String arrayToString(long[] array) {
        String returnString = "[";
        for (int i = 0; i < array.length - 1; i++) {
            returnString += array[i] + ", ";
        }
        returnString += array[array.length - 1] + "]";
        return returnString;
    }

    /**
     * Performs a linear search to find the {@code int} index of a
     * {@code TestParams} in a {@code TestParams[]}
     * 
     * @param params {@code TestParams[]} to search
     * @param param  {@code TestParams} parameter to be found
     * @return {@code int} index of the {@code TestParams} in the
     *         {@code TestParams[]}
     */
    public default int indexOf(TestParameter[] params, TestParameter param) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == param) {
                return i;
            }
        }
        return params.length;
    }
}
