package com.cafeteria.controller;

import com.cafeteria.Admin;
import com.cafeteria.Kiosk;
import com.cafeteria.entity.AllergyInfo;
import com.cafeteria.entity.Customer;
import com.cafeteria.repository.CustomerRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Queue<Customer> customerList = new LinkedList<>(main.readCustomerInfo());

        mainClose:
        while (true) {
            System.out.print("""
                    🍀💊🍀💊🍀💊 Ten Ten Cafeteria 💊🍀💊🍀💊🍀
                    사용자를 선택하세요
                    1. 고객
                    2. 관리자
                    : """);
            int userType = sc.nextInt();
            System.out.println();

            int cnt = 0;
            // 고객 선택 (고객의 수만큼 반복)
            if (userType == 1) {
                if (!customerList.isEmpty()) {
                    welcome:
                    while (cnt < customerList.size()) {
                        System.out.println("🤩 어서 오세요~ " + Objects.requireNonNull(customerList.peek()).getName() + "님 🤩");
                        Customer customer = customerList.peek();
                        boolean endFlag = false;
                        while (true) {
                            if (endFlag) break;
                            System.out.print("""
                                    =====================================
                                    원하는 메뉴를 선택해주세요!
                                    1. 메뉴보기
                                    2. 식권개수 조회
                                    :""");
                            int chooseNum = sc.nextInt();
                            System.out.println();
                            // 메뉴보기
                            if (chooseNum == 1) {
                                main.showWeekMenu();
                                // 보유 식권개수 조회
                            } else if (chooseNum == 2) {
                                System.out.println(customer.getCoupon() + "개 보유중입니다.");
                                System.out.println("=====================================");
                                while (true) {
                                    // 식권 구매 여부
                                    System.out.print("식권을 구매하시겠습니까?(Y/N) ");
                                    char buyCouponYN = sc.next().toUpperCase().charAt(0);
                                    // 쿠폰을 구매하는 경우
                                    if (buyCouponYN == 'Y') {
                                        // 쿠폰 구매 후 고객 정보
                                        Customer customerCouponAmount = kiosk.buyCoupon(customer);
                                        customerRepository.modifyCustomerInfo(customerCouponAmount); // 고객 파일에 값 저장
                                        continue;
                                    } else if (buyCouponYN == 'N') {
                                        // 식권이 0장일때
                                        if (customer.getCoupon() == 0) {
                                            System.out.println("보유 식권은 0장입니다. 구매창으로 이동합니다");
                                            Customer customerCouponAmount = kiosk.buyCoupon(customer);
                                            customerRepository.modifyCustomerInfo(customerCouponAmount); // 고객 파일에 값 저장
                                            continue;
                                            // 식권이 1장 이상일때
                                        } else {
                                            customerList.poll();
                                            // 오늘의 메뉴와 고객정보의 알레르기 비교
                                            char eatYN;
                                            AllergyInfo sameAllergyInfo = kiosk.compareAllergy(customer.getAllergyInfo());
                                            if (!sameAllergyInfo.isEmpty()) {
                                                System.out.print("메뉴에 " + sameAllergyInfo + "가 포함됩니다\n식사하시겠습니까?(Y/N) ");
                                                eatYN = sc.next().toUpperCase().charAt(0);
                                            } else {
                                                eatYN = 'Y';
                                            }
                                            // 식사를 함
                                            if (eatYN == 'Y') {
                                                // 쿠폰 사용 후 쿠폰 개수 -1된 고객 정보
                                                Customer customerUseCoupon = kiosk.useCoupon(customer);
                                                customerRepository.modifyCustomerInfo(customerUseCoupon);
                                                System.out.println("맛있게 드세요~🍴\n");
                                                cnt++;
                                                endFlag = true;
                                                break welcome;
                                                // To-do 배식 완료 후 이동할 곳 정하기

                                                // 식사를 하지 않음
                                            } else if (eatYN == 'N') {
                                                System.out.println("다음에 또 오세요~👩‍🍳");
                                                System.out.println();
                                                cnt++;
                                                endFlag = true;
                                                break welcome;
                                                // To-do 배식 완료 후 이동할 곳 정하기
                                            } else {
                                                System.out.println("잘못 입력하셨습니다.\n");
                                            }
                                        }
                                    } else {
                                        System.out.println("잘못 입력하셨습니다.\n");
                                    }
                                }
                            }
                            // 1. 메뉴보기, 2.식권개수 조회 아닌 다른 숫자 입력했을시 오류출력
                            else {
                                System.out.println("잘못된 입력입니다.");
                            }
                        }
                    }
                } else {
                    System.out.println("대기중인 고객이 없습니다. 영업을 종료합니다");
                }
            }
            // 관리자
            else {
                System.out.print("""
                        매출 조회를 선택하세요
                        1. 원하는 기간의 매출 조회
                        2. 키오스크 종료
                        :""");

                // 원하는 기간의 매출
                try{
                    while (sc.hasNext()) {
                        int chooseNum = sc.nextInt();
                        if (chooseNum == 1) {
                            System.out.println("💰 원하는 기간의 매출을 조회합니다 💰");
                            // 매출 날짜 입력 시 잘못된 값 들어갔을 때
                            int sales = main.getSales();
                            if (sales != -1) {
                                System.out.println(sales + "원\n");
                                break;
                            }else {
                                System.out.println(main.getSales() + "원\n");
                                break;
                            }
                        } else if (chooseNum == 2) {
                            System.out.println("시스템을 종료합니다🤗");
                            break mainClose;
                        }
                    }
                }catch (Exception e) {
                    System.out.println("잘못된 입력입니다.\n");
                    sc.nextLine(); // 추측: 버퍼 안의 값을 모두 읽어서 catch문 탈출.
                }
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
            System.out.println("잘못된 입력입니다.");
//            e.printStackTrace();
            return -1;
        }

        // 마지막 일 입력
        System.out.println("조회할 기간의 마지막 일을 입력하세요. (yyyy-MM-dd)");
        String endDateString = sc.next();
        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
        } catch (ParseException e) {
            System.out.println("잘못된 입력입니다.");
            e.printStackTrace();
        }
        return admin.getSales(startDate, endDate);
    }

    // Customer json 파일에서 고객 정보 읽어오기
    public List<Customer> readCustomerInfo() {
        return customerRepository.readCustomerInfo();
    }

    // 오늘의 점심 메뉴 or 일주일 식단표 보여주기
    public void showWeekMenu() {
        System.out.print("""
                =====================================
                1. 오늘의 점심 메뉴
                2. 일주일 식단표
                :""");
        int select = sc.nextInt();
        System.out.println();

        switch (select) {
            case 1:
                kiosk.displayDailyMenu();
                break;
            case 2:
                kiosk.displayWeekMenu();
                break;
            default:
                System.out.println("잘못된 입력입니다.");
                break;
        }
    }
}

