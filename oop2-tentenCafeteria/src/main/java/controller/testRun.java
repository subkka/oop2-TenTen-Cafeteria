package controller;

import entity.AllergyInfo;
import entity.Customer;
import entity.Menu;
import repository.MenuRepository;
import repository.SalesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class testRun {
    public static void main(String[] args) {
        // 가짜 데이터 생성
        Date currentDate = new Date();
        List<Menu> weeklyMenu = generateWeeklyMenu(currentDate);
        SalesRepository salesRepository = new SalesRepository();
        MenuRepository menuRepository = new MenuRepository();

        // 키오스크 객체 생성
        Kiosk kiosk = new Kiosk();

        // 가짜 고객 정보 생성
        Customer customer = new Customer("John Doe", 5); // 5장의 쿠폰을 가진 고객

        // 키오스크 기능 호출
        kiosk.displayDailyMenu();
        kiosk.displayWeekMenu();
        kiosk.displayAllergyInfo(weeklyMenu.get(0)); // 주간 메뉴 중 첫 번째 메뉴의 알러지 정보 표시

        // 쿠폰 사용 및 구매 기능 테스트
        kiosk.useCoupon(customer);
        kiosk.buyCoupon(customer);

        // 고객 알러지 정보와 주간 메뉴의 알러지 정보 비교
        AllergyInfo customerAllergy = new AllergyInfo();
        customerAllergy.addAllergen("Peanuts");
        AllergyInfo commonAllergies = kiosk.compareAllergy(customerAllergy);
        System.out.println("Customer's Allergies: " + customerAllergy.getAllergens());
        System.out.println("Common Allergies in Weekly Menu: " + commonAllergies.getAllergens());
    }

    // 임의의 주간 메뉴를 생성하는 메서드
    private static List<Menu> generateWeeklyMenu(Date currentDate) {
        List<Menu> weeklyMenu = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            // 현재 날짜부터 7일 동안의 메뉴를 생성하여 리스트에 추가
            weeklyMenu.add(new Menu("Menu " + (i + 1), new Date(currentDate.getTime() + i * 24 * 60 * 60 * 1000)));
        }
        return weeklyMenu;
    }
}
