package repository;

import entity.SalesLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesRepository {
    private List<SalesLog> salesLogList;
    private static final String JSON_FILE_PATH = "sales_data.json";

    public SalesRepository() {
        salesLogList = readSalesFromJson();
        if (salesLogList == null) {
            salesLogList = new ArrayList<>();
        }
    }

    private List<SalesLog> readSalesFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(JSON_FILE_PATH))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // JSON 문자열을 SalesLog 리스트로 변환
            return parseJsonToSalesLogList(jsonString.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<SalesLog> parseJsonToSalesLogList(String jsonString) {
        List<SalesLog> salesLogList = new ArrayList<>();
        try {
            // JSON 배열 형태로 저장된 데이터를 읽어옵니다.
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 각 객체에서 필요한 정보를 추출하여 SalesLog 객체로 변환합니다.
                Date saledate = new Date(jsonObject.getLong("date"));
                int sales = jsonObject.getInt("price");
                // SalesLog 객체를 생성하여 salesLogList에 추가합니다.
                SalesLog salesLog = new SalesLog(saledate,sales);
                salesLogList.add(salesLog);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return salesLogList;
    }

    private String writeSalesToJson(List<SalesLog> salesLogList) {
        JSONArray jsonArray = new JSONArray();
        // SalesLog 객체 리스트를 JSON 배열로 변환합니다.
        for (SalesLog salesLog : salesLogList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("date", salesLog.getSaleDate().getTime());
                jsonObject.put("price", salesLog.getSales());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // JSON 배열을 문자열로 변환하여 반환합니다.
        return jsonArray.toString();
    }

    public synchronized void addSalesLog(SalesLog salesLog) {
        salesLogList.add(salesLog);
        String jsonSalesLogList = writeSalesToJson(salesLogList);
        // 변환된 JSON 문자열을 파일에 씁니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH))) {
            writer.write(jsonSalesLogList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SalesLog> readSalesInfo(Date startDate, Date endDate) {
        List<SalesLog> result = new ArrayList<>();
        for (SalesLog salesLog : salesLogList) {
            if (!salesLog.getSaleDate().before(startDate) && !salesLog.getSaleDate().after(endDate)) {
                result.add(salesLog);
            }
        }
        return result;
    }
}