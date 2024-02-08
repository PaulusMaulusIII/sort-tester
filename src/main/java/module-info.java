module com.sortTester.App {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;

    exports com.sortTester.Algorithms;
    exports com.sortTester.App;
    exports com.sortTester.Tools;
}
