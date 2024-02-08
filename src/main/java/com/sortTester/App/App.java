package com.sortTester.App;

import com.sortTester.Tools.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import javafx.stage.Stage;
import javafx.util.Pair;

public class App extends Application implements ArrayTools, FXTools {

    private final int MAX_TEST_ARRAY_LENGTH = 8192;
    private final TestParameter[] ALGORITHMS = new TestParameter[] { TestParameter.BUBBLESORT,
            TestParameter.SELECTIONSORT,
            TestParameter.INSERTIONSORT, TestParameter.MERGESORT, TestParameter.SHAKERSORT };
    private final TestParameter[] MODES = new TestParameter[] { TestParameter.RANDOM, TestParameter.WORST_CASE,
            TestParameter.BEST_CASE, TestParameter.PARTLY_SORTED };
    private final TestParameter[] OPERATIONS = new TestParameter[] { TestParameter.COMPS, TestParameter.SWAPS };

    private LinkedList<TestParameter> algorithmList = new LinkedList<>();
    private LinkedList<TestParameter> modeList = new LinkedList<>();
    private LinkedList<TestParameter> operationList = new LinkedList<>();

    private File targetDirectory;
    private boolean writeFile = false;

    @Override
    public void start(Stage stage) {
        showInitialScene(stage);
    }

    private void showInitialScene(Stage stage) {
        stage.setTitle("Sortielalgorithmus-Test: Einstellungen");

        FlowPane mainPane = new FlowPane();
        VBox mainContainer = new VBox(5);
        HBox optionContainer = new HBox(5);

        Label titleLabel = createLabel(40, 400, "Moinsen", ContentDisplay.CENTER);

        optionContainer.getChildren().addAll(createAlgorithmSelection(), createModeSelection(),
                createAdditionalOptions(stage));
        mainContainer.getChildren().addAll(titleLabel, optionContainer);

        mainPane.getChildren().addAll(mainContainer);

        stage.setScene(new Scene(mainPane));
        stage.show();
    }

    private VBox createAlgorithmSelection() {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();
        for (TestParameter algorithm : ALGORITHMS) {
            selectionList.add(new Pair<String, Callable<Void>>(algorithm.getDescriptor(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    if (!modeList.contains(algorithm)) {
                        algorithmList.add(algorithm);
                    } else {
                        algorithmList.remove(algorithm);
                    }
                    return null;
                }
            }));
        }

        return createSelection(400, 200, 5, "Algorithmen:", selectionList);
    }

    private VBox createModeSelection() {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();
        for (TestParameter mode : MODES) {
            selectionList.add(new Pair<String, Callable<Void>>(mode.getDescriptor(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    if (!modeList.contains(mode)) {
                        modeList.add(mode);
                    } else {
                        modeList.remove(mode);
                    }
                    return null;
                }
            }));
        }

        return createSelection(400, 200, 5, "Modi:", selectionList);
    }

    private VBox createAdditionalOptions(Stage stage) {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();

        selectionList.add(new Pair<String, Callable<Void>>("Vergleiche", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (!operationList.contains(OPERATIONS[0])) {
                    operationList.add(OPERATIONS[0]);
                } else {
                    operationList.remove(OPERATIONS[0]);
                }
                return null;
            }
        }));

        selectionList.add(new Pair<String, Callable<Void>>("Tausche", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (!operationList.contains(OPERATIONS[1])) {
                    operationList.add(OPERATIONS[1]);
                } else {
                    operationList.remove(OPERATIONS[1]);
                }
                return null;
            }
        }));

        selectionList.add(new Pair<String, Callable<Void>>("Schließen", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                stage.close();
                return null;
            }
        }));

        selectionList.add(new Pair<String, Callable<Void>>("Zielordner auswählen", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                targetDirectory = showDirectoryChooser(stage);
                return null;
            }
        }));

        selectionList.add(new Pair<String, Callable<Void>>("Starten", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                showResultScene(stage);
                return null;
            }
        }));

        return createSelection(400, 200, 5, "Zusätzliche Einstellungen", selectionList);
    }

    private void showResultScene(Stage stage) {
        stage.setTitle("Sortieralgorithmus-Test: Ergebnisse");

        FlowPane mainPane = new FlowPane();

        for (TestParameter operation : operationList) {
            for (TestParameter mode : modeList) {
                mainPane.getChildren().add(createLineChart(mode, operation,
                        mode.getDescriptor() + " - " + operation.getDescriptor()));
            }
        }

        Scene scene = new Scene(mainPane, 1500, 800);

        stage.setScene(scene);

        stage.widthProperty().addListener(
                (obs, oldVal, newVal) -> resizeCharts(mainPane, stage.getWidth(), stage.getHeight(), modeList.size(),
                        operationList.size()));
        stage.heightProperty().addListener(
                (obs, oldVal, newVal) -> resizeCharts(mainPane, stage.getWidth(), stage.getHeight(), modeList.size(),
                        operationList.size()));

        resizeCharts(mainPane, stage.getWidth(), stage.getHeight(), modeList.size(),
                operationList.size());
        stage.show();
    }

    private LineChart<Number, Number> createLineChart(TestParameter mode, TestParameter count, String title) {
        final NumberAxis xAxis = createNumberAxis("Datensatzgröße");
        final NumberAxis yAxis = createNumberAxis("Operationen");

        LineChart<Number, Number> resultsLineChart = fillLineChart(xAxis, yAxis, mode, count);
        resultsLineChart.setTitle(title);

        return resultsLineChart;
    }

    private LineChart<Number, Number> fillLineChart(NumberAxis xAxis, NumberAxis yAxis, TestParameter mode,
            TestParameter count) {
        SortTester sortTester = new SortTester();
        LineChart<Number, Number> resultsLineChart = new LineChart<>(xAxis, yAxis);
        for (TestParameter algorithm : algorithmList) {
            XYChart.Series<Number, Number> resultSeries;
            String directoryPath = "";
            if (targetDirectory != null) {
                directoryPath = targetDirectory.getPath();
                writeFile = true;
            }

            resultSeries = sortTester.runTest(algorithm, mode, MAX_TEST_ARRAY_LENGTH,
                    count, directoryPath, writeFile);
            resultSeries.setName(algorithm.getDescriptor() + " - " + count.getDescriptor());
            resultsLineChart.getData().add(resultSeries);
        }
        return resultsLineChart;
    }

    public static void main(String[] args) {
        launch();
    }

}