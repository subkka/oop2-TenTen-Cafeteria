package com.cafeteria.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private int id;
    private String name;
    private int coupon;
    AllergyInfo allergyInfo;

    public Customer(int id, String name, int coupon, AllergyInfo allergyInfo) {
        this.id = id;
        this.name = name;
        this.coupon = coupon;
        this.allergyInfo = allergyInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public AllergyInfo getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(AllergyInfo allergyInfo) {
        this.allergyInfo = allergyInfo;
    }
}
