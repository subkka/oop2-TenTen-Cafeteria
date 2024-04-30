package tentenCafeteria.src.main.java.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu {
    private Date date;
    List<Meal> meals = new ArrayList<>();
    AllergyInfo allergyInfo;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public AllergyInfo getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(AllergyInfo allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
