package com.cafeteria.entity;

import java.util.ArrayList;
import java.util.List;

public class AllergyInfo {
    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    List<String> allergens = new ArrayList<>();

}
