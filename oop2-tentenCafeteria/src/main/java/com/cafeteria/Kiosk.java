package com.cafeteria;

import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.entity.Customer;
import com.cafeteria.entity.Menu;
import com.cafeteria.entity.SalesLog;
import com.cafeteria.repository.CustomerRepository;
import com.cafeteria.repository.MenuRepository;
import com.cafeteria.repository.SalesRepository;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Kiosk {
    private Date currentDate;
    private List<Menu> weeklyMenu;
    private int sales;
    private final SalesRepository salesRepository;
    private final MenuRepository menuRepository;

    public Kiosk() {

        salesRepository = new SalesRepository();
        menuRepository = new MenuRepository();
    }

    public Kiosk(Date currentDate, List<Menu> weeklyMenu, SalesRepository salesRepository, MenuRepository menuRepository) {
        this.currentDate = currentDate;
        this.weeklyMenu = weeklyMenu;
        this.salesRepository = salesRepository;
        this.menuRepository = menuRepository;
    }
    public void displayDailyMenu() {
        Calendar calendar = Calendar.getInstance();

        // 시간을 00:00:00 으로 설정
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 현재 날짜까지만 포함하고 시간은 00:00:00 으로 설정된 Date 객체 얻기
        Date currentDateOnly = calendar.getTime();
        currentDate = currentDateOnly;
        Date endDate = new Date(currentDate.getTime() + 2 * 24 * 60 * 60 * 1000);

        Menu dailyMenu = null;
        List<Menu> menuList = menuRepository.readMenuInfo(currentDate, endDate);
        if (!menuList.isEmpty()) {
            dailyMenu = menuList.get(0);
        }
        System.out.println("오늘의 식단");
        System.out.println("===================");
        System.out.println(dailyMenu);
    }
    public void displayWeekMenu() {
        Calendar calendar = Calendar.getInstance();

        // 시간을 00:00:00 으로 설정
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 현재 날짜까지만 포함하고 시간은 00:00:00 으로 설정된 Date 객체 얻기
        Date currentDateOnly = calendar.getTime();
        currentDate = currentDateOnly;
        Date endDate = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);

        weeklyMenu = menuRepository.readMenuInfo(currentDate, endDate);
        System.out.println("주간 식단");
        System.out.println("===================");
        for (Menu menu : weeklyMenu) {
            System.out.println(menu);
        }
    }
    public void displayAllergyInfo(Menu menu) {
        System.out.println(menu.getMeals() + "의 알러지 요소 : ");
        System.out.println(menu.getAllergyInfo());
    }
    public Customer useCoupon(Customer customer) {
        if (customer.getCoupon() > 0) {
            customer.setCoupon(customer.getCoupon() - 1);
            System.out.println("쿠폰이 사용되었습니다.");
        } else {
            System.out.println("쿠폰이 없습니다.");
        }
        return customer;
    }
    public Customer buyCoupon(Customer customer) {
        Calendar calendar = Calendar.getInstance();

        // 시간을 00:00:00 으로 설정
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 현재 날짜까지만 포함하고 시간은 00:00:00 으로 설정된 Date 객체 얻기
        Date currentDateOnly = calendar.getTime();
        currentDate = currentDateOnly;
        Scanner sc = new Scanner(System.in);
        while (true) {
            int sales = 8000;
            System.out.println("식권을 몇장 구매하시겠습니까? (1장에서 10장까지 가능합니다)");
            int num = sc.nextInt();
            if (num >= 1 && num <= 10) {
                customer.setCoupon(customer.getCoupon() + num);
                System.out.println("식권 " + num + "장을 구매하였습니다.");
                System.out.println();

                Date endDate = new Date(currentDate.getTime() + 2 * 24 * 60 * 60 * 1000);
                Menu dailyMenu = null;
                List<Menu> menuList = menuRepository.readMenuInfo(currentDate, endDate);

                if (!menuList.isEmpty()) {
                    dailyMenu = menuList.get(0);
                }
                for(int i = 0; i< num; i++) {
                    salesRepository.addSalesLog(new SalesLog(currentDate, sales, customer, dailyMenu));
                }
                break;
            } else {
                System.out.println("1에서 10 사이의 값을 입력하세요.");
            }
        }
        return customer;
    }

    public AllergyInfo compareAllergy(AllergyInfo customerAllergy) {
        AllergyInfo commonAllergies = new AllergyInfo();
        Calendar calendar = Calendar.getInstance();

        // 시간을 00:00:00 으로 설정
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 현재 날짜까지만 포함하고 시간은 00:00:00 으로 설정된 Date 객체 얻기
        Date currentDateOnly = calendar.getTime();
        currentDate = currentDateOnly;
        Date endDate = new Date(currentDate.getTime() + 2 * 24 * 60 * 60 * 1000);

        Menu dailyMenu = null;
        List<Menu> menuList = menuRepository.readMenuInfo(currentDate, endDate);
        if (!menuList.isEmpty()) {
            dailyMenu = menuList.get(0);
        }

        if (dailyMenu != null) {
            AllergyInfo menuAllergyInfo = dailyMenu.getAllergyInfo();
            if(!menuAllergyInfo.isEmpty()) {
                for (String allergy : menuAllergyInfo.getAllergens()) {
                    if (customerAllergy.getAllergens().contains(allergy)) {
                        commonAllergies.addAllergen(allergy);
                    }
                }
            }
            else commonAllergies = null;
        }
        return commonAllergies;
    }


    private List<String> findCommonAllergens(AllergyInfo customerAllergy, AllergyInfo menuAllergy) {
        List<String> commonAllergens = new ArrayList<>();

        for (String allergen : customerAllergy.getAllergens()) {
            if (menuAllergy.getAllergens().contains(allergen)) {
                commonAllergens.add(allergen);
            }
        }
        return commonAllergens;
    }
  
}
