package com.cafeteria.controller;

import com.cafeteria.Admin;
import com.cafeteria.Kiosk;
import com.cafeteria.entity.Customer;
import com.cafeteria.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class MainController {
    private static Scanner sc = new Scanner(System.in);
    private static Kiosk kiosk;
    private static Admin admin;
    private CustomerRepository customerRepository;

    public static void main(String[] args) {

        MainController main = new MainController();
        kiosk = new Kiosk();
        admin = new Admin();

        System.out.println("손님/ 관리자 중 선택하세요");
        String userType = sc.next();

        // 손님
        if(userType.equals("손님")){
            System.out.println(
                    """
                    1. 메뉴보기
                    2. 식권개수 조회
                    """);
            while(sc.hasNext()){
                int option1 = sc.nextInt();

                // 메뉴보기
                if(option1 == 1) {
                    main.showWeekMenu();
                    // 보유 식권개수 조회
                } else if (option1 == 2) {
                    Queue<Customer> customerList = main.readCustomerInfo();
                    System.out.println(customerList.getCoupon());
                    // customerList.poll()

                    // 식권 구매 여부
                    System.out.print("식권을 구매하시겠습니까?(Y/N)");
                    char buyCouponYN = sc.next().toUpperCase().charAt(0);

                    if(buyCouponYN == 'Y'){
                        kiosk.buyCoupon();
                        // 식권 몇장 살지 선택
                        // buyCoupon하면 결제 되었습니다가 바로 뜨나?
                    }else{
                        // 식권이 0장
                        if(식권이 0장일경우){
                            System.out.println("보유 식권은 0장입니다. 구매창으로 이동합니다");
                            kiosk.buyCoupon();

                            // 식권이 1장 이상
                        } else{
                            // 식단과 고객정보의 알레르기 비교
                            kiosk.compareAllergy(.getAllergyInfo()); // allergyinfo어디서 가져와야하지
                            System.out.println("식사메뉴에" +  .getAllergyInfo() + "가 포함됩니다\n식사하시겠습니까?(Y/N)");
                            char eatYN = sc.next().toUpperCase().charAt(0);

                            // 식사 함
                            if(eatYN == 'Y'){
                                kiosk.useCoupon(Customer);
                                System.out.println("배식 완료");
                                // 식사 안함
                            } else System.out.println("다음에 또 오세요");
                        }
                    }
                } else System.out.println("잘못된 입력입니다.");
            }
        }

        // 관리자
            else{
            System.out.print(
                    """
                    매출 조회를 선택하세요
                    1. 원하는 기간의 매출 조회
                    2. 현재 매출 조회
                    """);

            while(sc.hasNext()){
                int option2 = sc.nextInt();

                // 원하는 기간의 매출
                if(option2 == 1)
                    System.out.println(main.getSales() + "원");
                    // 현재 매출
                else if (option2 == 2)
                    System.out.println(admin.getTotalSales() + "원");
                else
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 원하는 기간의 매출 조회
    public int getSales() {
        System.out.println("조회할 기간의 시작일을 입력하세요. (yyyy-MM-dd)");
        String startDateString = sc.next();
        LocalDate startDate = LocalDate.parse(startDateString);
        System.out.println("조회할 기간의 마지막 일을 입력하세요. (yyyy-MM-dd)");
        String endDateString = sc.next();
        LocalDate endDate = LocalDate.parse(endDateString);
        return admin.getSales(startDate, endDate);
    }

    // Customer json 파일에서 고객 정보 읽어오기
    public List<Customer> readCustomerInfo() {
        return null;
    }

    // 오늘의 점심 메뉴 or 일주일 식단표 보여주기
    public void showWeekMenu() {
        System.out.println("""
                1. 오늘의 점심 메뉴
                2. 일주일 식단표
                ===================
                """);
        int select = sc.nextInt();

        switch (select) {
            case 1:
                kiosk.displayDailyMenu();
            case 2:
                kiosk.displayWeekMenu();
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }
}

