package com.cafeteria.controller;

import com.cafeteria.Admin;
import com.cafeteria.Kiosk;
import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.repository.CustomerRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class MainController {
    private static Scanner sc = new Scanner(System.in);
    private static Kiosk kiosk;
    private static Admin admin;
    private static CustomerRepository customerRepository;

    public static void main(String[] args) {
        MainController main = new MainController();
        kiosk = new Kiosk();
        admin = new Admin();
        customerRepository = new CustomerRepository();
        Queue<Customer> customerList = null;
        String allergyInfo = null;

        System.out.print("> 사용자를 선택하세요 1. 고객 / 2. 관리자: ");
        int userType = sc.nextInt();

        int cnt = 0;
        // 고객 선택 (고객의 수만큼 반복)
        while( cnt < customerList.size() ){
            if(userType == 1){
                System.out.print(
                        """
                        1. 메뉴보기
                        2. 식권개수 조회
                        """);
                while(sc.hasNext()){
                    int chooseNum = sc.nextInt();
                    // 메뉴보기
                    if(chooseNum == 1) {
                        main.showWeekMenu();
                        break;
                        // 보유 식권개수 조회
                    } else if (chooseNum == 2) {
                        while(true) {
                            Customer customer = customerList.peek();
                            System.out.println(customer.getCoupon() + "개 보유중입니다.");
                            // 식권 구매 여부
                            System.out.print("식권을 구매하시겠습니까?(Y/N) ");
                            char buyCouponYN = sc.next().toUpperCase().charAt(0);
                            // 쿠폰을 구매하는 경우
                            if(buyCouponYN == 'Y'){
                                // 쿠폰 구매 후 고객 정보
                                Customer customerCouponAmount = kiosk.buyCoupon(customer);
                                customerRepository.modifyCustomerInfo(customerCouponAmount); // 고객 파일에 값 저장
                                continue;
                            }else{
                                // 식권이 0장일때
                                if(customer.getCoupon() == 0){
                                    System.out.println("보유 식권은 0장입니다. 구매창으로 이동합니다");
                                    Customer customerCouponAmount = kiosk.buyCoupon(customer);
                                    customerRepository.modifyCustomerInfo(customerCouponAmount); // 고객 파일에 값 저장
                                    continue;
                                    // 식권이 1장 이상일때
                                }else{
                                    // 오늘의 메뉴와 고객정보의 알레르기 비교
                                    AllergyInfo sameAllergyInfo = kiosk.compareAllergy(customer.getAllergyInfo());
                                    System.out.print("식사메뉴에" + sameAllergyInfo + "가 포함됩니다\n식사하시겠습니까?(Y/N) ");
                                    char eatYN = sc.next().toUpperCase().charAt(0);
                                    // 식사를 함
                                    if(eatYN == 'Y'){
                                        // 쿠폰 사용 후 쿠폰 개수 -1된 고객 정보
                                        Customer customerUseCoupon = kiosk.useCoupon(customer);
                                        customerRepository.modifyCustomerInfo(customerUseCoupon);
                                        System.out.println("배식 완료");
                                        cnt++;
                                        break;
                                        // To-do 배식 완료 후 이동할 곳 정하기

                                        // 식사를 하지 않음
                                    } else{
                                        System.out.println("다음에 또 오세요");
                                        cnt++;
                                        break;
                                        // To-do 배식 완료 후 이동할 곳 정하기
                                    }
                                }
                            }
                        }
                    }
                    // 1. 메뉴보기, 2.식권개수 조회 아닌 다른 숫자 입력했을시 오류출력
                    else
                        System.out.println("잘못된 입력입니다.");
                    break;
                }
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
                int chooseNum = sc.nextInt();

                // 원하는 기간의 매출
                if(chooseNum == 1)
                    System.out.println(main.getSales() + "원");
                // 현재 매출
                else if (chooseNum == 2)
                    System.out.println(admin.getTotalSales() + "원");
                else
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 원하는 기간의 매출 조회
    public int getSales() {
        // 시작일 입력
        System.out.println("조회할 기간의 시작일을 입력하세요. (yyyy-MM-dd)");
        String startDateString = sc.next();
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
        } catch (ParseException e) {
            System.out.println("잘못 입력하셨습니다.");
            e.printStackTrace();
        }

        // 마지막 일 입력
        System.out.println("조회할 기간의 마지막 일을 입력하세요. (yyyy-MM-dd)");
        String endDateString = sc.next();
        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
        } catch (ParseException e) {
            System.out.println("잘못 입력하셨습니다.");
            e.printStackTrace();
        }

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
