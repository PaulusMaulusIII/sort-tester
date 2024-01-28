package com.sortTester.App;

import com.sortTester.Tools.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sortTester.Algorithms.*;
import javafx.scene.chart.XYChart;

public class SortTester implements ArrayFunctions {

    static int testArraySize;
    int[] testArray;

    public SortTester() {

    }

    public XYChart.Series<Number, Number> runTest(int algorithm, int mode, int maxArrayLength, int operation) {
        String fileName = "";
        ResultTable testResultTable = new ResultTable(
                new String[] { "Algorithmus", "Modus", "Array-Länge", "Vergleiche", "Tausche" });
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
            testArray = new int[arrayLength];
            switch (mode) {
                case 1:
                    testArray = generateRandomArray(arrayLength, arrayLength * 5, false);
                    break;
                case 2:
                    testArray = generateWorstCaseArray(arrayLength);
                    break;
                case 3:
                    testArray = generateBestCaseArray(arrayLength);
                    break;
                case 4:
                    testArray = generatePartlySortedArray(arrayLength);
                    break;

                default:
                    testArray = generateRandomArray(arrayLength, arrayLength * 5, false);
                    break;
            }
            long[] results = new long[2];
            switch (algorithm) {
                case 1:
                    results = new BubbleSortTest(testArray).run();
                    break;
                case 2:
                    results = new SelectionSortTest(testArray).run();
                    break;
                case 3:
                    results = new InsertionSortTest(testArray).run();
                    break;
                case 4:
                    results = new MergeSortTest(testArray).run();
                    break;
                case 5:
                    results = new ShakerSortTest(testArray).run();
                    break;

                default:
                    System.err.println("Invalid Mode");
                    System.exit(1);
                    break;
            }

            fileName = sendStatus(algorithm, mode, arrayLength, results, testResultTable);

            long result = results[operation - 1];
            testResultSeries.getData().add(new XYChart.Data<>(arrayLength, result));
        }

        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            fileWriter.write(new CSVParser().parse(testResultTable));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();

        return testResultSeries;
    }

    public String sendStatus(int algorithm, int mode, int arrayLength, long[] results, ResultTable resultTable) {
        String[] resultRow = new String[5];
        String message = "      ";
        switch (algorithm) {
            case 1:
                message += "Bubblesort ";
                resultRow[0] = "Bubblesort";
                break;
            case 2:
                message += "Selectionsort ";
                resultRow[0] = "Selectionsort";
                break;
            case 3:
                message += "Insertionsort ";
                resultRow[0] = "Insertionsort";
                break;
            case 4:
                message += "Mergesort ";
                resultRow[0] = "Mergesort";
                break;
            case 5:
                message += "Shakersort ";
                resultRow[0] = "Shakersort";
                break;

            default:
                break;
        }
        switch (mode) {
            case 1:
                message += "Zufälliges Array ";
                resultRow[1] = "Zufälliges Array";
                break;
            case 2:
                message += "Worst-Case ";
                resultRow[1] = "Worst-Case";
                break;
            case 3:
                message += "Best-Case ";
                resultRow[1] = "Best-Case";
                break;
            case 4:
                message += "Teilsortiert ";
                resultRow[1] = "Teilsortiert";
                break;

            default:
                break;
        }
        message += arrayLength + " : ";
        resultRow[2] = Integer.toString(arrayLength);
        message += arrayToString(results);
        resultRow[3] = Long.toString(results[0]);
        resultRow[4] = Long.toString(results[1]);
        System.out.println(message);
        resultTable.addRow(resultRow);

        return "results/" + resultRow[0] + "-" + resultRow[1] + " Ergebnisse.csv";
    }

}
