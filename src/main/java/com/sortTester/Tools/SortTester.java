package com.sortTester.Tools;

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

    public XYChart.Series<Number, Number> runTest(TestParameter algorithm, TestParameter mode, int maxArrayLength,
            TestParameter operation, String targetDirectoryPath, boolean writeFile, String parser) {
        String fileName = "";
        ResultTable testResultTable = new ResultTable(
                new String[] { "Algorithmus", "Modus", "Array-Länge", "Vergleiche", "Tausche" });
        XYChart.Series<Number, Number> testResultSeries = new XYChart.Series<Number, Number>();
        System.out.println(operation.getDescriptor());
        for (int arrayLength = 2; arrayLength < maxArrayLength + 1; arrayLength = arrayLength * 2) {
            testArray = generateArray(mode, arrayLength);
            long[] results = getResults(algorithm);
            fileName = getFileName(algorithm, mode, arrayLength, results, testResultTable);

            long result = 0;
            switch (operation) {
                case COMPS:
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

        if (writeFile) {
            try {
                FileWriter fileWriter = new FileWriter(new File(targetDirectoryPath + "/" + fileName));
                System.out.println("Saved results as: " + targetDirectoryPath + "/" + fileName);
                switch (parser) {
                    case "HTML":
                        fileWriter.write(new HTMLParser().parse(testResultTable));
                        break;

                    case "CSV":
                        fileWriter.write(new CSVParser().parse(testResultTable));
                        break;

                    default:
                        System.err.println("Invalid Parser");
                        break;
                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();

        return testResultSeries;
    }

    private long[] getResults(TestParameter algorithm) {
        long[] testResults = new long[2];
        switch (algorithm) {
            case BUBBLESORT:
                testResults = new BubbleSortTest(testArray).run();
                break;
            case SELECTIONSORT:
                testResults = new SelectionSortTest(testArray).run();
                break;
            case INSERTIONSORT:
                testResults = new InsertionSortTest(testArray).run();
                break;
            case MERGESORT:
                testResults = new MergeSortTest(testArray).run();
                break;
            case SHAKERSORT:
                testResults = new ShakerSortTest(testArray).run();
                break;

            default:
                System.err.println("Invalid Mode");
                System.exit(1);
                break;
        }
        return testResults;
    }

    private int[] generateArray(TestParameter mode, int arrayLength) {
        int[] array = new int[arrayLength];
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
        return array;
    }

    public String getFileName(TestParameter algorithm, TestParameter mode, int arrayLength, long[] results,
            ResultTable resultTable) {
        String[] resultRow = new String[5];
        String message = "      ";
        message += algorithm;
        resultRow[0] = algorithm.toString();
        message += "_" + mode;
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
