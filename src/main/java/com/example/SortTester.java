package com.example;

import java.util.Arrays;
import java.util.LinkedList;
import javafx.scene.chart.XYChart;

public class SortTester {

    static int testArraySize;
    int[] a;

    public SortTester() {

    }

    public XYChart.Series<Number, Number> runTest(int algorithm, int mode, int maxArrayLength, int operation) {
        XYChart.Series<Number, Number> testResultSeries = new XYChart.Series<Number, Number>();
        switch (operation) {
            case 1:
                System.out.println("Vergleichsoperationen:");
                break;

            case 2:
                System.out.println("Tauschoperationen:");
                break;

            default:
                break;
        }
        for (int arrayLength = 2; arrayLength < maxArrayLength + 1; arrayLength = arrayLength * 2) {
            a = new int[arrayLength];
            switch (mode) {
                case 1:
                    a = generateRandomArray(arrayLength, arrayLength * 5);
                    break;
                case 2:
                    a = generateWorstCaseArray(arrayLength);
                    break;
                case 3:
                    a = generateBestCaseArray(arrayLength);
                    break;

                default:
                    a = generateRandomArray(arrayLength, arrayLength * 5);
                    break;
            }
            int[] results = new int[2];
            switch (algorithm) {
                case 1:
                    results = bubbleTest(a);
                    break;
                case 2:
                    results = selectionTest(a);
                    break;
                case 3:
                    results = insertionTest(a);
                    break;

                default:
                    System.err.println("Invalid Mode");
                    System.exit(1);
                    break;
            }

            sendStatus(algorithm, mode, arrayLength, results);

            int result = results[operation - 1];
            testResultSeries.getData().add(new XYChart.Data<>(arrayLength, result));
        }
        System.out.println();

        return testResultSeries;
    }

    public void sendStatus(int algorithm, int mode, int arrayLength, int[] results) {
        String message = "      ";
        switch (algorithm) {
            case 1:
                message += "Bubblesort ";
                break;
            case 2:
                message += "Selectionsort ";
                break;
            case 3:
                message += "Insertionsort ";
                break;

            default:
                break;
        }
        switch (mode) {
            case 1:
                message += "Zufälliges Array ";
                break;
            case 2:
                message += "Worst-Case ";
                break;
            case 3:
                message += "Best-Case ";
                break;

            default:
                break;
        }
        message += arrayLength + " : ";
        message += Arrays.toString(results);
        System.out.println(message);
    }

    public int[] bubbleTest(int[] array) {
        int[] sort = copyArray(array);
        int n = sort.length;
        int vergleiche = 0;
        int tauschen = 0;
        int speichern;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (sort[j] > sort[j + 1]) {
                    speichern = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = speichern;
                    tauschen++;
                }
                vergleiche++;
            }
        }
        return new int[] { vergleiche, tauschen };

    }

    public int[] selectionTest(int[] array) {
        int[] sort = copyArray(array);

        int n = sort.length;
        int speichern;
        int links = 0;
        int min;
        int vergleichen = 0;
        int tauschen = 0;

        while (links < n) {
            min = links;

            for (int i = links + 1; i < n; i++) {
                if (sort[i] < sort[min]) {
                    min = i;
                }
                vergleichen++;
            }

            speichern = sort[min];
            sort[min] = sort[links];
            sort[links] = speichern;
            tauschen++;
            vergleichen++;

            links = links + 1;
        }

        return new int[] { vergleichen, tauschen };
    }

    public int[] insertionTest(int[] array) {
        int[] sort = copyArray(array);
        int n = sort.length;
        int wert, j;
        int vergleiche = 0;
        int tauschen = 0;
        for (int i = 1; i < n; i++) {
            wert = sort[i];
            j = i;
            while (j > 0 && sort[j - 1] > wert) {
                sort[j] = sort[j - 1];
                j = j - 1;
                vergleiche++;
                tauschen++;
            }
            vergleiche++;
            sort[j] = wert;
        }
        return new int[] { vergleiche, tauschen };
    }

    public int[] generateRandomArray(int length, int max) {
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

    public int[] generateWorstCaseArray(int length) {
        int[] array = new int[length];
        int j = array.length;
        for (int i = 0; i < array.length; i++) {
            j--;
            array[i] = j;
        }
        return array;
    }

    public int[] generateBestCaseArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    public int[] copyArray(int[] array) {
        int[] copy = new int[array.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }
}
