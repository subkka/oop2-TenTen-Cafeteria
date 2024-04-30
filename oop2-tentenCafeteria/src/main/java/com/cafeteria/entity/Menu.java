package com.cafeteria.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu {
    private Date date;
    List<Meal> meals = new ArrayList<>();
    AllergyInfo allergyInfo;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
