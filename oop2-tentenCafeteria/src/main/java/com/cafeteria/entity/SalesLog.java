package com.cafeteria.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class SalesLog {
    private Date saleDate;
    private int sales;
    private Customer customer;
    private Menu menu;

    public Customer getCustomer() {
        return customer;
    }

    public Menu getMenu() {
        return menu;
    }



    public SalesLog(Date saleDate, int sales, Customer customer, Menu menu) {
        this.saleDate = saleDate;
        this.sales = sales;
        this.customer = customer;
        this.menu = menu;
    }

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
