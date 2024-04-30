package com.cafeteria.entity;

import java.util.Date;

public class SalesLog {
    private Date saleDate;
    private int sales;

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
