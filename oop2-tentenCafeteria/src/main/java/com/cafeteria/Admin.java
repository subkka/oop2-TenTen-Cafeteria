package com.cafeteria;

import com.cafeteria.repository.SalesRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class Admin {

    private String admin_id;
    private SalesRepository salesRepository;

    public Admin() {
        salesRepository = new SalesRepository();
    }

    // 총 매출 정보 가져오기
    public int getTotalSales() {
        return 0;
    }

    // 원하는 기간의 매출 정보 가져오기
    public int getSales(Date startDate, Date endDate) {
        salesRepository.readSalesInfo(startDate, endDate);
        return 0;
    }
}
