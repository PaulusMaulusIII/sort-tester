package com.sortTester.Tools;

public enum TestParameter {
    ALGORITHMS,
    MODES,
    OPERATIONS,

    BUBBLESORT("Bubblesort", ALGORITHMS),
    SELECTIONSORT("Selectionsort", ALGORITHMS),
    INSERTIONSORT("Insertionsort", ALGORITHMS),
    MERGESORT("Mergesort", ALGORITHMS),
    SHAKERSORT("Shakersort", ALGORITHMS),

    RANDOM("Zufällig", MODES),
    WORST_CASE("Worst-case", MODES),
    BEST_CASE("Best-case", MODES),
    PARTLY_SORTED("Teilsortiert", MODES),

    COMPS("Vergleiche", OPERATIONS),
    SWAPS("Tausche", OPERATIONS);

    private String descriptor;
    private TestParameter group;

    private TestParameter() {
        
    }

    private TestParameter(String descriptor, TestParameter group) {
        this.descriptor = descriptor;
        this.group = group;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public TestParameter getGroup() {
        return this.group;
    }

    public TestParameter[] getParamsOfGroup(TestParameter group) {
        TestParameter[] foundParameters = new TestParameter[0];
        for (TestParameter parameter : TestParameter.values()) {
            try {
                if (parameter.getGroup() == group) {
                    addParameterToArray(foundParameters, parameter);
                }
            } catch (Exception e) {
                // TODO: handle exception
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
}
