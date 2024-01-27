package com.sortTester.Tools;

import java.util.LinkedList;

public class ResultTable {

    public String[] head;
    public String[][] results;

    public ResultTable() {

    }

    public ResultTable(String[] tableHead) {

    }

    public void setWidth(int width) {
        if (head.length == 0) {
            head = new String[width];
        } else {
            String[] temp = new String[width];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = head[i];
            }
            head = temp;
        }
        if (results.length == 0) {
            results = new String[1][width];
        } else {
            for (int i = 0; i < results.length; i++) {
                String[] temp = new String[width];
                for (int j = 0; j < temp.length; j++) {
                    temp[j] = results[i][j];
                }
                results[i] = temp;
            }
        }
    }

    public int getWidth() {
        return results[0].length;
    }

    public void addRow(String[] row) {
        if (results != null && results.length > 0) {
            String[][] temp = new String[1][0];
            temp = new String[results.length + 1][getWidth()];
            for (int i = 0; i < results.length; i++) {
                temp[i] = results[i];
            }
            temp[temp.length - 1] = row;
            results = temp;
        } else {
            results = new String[][] { row };
        }
    }

    public void setHead(String[] tableHead) {
        this.head = tableHead;
    }
}
