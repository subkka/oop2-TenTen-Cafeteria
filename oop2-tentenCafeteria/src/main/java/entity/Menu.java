package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
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
