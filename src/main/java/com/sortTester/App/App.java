package com.sortTester.App;

import com.sortTester.Tools.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class App extends Application implements ArrayTools {

    private final int MAX_TEST_ARRAY_LENGTH = 8192;
    private final TestParams[] ALGORITHM_NAMES = new TestParams[] { TestParams.BUBBLESORT, TestParams.SELECTIONSORT,
            TestParams.INSERTIONSORT, TestParams.MERGESORT, TestParams.SHAKERSORT };
    private final TestParams[] MODES = new TestParams[] { TestParams.RANDOM, TestParams.WORST_CASE,
            TestParams.BEST_CASE, TestParams.PARTLY_SORTED };
    private final TestParams[] OPERATIONS = new TestParams[] { TestParams.COMPARISONS, TestParams.SWAPS };

    private File targetDirectory;

    @Override
    public void start(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        targetDirectory = directoryChooser.showDialog(stage);

        stage.setTitle("Sortieralgorithmus-Test");

        GridPane graphContainerPane = new GridPane();
        GridPane graphContainerPane2 = new GridPane();
        FlowPane mainPane = new FlowPane(graphContainerPane, graphContainerPane2);

        for (TestParams operation : OPERATIONS) {
            for (TestParams mode : MODES) {
                LineChart<Number, Number> lineChart = runTest(mode, operation);
                lineChart.setTitle(mode + " - " + operation);
                switch (operation) {
                    case COMPARISONS:
                        graphContainerPane.add(lineChart, indexOf(MODES, mode), 0);
                        break;

                    case SWAPS:
                        graphContainerPane2.add(lineChart, indexOf(MODES, mode), 0);
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

    public LineChart<Number, Number> runTest(TestParams mode, TestParams count) {
        SortTester sortTester = new SortTester();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Datensatzgröße");
        yAxis.setLabel("Zahl der Operationen");
        LineChart<Number, Number> resultsLineChart = new LineChart<>(xAxis, yAxis);
        for (TestParams algorithm : ALGORITHM_NAMES) {
            XYChart.Series<Number, Number> resultSeries = sortTester.runTest(algorithm, mode, MAX_TEST_ARRAY_LENGTH,
                    count, targetDirectory.getPath());
            resultSeries.setName(algorithm + " - " + count);
            resultsLineChart.getData().add(resultSeries);
        }
        return resultsLineChart;
    }

    public static void main(String[] args) {
        launch();
    }

}