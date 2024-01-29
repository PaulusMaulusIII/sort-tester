package com.sortTester.Tools;

public class HTMLParser {

    public String parse(ResultTable table) {

        String html = "<!DOCTYPE html>\n<html>\n<body>\n<table>\n    <tbody>\n       <tr>\n";

        for (int i = 0; i < table.head.length - 1; i++) {
            html += "           <th>" + table.head[i] + "</th>\n";
        }
        html += "           <th>" + table.head[table.head.length - 1] + "</th>\n       </tr>\n       <tr>\n";

        for (int i = 0; i < table.results.length - 1; i++) {
            for (int j = 0; j < table.results[i].length - 1; j++) {
                html += "           <td>" + table.results[i][j] + "</td>\n";
            }
            html += "           <td>" + table.results[i][table.results[i].length - 1]
                    + "</td>\n       </tr>\n       <tr>\n";
        }
        for (int j = 0; j < table.results[table.results.length - 1].length - 1; j++) {
            html += "           <td>" + table.results[table.results.length - 1][j] + "</td>\n";
        }
        html += "           <td>"
                + table.results[table.results.length - 1][table.results[table.results.length - 1].length - 1]
                + "</td>\n       </tr>\n";

        html += "    </tbody>\n</table>\"</body>\n</html>";

        return html;
    }
}