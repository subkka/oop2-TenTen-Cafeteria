package com.cafeteria.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String foodType;
    private String foodName;
    List<String> ingredients = new ArrayList<>();
    public Meal(String foodType, String foodName, List<String> ingredients) {
        this.foodType = foodType;
        this.foodName = foodName;
        this.ingredients = ingredients;
    }
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public List<String> getIngredients(){
        return ingredients;
    }
}
