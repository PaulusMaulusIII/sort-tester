package com.sortTester.Tools;

public enum TestParameter {
    BUBBLESORT("Bubblesort"),
    SELECTIONSORT("Selectionsort"),
    INSERTIONSORT("Insertionsort"),
    MERGESORT("Mergesort"),
    SHAKERSORT("Shakersort"),
    RANDOM("Zufällig"),
    WORST_CASE("Worst-case"),
    BEST_CASE("Best-case"),
    PARTLY_SORTED("Teilsortiert"),
    COMPS("Vergleiche"),
    SWAPS("Tausche");

    private String descriptor;

    TestParameter(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
