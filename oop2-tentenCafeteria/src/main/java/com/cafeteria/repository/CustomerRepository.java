package com.cafeteria.repository;

import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.entity.Customer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private List<Customer> customerList;
    private static final String JSON_FILE_PATH = "oop2-tentenCafeteria/src/main/resources/customerData.json";

    public CustomerRepository() {
        customerList = readCustomerFromJson();

        if (customerList == null) {
            customerList = new ArrayList<>();
        }
    }

    private List<Customer> readCustomerFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
            StringBuilder jsonString = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            // JSON 문자열을 Customer 리스트로 변환
            return parseJsonToCustomerList(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Customer> parseJsonToCustomerList(String jsonString) {
        customerList = new ArrayList<>();
        try {
            // JSON 배열 형태로 저장된 데이터를 읽어오기
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 각 객체에서 필요한 정보를 추출하여 Customer 객체로 변환
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                int coupon = jsonObject.getInt("coupon");

                // JSON 형식으로 저장된 알러지 정보를 AllergyInfo 객체로 변환
                JSONObject allergyInfoObject = jsonObject.getJSONObject("allergyInfo");
                List<String> allergens = new ArrayList<>();
                JSONArray allergensArray = allergyInfoObject.getJSONArray("allergens");

                for (int j = 0; j < allergensArray.length(); j++) {
                    allergens.add(allergensArray.getString(j));
                }

                AllergyInfo allergyInfo = new AllergyInfo();
                allergyInfo.setAllergens(allergens);

                // Customer 객체를 생성하여 customerList에 추가
                Customer customer = new Customer(id, name, coupon, allergyInfo);
                customerList.add(customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    private String writeCustomerToJson(List<Customer> customerList) {
        JSONArray jsonArray = new JSONArray();

        // Customer 객체 리스트를 JSON 배열로 변환
        for (Customer customer : customerList) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("id", customer.getId());
                jsonObject.put("name", customer.getName());
                jsonObject.put("coupon", customer.getCoupon());
                jsonObject.put("allergyInfo", customer.getAllergyInfo().getAllergens());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // JSON 배열을 문자열로 변환하여 반환
        return jsonArray.toString();
    }

    public synchronized void modifyCustomerInfo(Customer customer) {
        customerList.add(customer);
        String jsonCustomerList = writeCustomerToJson(customerList);

        // 변환된 JSON 문자열을 파일에 쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            writer.write(jsonCustomerList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> readCustomerInfo() {
        return new ArrayList<>(customerList);
    }
}
