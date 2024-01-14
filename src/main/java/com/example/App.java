package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class App extends Application {
    private final int MAX_TEST_ARRAY_LENGTH = 8192;
    private final int COMPARISON_COUNT = 1;
    private final int SWAP_COUNT = 2;
    private final String[] ALGORTIHM_NAMES = new String[] { "Bubblesort", "Selectionsort", "Insertionsort" };
    private final String[] MODES = new String[] { "Zufall", "Worst-Case", "Best-Case" };
    private final String[] OPERATIONS = new String[] { "Vergleichsoperationen", "Tauschoperationen" };

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sortieralgorithmus-Test");

        FlowPane graphContainerPane = new FlowPane();

        for (int j = 1; j < 3; j++) {
            for (int i = 1; i < 4; i++) {
                LineChart<Number, Number> lineChart = runTest(i, j);
                lineChart.setTitle(MODES[i - 1] + " - " + OPERATIONS[j - 1]);
                graphContainerPane.getChildren().add(lineChart);
            }
        }

        Scene scene = new Scene(graphContainerPane, 1500, 800);

        stage.setScene(scene);
        stage.show();
    }

    public LineChart<Number, Number> runTest(int mode, int count) {
        SortTester sortTester = new SortTester();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Datensatzgröße");
        yAxis.setLabel("Zahl der Operationen");
        LineChart<Number, Number> resultsLineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 1; i < ALGORTIHM_NAMES.length + 1; i++) {
            XYChart.Series<Number, Number> resultSeries = sortTester.runTest(i, mode, MAX_TEST_ARRAY_LENGTH, count);

            switch (count) {
                case COMPARISON_COUNT:
                    resultSeries.setName(ALGORTIHM_NAMES[i - 1] + " - Vergleiche");
                    break;

                case SWAP_COUNT:
                    resultSeries.setName(ALGORTIHM_NAMES[i - 1] + " - Tausche");
                    break;

                default:
                    break;
            }

            resultsLineChart.getData().add(resultSeries);
        }

        return resultsLineChart;
    }

    public static void main(String[] args) {
        launch();
    }

}