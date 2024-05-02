package com.cafeteria.repository;

import com.cafeteria.entity.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesRepository {
    private List<SalesLog> salesLogList;
    private static final String JSON_FILE_PATH = "oop2-tentenCafeteria/src/main/resources/saleData.json";

    public SalesRepository() {

        salesLogList = new ArrayList<>();
        salesLogList = readSalesFromJson();

    }

//    private List<SalesLog> readSalesFromJson() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
//            StringBuilder jsonString = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    jsonString.append(line);
//                }
//                // JSON 문자열을 SalesLog 리스트로 변환
//                return parseJsonToSalesLogList(jsonString.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("예외");
//            return null;
//        }
//
//    }

    private List<SalesLog> parseJsonToSalesLogList(String jsonData) {
        List<SalesLog> salesLogList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray salesArray = gson.fromJson(jsonData, JsonArray.class);

        for (int i = 0; i < salesArray.size(); i++) {
            JsonObject salesObject = salesArray.get(i).getAsJsonObject();
            Date saledate = parseDateString(salesObject.get("saledate").getAsString());
            int sales = salesObject.get(String.valueOf(i)).getAsInt();
            Customer customer = parseCustomerInfo(salesObject.getAsJsonObject("customer"));
            List<Menu> menu = parseMenu(salesObject.getAsJsonArray("menu"));
            SalesLog salesLog = new SalesLog(saledate, sales, customer, (Menu) menu);
            salesLogList.add(salesLog);
        }
        return salesLogList;
    }

    private static Customer parseCustomerInfo(JsonObject customerInfoObject) {
        int id = customerInfoObject.get("id").getAsInt();
        String name = customerInfoObject.get("name").getAsString();
        return new Customer(id,name);
    }

    private static List<Menu> parseMenu(JsonArray menuArray) {
        List<Menu> menus = new ArrayList<>();
        for (int i = 0; i < menuArray.size(); i++) {
            JsonObject menuObject = menuArray.get(i).getAsJsonObject();
            List<Meal> meals = parseMeals(menuObject.getAsJsonArray("meals"));
            Menu menu = new Menu(meals);
            menus.add(menu);
        }
        return menus;
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

    private static Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private String writeSalesToJson(List<SalesLog> salesLogList) {
//        JSONArray jsonArray = new JSONArray();
//        // SalesLog 객체 리스트를 JSON 배열로 변환합니다.
//        for (SalesLog salesLog : salesLogList) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("saledate", salesLog.getSaleDate().getTime());
//                jsonObject.put("sales", salesLog.getSales());
//
//                Customer customer = salesLog.getCustomer();
//                JSONObject customerObject = new JSONObject();
//                customerObject.put("id", customer.getId());
//                customerObject.put("name", customer.getName());
//                jsonObject.put("customer", customerObject);
//
//                Menu menu = salesLog.getMenu();
//                JSONObject menuObject = new JSONObject();
//                menuObject.put("menuinfo", menu.getMeals());
//                jsonObject.put("menu", menuObject);
//                jsonArray.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        // JSON 배열을 문자열로 변환하여 반환합니다.
//        return jsonArray.toString();
//    }

    public static String writeSalesToJson(List<SalesLog> salesLogList) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(salesLogList);
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static List<SalesLog> readSalesFromJson() {
        List<SalesLog> salesLogList = new ArrayList<>();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<SalesLog>>(){}.getType();
            salesLogList = gson.fromJson(reader, listType);

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        }
        return salesLogList;
    }

    public synchronized void addSalesLog(SalesLog salesLog) {
        salesLogList.add(salesLog);
        String jsonSalesLogList = writeSalesToJson(salesLogList);
        // 변환된 JSON 문자열을 파일에 씁니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            writer.write(jsonSalesLogList);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SalesLog> readSalesInfo(Date startDate, Date endDate) {
        List<SalesLog> result = new ArrayList<>();
        long startDateTime = startDate.getTime();
        long endDateTime = endDate.getTime();

        for (SalesLog salesLog : salesLogList) {
            System.out.println(salesLog.getSaleDate().getTime()+"세일즈 로그"+"시작타임"+startDate.getTime()+ " 끝 타임"+endDate.getTime());
            long saleDateTime = salesLog.getSaleDate().getTime();
//            System.out.println("세일즈 타임" +salesLog.getSaleDate().getTime()+"시작타임"+startDate.getTime()+ " 끝 타임"+endDate.getTime());
            if (saleDateTime >= startDateTime && saleDateTime <= endDateTime) {
                System.out.println(salesLog.getSales()+"세일즈 로그2");
                result.add(salesLog);
            }
        }

        return result;
    }
}