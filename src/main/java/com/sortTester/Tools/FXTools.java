package com.sortTester.Tools;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public interface FXTools {

    public default void resizeCharts(FlowPane pane, double width, double height, int horizontalChartAmount,
            int verticalChartAmount) {
        double chartWidth = width / (horizontalChartAmount + 0.05);
        double chartHeight = height / (verticalChartAmount + 0.1);

        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i) instanceof LineChart) {
                @SuppressWarnings("unchecked")
                LineChart<Number, Number> lineChart = (LineChart<Number, Number>) pane.getChildren().get(i);
                lineChart.setPrefWidth(chartWidth);
                lineChart.setPrefHeight(chartHeight);
            }
        }
    }

    public default NumberAxis createNumberAxis(String axisLabel) {
        NumberAxis axis = new NumberAxis();
        axis.setLabel(axisLabel);
        return axis;
    }

    public default File showDirectoryChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Speicherort wählen");
        return directoryChooser.showDialog(stage);
    }

    public default Label createLabel(double height, double width, String text, ContentDisplay contentDisplay) {
        Label label = new Label();

        label.setPrefHeight(height);
        label.setPrefWidth(width);
        label.setText(text);
        label.setContentDisplay(contentDisplay);

        return label;
    }

    public default Button createButton(double height, double width, String text, Callable<Void> clickHandler) {
        Button button = new Button();
        button.setPrefHeight(height);
        button.setPrefWidth(width);
        button.setText(text);
        button.setOnAction(
                (event) -> {
                    try {
                        if (button.getText().equals("[" + text + "]")) {
                            button.setText(text);
                        } else {
                            button.setText("[" + text + "]");
                        }
                        clickHandler.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return button;
    }

    public default CheckBox createCheckBox(double height, double width, String text) {
        CheckBox checkBox = new CheckBox();
        checkBox.setPrefHeight(height);
        checkBox.setPrefWidth(width);
        checkBox.setText(text);

        return checkBox;
    }

    public default VBox createSelection(double height, double width, int spacing, String labelText,
            LinkedList<Pair<String, Callable<Void>>> selectionList) {
        Button[] buttons = new Button[0];

        for (Pair<String, Callable<Void>> pair : selectionList) {
            buttons = addToButtonArray(buttons, createButton(10, 10, pair.getKey(), pair.getValue()));
        }

        return createButtonGroup(height, width, spacing, createLabel(10, 10, labelText, ContentDisplay.CENTER),
                buttons);
    }

    public default VBox createButtonGroup(double height, double width, int spacing, Label label, Button[] buttons) {
        VBox buttonGroup = new VBox(spacing);

        buttonGroup.setPrefHeight(height);
        buttonGroup.setPrefWidth(width);
        buttonGroup.getChildren().add(label);
        buttonGroup.getChildren().addAll(buttons);
        for (Node item : buttonGroup.getChildren()) {
            if (item instanceof Button) {
                ((Button) item).setPrefHeight(height / buttonGroup.getChildren().size());
                ((Button) item).setPrefWidth(width - 0.5);
            } else if (item instanceof Label) {
                ((Label) item).setPrefHeight(height / buttonGroup.getChildren().size());
                ((Label) item).setPrefWidth(width - 0.5);
            }
        }

        return buttonGroup;
    }

    private Button[] addToButtonArray(Button[] buttons, Button button) {
        Button[] newButtons = new Button[buttons.length + 1];
        for (int i = 0; i < buttons.length; i++) {
            newButtons[i] = buttons[i];
        }
        newButtons[buttons.length] = button;
        return newButtons;
    }
}
