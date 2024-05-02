package com.cafeteria.repository;

import com.cafeteria.entity.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SalesRepository {
    private List<SalesLog> salesLogList;
    private static final String JSON_FILE_PATH = "oop2-tentenCafeteria/src/main/resources/saleData.json";

    public SalesRepository() {

        salesLogList = new ArrayList<>();
        salesLogList = readSalesFromJson();

    }
    private List<SalesLog> readSalesFromJson() {
        List<SalesLog> salesLogs = new ArrayList<>();
        try {
            FileReader reader = new FileReader(JSON_FILE_PATH);
            Gson gson = new Gson();
            Type salesLogListType = new TypeToken<ArrayList<SalesLog>>(){}.getType();
            salesLogs = gson.fromJson(reader, salesLogListType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salesLogs;
    }

    public void writeSalesToJson(List<SalesLog> salesLogs) {
        try {
            // 기존 데이터 읽기
            List<SalesLog> currentSalesLogs = new ArrayList<>();
            currentSalesLogs = readSalesFromJson();
            if(currentSalesLogs!=null)// 기존 데이터와 새로운 데이터 합치기
                currentSalesLogs.addAll(salesLogs);
            else currentSalesLogs = salesLogs;
            // 파일에 쓰기
            FileWriter writer = new FileWriter(JSON_FILE_PATH);
            Gson gson = new Gson();
            gson.toJson(currentSalesLogs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addSalesLog(SalesLog salesLog) {
        List<SalesLog> salesLogs = new ArrayList<>();
        salesLogs.add(salesLog);
        writeSalesToJson(salesLogs);
    }

    public List<SalesLog> readSalesInfo(Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        System.out.println("Sales information between " + dateFormat.format(startDate) +
//                " and " + dateFormat.format(endDate) + ":");

        List<SalesLog> salesLogs = new ArrayList<>();
        for (SalesLog sale : salesLogList) {
            if (sale.getSaleDate().after(startDate) && sale.getSaleDate().before(endDate)) {
                salesLogs.add(sale);
            }
        }
        return salesLogs;
    }
}

