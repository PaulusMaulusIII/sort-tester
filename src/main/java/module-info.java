module com.sortTester.App {
    requires javafx.controls;
    requires transitive javafx.graphics;

    exports com.sortTester.Algorithms;
    exports com.sortTester.App;
    exports com.sortTester.Tools;
}
