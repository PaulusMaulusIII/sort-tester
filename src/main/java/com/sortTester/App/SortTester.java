package com.sortTester.App;

import com.sortTester.Tools.ArrayFunctions;
import com.sortTester.Algorithms.BubbleSortTest;
import com.sortTester.Algorithms.InsertionSortTest;
import com.sortTester.Algorithms.MergeSortTest;
import com.sortTester.Algorithms.SelectionSortTest;
import com.sortTester.Algorithms.ShakerSortTest;

import javafx.scene.chart.XYChart;

public class SortTester implements ArrayFunctions {

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
                case 4:
                    a = generatePartlySortedArray(arrayLength);
                    break;

                default:
                    a = generateRandomArray(arrayLength, arrayLength * 5);
                    break;
            }
            long[] results = new long[2];
            switch (algorithm) {
                case 1:
                    results = new BubbleSortTest(a).run();
                    break;
                case 2:
                    results = new SelectionSortTest(a).run();
                    break;
                case 3:
                    results = new InsertionSortTest(a).run();
                    break;
                case 4:
                    results = new MergeSortTest(a).run();
                    break;
                case 5:
                    results = new ShakerSortTest(a).run();
                    break;

                default:
                    System.err.println("Invalid Mode");
                    System.exit(1);
                    break;
            }

            sendStatus(algorithm, mode, arrayLength, results);

            long result = results[operation - 1];
            testResultSeries.getData().add(new XYChart.Data<>(arrayLength, result));
        }
        System.out.println();

        return testResultSeries;
    }

    public void sendStatus(int algorithm, int mode, int arrayLength, long[] results) {
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
            case 4:
                message += "Mergesort ";
                break;
            case 5:
                message += "Shakersort ";
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
            case 4:
                message += "Teilsortiert ";
                break;

            default:
                break;
        }
        message += arrayLength + " : ";
        message += arrayToString(results);
        System.out.println(message);
    }

}
