package controller;


import java.util.Scanner;

public class MainController {

    Scanner sc = new Scanner(System.in);

    MainController main = new MainController();

    Kiosk kiosk = Kiosk.getInstance();
    Admin admin = Admin.getInstance();

        System.out.println("손님/ 관리자 중 선택하세요");
    String userType = sc.next();

    // 손님
        if(userType.equals("손님")){
        System.out.print(
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
                int buyCouponYN = sc.next().charAt(0).toUpperCase();

                if(buyCouponYN = 'Y'){
                    kiosk.buyCoupon();
                    // 식권 몇장 살지 선택
                    // buyCoupon하면 결제 되었습니다가 바로 뜨나?
                }else{
                    // 식권이 0장
                    if(식권이 0장일경우){
                        System.out.println("보유 식권은 0장입니다. 구매창으로 이동합니다");
                        kiosk.buyCoupon();

                        // 식권이 1장 이상
                    }else{
                        // 식단과 고객정보의 알레르기 비교
                        kiosk.compareAllergy(.getAllergyInfo()); // allergyinfo어디서 가져와야하지
                        System.out.println("식사메뉴에" +  .getAllergyInfo() + "가 포함됩니다\n식사하시겠습니까?(Y/N)");
                        char eatYN = sc.next().charAt(0).toUpperCase();

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
                매출조회를 선택하세요
                1. 일매출 조회
                2. 현재매출 조회
                """);

        while(sc.hasNext()){
            int Option2 = sc.nextInt();

            // 일매출
            if(option2 == 1)
                admin.SalesRepository.getTotalSales();
                // 현재매출
            else if (option2 == 2)
                admin.SalesRepository.getSales(시작날짜, 종료날짜);
            else
                System.out.println("잘못된 입력입니다.");
        }
    }
}
}
