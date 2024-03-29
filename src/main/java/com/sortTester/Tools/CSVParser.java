package com.sortTester.Tools;

public class CSVParser {

    public String parse(ResultTable table) {

        String csv = "";

        for (int i = 0; i < table.head.length - 1; i++) {
            csv += table.head[i] + ",";
        }
        csv += table.head[table.head.length - 1];
        csv += "\n";

        for (int i = 0; i < table.results.length - 1; i++) {
            for (int j = 0; j < table.results[i].length - 1; j++) {
                csv += table.results[i][j] + ",";
            }
            csv += table.results[i][table.results[i].length - 1];
            csv += "\n";
        }
        for (int j = 0; j < table.results[table.results.length - 1].length - 1; j++) {
            csv += table.results[table.results.length - 1][j] + ",";
        }
        csv += table.results[table.results.length - 1][table.results[table.results.length - 1].length - 1];

        return csv;
    }
}
