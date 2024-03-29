package com.sortTester.App;

import com.sortTester.Tools.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.stage.Stage;
import javafx.util.Pair;

public class App extends Application implements ArrayTools, FXTools {

    private final int MAX_TEST_ARRAY_LENGTH = 8192;
    private final TestParameter[] ALGORITHMS = getParamsOfGroup(TestParameter.ALGORITHMS);
    private final TestParameter[] MODES = getParamsOfGroup(TestParameter.MODES);
    private final TestParameter[] OPERATIONS = getParamsOfGroup(TestParameter.OPERATIONS);

    private LinkedList<TestParameter> algorithmList = new LinkedList<>();
    private LinkedList<TestParameter> modeList = new LinkedList<>();
    private LinkedList<TestParameter> operationList = new LinkedList<>();

    private File targetDirectory;
    private boolean writeFile = false;
    private String parser;

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

    public class ToggleButtonHandler implements Callable<Void> {

        String currentText;
        String alternateText;
        LinkedList<TestParameter> parameterList;
        TestParameter parameter;

        public ToggleButtonHandler(LinkedList<TestParameter> parameters, TestParameter parameter) {
            this.parameter = parameter;
            this.parameterList = parameters;
        }

        public ToggleButtonHandler(LinkedList<TestParameter> parameters, TestParameter parameter,
                String alternateText) {
            this.parameter = parameter;
            this.parameterList = parameters;
            this.alternateText = alternateText;
        }

        @Override
        public Void call() throws Exception {
            if (!parameterList.contains(parameter)) {
                parameterList.add(parameter);
            } else {
                parameterList.remove(parameter);
            }
            return null;
        }

        public String getAlternateText() {
            return alternateText;
        }

        public void setAlternateText(String alternateText) {
            this.alternateText = alternateText;
        }

        public boolean hasAlternateText() {
            return alternateText != null;
        }

        public String getCurrentText() {
            return currentText;
        }

        public void setCurrentText(String text) {
            this.currentText = text;
        }

        public boolean hasCurrentText() {
            return currentText != null;
        }
    }

    private VBox createAlgorithmSelection() {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();
        for (TestParameter algorithm : ALGORITHMS) {
            selectionList.add(new Pair<String, Callable<Void>>(algorithm.getDescriptor(),
                    new ToggleButtonHandler(algorithmList, algorithm)));
        }

        return createSelection(400, 200, 5, "Algorithmen:", selectionList);
    }

    private VBox createModeSelection() {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();
        for (TestParameter mode : MODES) {
            selectionList.add(
                    new Pair<String, Callable<Void>>(mode.getDescriptor(), new ToggleButtonHandler(modeList, mode)));
        }

        return createSelection(400, 200, 5, "Modi:", selectionList);
    }

    private VBox createAdditionalOptions(Stage stage) {
        LinkedList<Pair<String, Callable<Void>>> selectionList = new LinkedList<>();

        selectionList.add(
                new Pair<String, Callable<Void>>("Vergleiche", new ToggleButtonHandler(operationList, OPERATIONS[0])));

        selectionList.add(
                new Pair<String, Callable<Void>>("Tausche", new ToggleButtonHandler(operationList, OPERATIONS[1])));

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

        selectionList.add(new Pair<String, Callable<Void>>("CSV", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                
                return null;
            }
        }));

        selectionList.add(new Pair<String, Callable<Void>>("Starten", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                if (operationList.isEmpty() || algorithmList.isEmpty() || modeList.isEmpty()) {
                    return null;
                }
                showResultScene(stage);
                return null;
            }
        }));

        return createSelection(400, 200, 5, "Zusätzliche Einstellungen", selectionList);
    }

    private void showResultScene(Stage stage) {
        stage.setTitle("Sortieralgorithmus-Test: Ergebnisse");

        FlowPane mainPane = new FlowPane() {
            {
                setAlignment(Pos.TOP_CENTER);
            }

            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                final LinkedHashMap<Double, List<Node>> rows = new LinkedHashMap<>();
                getChildren().forEach(node -> {
                    final double layoutY = node.getLayoutY();
                    List<Node> row = rows.get(node.getLayoutY());
                    if (row == null) {
                        row = new ArrayList<>();
                        rows.put(layoutY, row);
                    }

                    row.add(node);
                });

                final Object[] keys = rows.keySet().toArray();
                final List<Node> firstRow = rows.get(keys[0]);
                final List<Node> lastRow = rows.get(keys[keys.length - 1]);

                for (int i = 0; i < lastRow.size(); i++) {
                    lastRow.get(i).setLayoutX(firstRow.get(i).getLayoutX());
                }
            }
        };
        ;

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
                    count, directoryPath, writeFile, parser);
            resultSeries.setName(algorithm.getDescriptor() + " - " + count.getDescriptor());
            resultsLineChart.getData().add(resultSeries);
        }
        return resultsLineChart;
    }

    private TestParameter[] getParamsOfGroup(TestParameter group) {
        TestParameter[] foundParameters = new TestParameter[0];
        for (TestParameter parameter : TestParameter.values()) {
            try {
                if (parameter.getGroup() == group) {
                    foundParameters = addParameterToArray(foundParameters, parameter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foundParameters;
    }

    private TestParameter[] addParameterToArray(TestParameter[] array, TestParameter parameter) {
        TestParameter[] newArray = new TestParameter[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length] = parameter;
        return newArray;
    }

    public static void main(String[] args) {
        launch();
    }

}