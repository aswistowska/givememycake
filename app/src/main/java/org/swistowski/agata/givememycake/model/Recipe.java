package org.swistowski.agata.givememycake.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements java.io.Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;


    private final List<Step> steps;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Step getStepById(int stepId) {
        for(int i = 0; i < getSteps().size(); i++) {
            Step currentStep = getSteps().get(i);
            if(currentStep.getId() == stepId) {
                return currentStep;
            }
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
