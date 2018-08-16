package org.swistowski.agata.givememycake.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements java.io.Serializable {

    @SerializedName("quantity")
    private Double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String name;


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


    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setName(String name) {
        this.name = name;
    }

}
