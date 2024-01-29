package com.sortTester.App;

import com.sortTester.Tools.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sortTester.Algorithms.*;
import javafx.scene.chart.XYChart;

public class SortTester implements ArrayTools {

    static int testArraySize;
    int[] testArray;

    public SortTester() {

    }

    public XYChart.Series<Number, Number> runTest(TestParams algorithm, TestParams mode, int maxArrayLength,
            TestParams operation, String targetDirectoryPath) {
        String fileName = "";
        ResultTable testResultTable = new ResultTable(
                new String[] { "Algorithmus", "Modus", "Array-Länge", "Vergleiche", "Tausche" });
        XYChart.Series<Number, Number> testResultSeries = new XYChart.Series<Number, Number>();
        System.out.println(operation);
        for (int arrayLength = 2; arrayLength < maxArrayLength + 1; arrayLength = arrayLength * 2) {
            testArray = new int[arrayLength];
            switch (mode) {
                case RANDOM:
                    testArray = generateRandomArray(arrayLength, arrayLength * 5, false);
                    break;
                case WORST_CASE:
                    testArray = generateWorstCaseArray(arrayLength);
                    break;
                case BEST_CASE:
                    testArray = generateBestCaseArray(arrayLength);
                    break;
                case PARTLY_SORTED:
                    testArray = generatePartlySortedArray(arrayLength);
                    break;

                default:
                    testArray = generateRandomArray(arrayLength, arrayLength * 5, false);
                    break;
            }
            long[] results = new long[2];
            switch (algorithm) {
                case BUBBLESORT:
                    results = new BubbleSortTest(testArray).run();
                    break;
                case SELECTIONSORT:
                    results = new SelectionSortTest(testArray).run();
                    break;
                case INSERTIONSORT:
                    results = new InsertionSortTest(testArray).run();
                    break;
                case MERGESORT:
                    results = new MergeSortTest(testArray).run();
                    break;
                case SHAKERSORT:
                    results = new ShakerSortTest(testArray).run();
                    break;

                default:
                    System.err.println("Invalid Mode");
                    System.exit(1);
                    break;
            }

            fileName = sendStatus(algorithm, mode, arrayLength, results, testResultTable);

            long result = 0;
            switch (operation) {
                case COMPARISONS:
                    result = results[0];
                    break;
                case SWAPS:
                    result = results[1];
                    break;

                default:
                    break;
            }

            testResultSeries.getData().add(new XYChart.Data<>(arrayLength, result));
        }

        try {
            FileWriter fileWriter = new FileWriter(new File(targetDirectoryPath + "/" + fileName));
            fileWriter.write(new HTMLParser().parse(testResultTable));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();

        return testResultSeries;
    }

    public String sendStatus(TestParams algorithm, TestParams mode, int arrayLength, long[] results,
            ResultTable resultTable) {
        String[] resultRow = new String[5];
        String message = "      ";
        message += algorithm;
        resultRow[0] = algorithm.toString();
        message += mode;
        resultRow[1] = mode.toString();
        message += "_" + arrayLength + " : ";
        resultRow[2] = Integer.toString(arrayLength);
        message += arrayToString(results);
        resultRow[3] = Long.toString(results[0]);
        resultRow[4] = Long.toString(results[1]);
        System.out.println(message);
        resultTable.addRow(resultRow);

        return message.split(":")[0].strip() + "_RESULTS.html";
    }

}
