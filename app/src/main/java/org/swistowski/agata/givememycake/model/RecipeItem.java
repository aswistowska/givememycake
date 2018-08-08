package org.swistowski.agata.givememycake.model;

public class RecipeItem {

    private final String label;
    private final boolean isStep;
    private final int stepId;


    public RecipeItem(String label, boolean isStep, int stepId) {
        this.label = label;
        this.isStep = isStep;
        this.stepId = stepId;
    }


    public String getLabel() {
        return label;
    }

    public int getStepId() {
        return stepId;
    }
}
