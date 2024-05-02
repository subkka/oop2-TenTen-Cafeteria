package com.cafeteria.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class AllergyInfo {
    List<String> allergens = new ArrayList<>();
    public AllergyInfo(List<String> ingredients) {
        allergens = ingredients;
    }
    public AllergyInfo(){}
    public List<String> getAllergens() {
        return allergens;
    }
    public boolean isEmpty(){
        return allergens.isEmpty();
    }
    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public void addAllergen(String allergen) {
        allergens.add(allergen);
    }
    public List<String> addAllergens(List<String> commonAllergens) {
        return allergens;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Allergens: [");
        stringBuilder.append("[");

        if (allergens != null && !allergens.isEmpty()) {
            for (int i = 0; i < allergens.size(); i++) {
                stringBuilder.append(allergens.get(i));
                if (i < allergens.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        } else {
            stringBuilder.append("None");
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
