package com.cafeteria.repository;

import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.entity.Menu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuRepository {
    private List<Menu> menuList;
    private static final String JSON_FILE_PATH = "menu_data.json";

    public MenuRepository() {
        menuList = readMenuFromJson();
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
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
            return null;
        }
    }

    private List<Menu> parseJsonToMenuList(String jsonString) {
        List<Menu> menuList = new ArrayList<>();
        try {
            // JSON 배열 형태로 저장된 데이터를 읽어옵니다.
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 각 객체에서 필요한 정보를 추출하여 Menu 객체로 변환합니다.
                String name = jsonObject.getString("name");
                Date date = new Date(jsonObject.getLong("date"));
                // JSON 형식으로 저장된 알러지 정보를 AllergyInfo 객체로 변환합니다.
                JSONObject allergyInfoObject = jsonObject.getJSONObject("allergyInfo");
                List<String> allergens = new ArrayList<>();
                JSONArray allergensArray = allergyInfoObject.getJSONArray("allergens");
                for (int j = 0; j < allergensArray.length(); j++) {
                    allergens.add(allergensArray.getString(j));
                }
                AllergyInfo allergyInfo = new AllergyInfo();
                allergyInfo.setAllergens(allergens);

                // Menu 객체를 생성하여 menuList에 추가합니다.
                Menu menu = new Menu(name, date, allergyInfo);
                menuList.add(menu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return menuList;
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
        for (Menu menu : menuList) {
            if (!menu.getDate().before(startDate) && !menu.getDate().after(endDate)) {
                result.add(menu);
            }
        }
        return result;
    }
}
