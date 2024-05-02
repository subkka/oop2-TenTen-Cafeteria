package com.cafeteria;

import com.cafeteria.entity.SalesLog;
import com.cafeteria.repository.SalesRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Admin {

    private String admin_id;
    private SalesRepository salesRepository;

    public Admin() {
        salesRepository = new SalesRepository();
    }

    // 총 매출 정보 가져오기
    public int getTotalSales(Date startDate, Date endDate) {
        int sum = 0;
        List<SalesLog> sales = salesRepository.readSalesInfo(startDate, endDate);
        for (SalesLog salesLog : sales) {
            sum += salesLog.getSales();
        }
        return sum;
    }

    // 원하는 기간의 매출 정보 가져오기
    public int getSales(Date startDate, Date endDate) {
        int sum = 0;
        List<SalesLog> sales = salesRepository.readSalesInfo(startDate, endDate);
        for (SalesLog salesLog : sales) {
            sum += salesLog.getSales();
        }
        return sum;
    }
}
