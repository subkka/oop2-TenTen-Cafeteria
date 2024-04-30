package com.cafeteria.entity;

import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD:oop2-tentenCafeteria/src/main/java/com/cafeteria/entity/SalesLog.java
=======
import lombok.Getter;
import lombok.Setter;

>>>>>>> 95d6ecc4cc903e73e0e4b43eea79a2fd4fbbefc2:oop2-tentenCafeteria/src/main/java/entity/SalesLog.java
import java.util.Date;
@Getter
@Setter
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
