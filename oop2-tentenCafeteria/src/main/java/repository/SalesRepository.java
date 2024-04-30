package repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.SalesLog;

public class SalesRepository {

    private static final String SALES_FILE_PATH = "sales.json";

    // 매출 정보를 수정하고 추가된 경우 true를 반환하고, 그렇지 않으면 false를 반환한다.
    public boolean modifySalesInfo(SalesLog salesLog) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // 매출 조회 파일 읽기
            FileReader reader = new FileReader(SALES_FILE_PATH);
            SalesInfo salesInfo = gson.fromJson(reader, SalesInfo.class);
            reader.close();

            // 매출 정보 업데이트
            salesInfo.addSalesLog(salesLog);

            // 매출 조회 파일 쓰기
            FileWriter writer = new FileWriter(SALES_FILE_PATH);
            gson.toJson(salesInfo, writer);
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 원하는 기간의 매출 정보를 읽어온다.
    public SalesInfo readSalesInfo(Date startDate, Date endDate) {
        try {
            Gson gson = new Gson();

            // 매출 조회 파일 읽기
            FileReader reader = new FileReader(SALES_FILE_PATH);
            SalesInfo salesInfo = gson.fromJson(reader, SalesInfo.class);
            reader.close();

            // 원하는 기간의 매출 정보 반환
            return salesInfo.filterSalesLog(startDate, endDate);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}