package com.cafeteria.repository;

import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.entity.Meal;
import com.cafeteria.entity.Menu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuRepository {
    private List<Menu> menuList = new ArrayList<>();
    private static final String JSON_FILE_PATH = "oop2-tentenCafeteria/src/main/resources/menuData.json";

    public MenuRepository() {
        menuList = readMenuFromJson();
    }

    private List<Menu> readMenuFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // JSON 문자열을 Menu 리스트로 변환
            return parseJsonToMenuList(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("예외");
            return null;
        }
    }

    public static List<Menu> parseJsonToMenuList(String jsonData) {
        List<Menu> menuList = new ArrayList<>();

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(jsonData, JsonArray.class);

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Date date = parseDateString(jsonObject.get("date").getAsString());
            AllergyInfo allergyInfo = parseAllergyInfo(jsonObject.getAsJsonObject("allergyInfo"));
            List<Meal> meals = parseMeals(jsonObject.getAsJsonArray("meals"));
            Menu menu = new Menu(date, meals, allergyInfo);
            menuList.add(menu);
        }

        return menuList;
    }
    private static Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static AllergyInfo parseAllergyInfo(JsonObject allergyInfoObject) {
        JsonArray ingredientsArray = allergyInfoObject.getAsJsonArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.size(); i++) {
            ingredients.add(ingredientsArray.get(i).getAsString());
        }
        return new AllergyInfo(ingredients);
    }
    private static List<Meal> parseMeals(JsonArray mealsArray) {
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < mealsArray.size(); i++) {
            JsonObject mealObject = mealsArray.get(i).getAsJsonObject();
            String foodType = mealObject.get("foodType").getAsString();
            String foodName = mealObject.get("foodName").getAsString();
            List<String> ingredients = parseIngredients(mealObject.getAsJsonArray("ingredients"));
            Meal meal = new Meal(foodType, foodName, ingredients);
            meals.add(meal);
        }
        return meals;
    }
    private static List<String> parseIngredients(JsonArray ingredientsArray) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.size(); i++) {
            ingredients.add(ingredientsArray.get(i).getAsString());
        }
        return ingredients;
    }
    private String writeMenuToJson(List<Menu> menuList) {
        JSONArray jsonArray = new JSONArray();
        // Menu 객체 리스트를 JSON 배열로 변환합니다.
        for (Menu menu : menuList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", menu.getMeals());
                jsonObject.put("date", menu.getDate().getTime());
                // AllergyInfo 객체를 JSON 형식으로 변환하여 저장합니다.
                AllergyInfo allergyInfo = menu.getAllergyInfo();
                JSONObject allergyInfoObject = new JSONObject();
                List<String> allergens = allergyInfo.getAllergens();
                JSONArray allergensArray = new JSONArray(allergens);
                allergyInfoObject.put("allergens", allergensArray);
                jsonObject.put("allergyInfo", allergyInfoObject);

                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // JSON 배열을 문자열로 변환하여 반환합니다.
        return jsonArray.toString();
    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
        String jsonMenuList = writeMenuToJson(menuList);
        // 변환된 JSON 문자열을 파일에 씁니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            writer.write(jsonMenuList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Menu> readMenuInfo(Date startDate, Date endDate) {
        List<Menu> result = new ArrayList<>();
        long startDateTime = startDate.getTime();
        long endDateTime = endDate.getTime();

        for (Menu menu : menuList) {
            long menuDateTime = menu.getDate().getTime();
            if (menuDateTime >= startDateTime && menuDateTime <= endDateTime) {
                result.add(menu);
            }
        }
        return result;
    }

}
