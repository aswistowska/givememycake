package org.swistowski.agata.givememycake.model;

public class Ingredient implements java.io.Serializable {

    private final double quantity;
    private final String measure;
    private final String name;


    public Ingredient(double quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

}
