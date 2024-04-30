package entity;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class AllergyInfo {
    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    List<String> allergens = new ArrayList<>();

    public void addAllergens(List<String> commonAllergens) {
    }
}
