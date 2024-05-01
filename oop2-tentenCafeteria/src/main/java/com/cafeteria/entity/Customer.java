package com.cafeteria.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {
    private int coupon;
    AllergyInfo allergyInfo;

    public Customer(int id, String name, int coupon, AllergyInfo allergyInfo) {
    }

    public AllergyInfo getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(AllergyInfo allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }
}
