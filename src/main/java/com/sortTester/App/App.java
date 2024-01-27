package com.sortTester.App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private final int MAX_TEST_ARRAY_LENGTH = 8192;
    private final String[] ALGORITHM_NAMES = new String[] { "Bubblesort", "Selectionsort", "Insertionsort",
            "Mergesort", "Shakersort" };
    private final String[] MODES = new String[] { "Zufall", "Worst-Case", "Best-Case", "Teilsortiert" };
    private final String[] OPERATIONS = new String[] { "Vergleichsoperationen", "Tauschoperationen" };
    private final String[] OPERATION_COUNTS = new String[] { "Vergleiche", "Tausche" };

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sortieralgorithmus-Test");

        GridPane graphContainerPane = new GridPane();
        GridPane graphContainerPane2 = new GridPane();
        FlowPane mainPane = new FlowPane(graphContainerPane, graphContainerPane2);

        for (int j = 1; j < OPERATIONS.length + 1; j++) {
            for (int i = 1; i < MODES.length + 1; i++) {
                LineChart<Number, Number> lineChart = runTest(i, j);
                lineChart.setTitle(MODES[i - 1] + " - " + OPERATIONS[j - 1]);
                switch (j) {
                    case 1:
                        graphContainerPane.add(lineChart, i, 0);
                        break;
                    case 2:
                        graphContainerPane2.add(lineChart, i, 0);
                        break;

                    default:
                        break;
                }
            }
        }

        Scene scene = new Scene(mainPane, 1500, 800);

        stage.setScene(scene);

        stage.widthProperty().addListener(
                (obs, oldVal, newVal) -> resizeCharts(mainPane, stage.getWidth(), stage.getHeight()));
        stage.heightProperty().addListener(
                (obs, oldVal, newVal) -> resizeCharts(mainPane, stage.getWidth(), stage.getHeight()));

        stage.show();
    }

    private void resizeCharts(FlowPane mainPane, double width, double height) {
        GridPane pane1 = (GridPane) mainPane.getChildren().get(0);
        GridPane pane2 = (GridPane) mainPane.getChildren().get(1);
        double chartWidth = width / (MODES.length + 0.05);
        double chartHeight = height / (OPERATIONS.length + 0.1);

        GridPane[] panes = new GridPane[] { pane1, pane2 };
        for (GridPane pane : panes) {
            pane.setPrefWidth(width);
            pane.setPrefHeight(chartHeight);
            for (int i = 0; i < pane1.getChildren().size(); i++) {
                if (pane.getChildren().get(i) instanceof LineChart) {
                    LineChart<Number, Number> lineChart = (LineChart<Number, Number>) pane.getChildren().get(i);
                    lineChart.setPrefWidth(chartWidth);
                    lineChart.setPrefHeight(chartHeight);
                }
            }
        }
    }

    public LineChart<Number, Number> runTest(int mode, int count) {
        SortTester sortTester = new SortTester();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Datensatzgröße");
        yAxis.setLabel("Zahl der Operationen");
        LineChart<Number, Number> resultsLineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 1; i < ALGORITHM_NAMES.length + 1; i++) {
            XYChart.Series<Number, Number> resultSeries = sortTester.runTest(i, mode, MAX_TEST_ARRAY_LENGTH, count);
            resultSeries.setName(ALGORITHM_NAMES[i - 1] + " - " + OPERATION_COUNTS[count - 1]);
            resultsLineChart.getData().add(resultSeries);
        }

        return resultsLineChart;
    }

    public static void main(String[] args) {
        launch();
    }

}