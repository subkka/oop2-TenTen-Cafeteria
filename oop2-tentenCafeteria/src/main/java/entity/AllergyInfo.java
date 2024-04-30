package entity;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class AllergyInfo {
    List<String> allergens = new ArrayList<>();

}
