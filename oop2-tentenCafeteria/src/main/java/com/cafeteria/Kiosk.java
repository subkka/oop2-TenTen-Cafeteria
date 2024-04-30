

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Kiosk {
    private Date currentDate;
    private List<Menu> weeklyMenu;
    private SalesRepository salesRepository;
    private MenuRepository menuRepository;
    public Kiosk(Date currentDate, List<Menu> weeklyMenu, SalesRepository salesRepository, MenuRepository menuRepository) {
        this.currentDate = currentDate;
        this.weeklyMenu = weeklyMenu;
        this.salesRepository = salesRepository;
        this.menuRepository = menuRepository;
    }
    public void displayDailyMenu() {
        Menu dailyMenu = (Menu) menuRepository.readMenuInfo(currentDate, currentDate);
        System.out.println("오늘의 식단");
        System.out.println(dailyMenu);
    }
    public void displayWeekMenu() {

        Date endDate = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);

        weeklyMenu = menuRepository.readMenuInfo(currentDate, endDate);
        System.out.println("주간 식단");
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
    public int buyCoupon(Customer customer) {
        System.out.println("식권을 구매하시겠습니까?");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        customer.setCoupon(customer.getCoupon() + num);
        System.out.println("식권 " + num + "장을 구매하였습니다.");
        return customer.getCoupon();
    }
  

  

    public AllergyInfo compareAllergy(AllergyInfo customerAllergy) {
        AllergyInfo commonAllergies = new AllergyInfo();

        for (Menu menu : weeklyMenu) {
            AllergyInfo menuAllergyInfo = menu.getAllergyInfo();
            List<String> commonAllergens = findCommonAllergens(customerAllergy, menuAllergyInfo);

            if (!commonAllergens.isEmpty()) {
                commonAllergies.addAllergens(commonAllergens);
            }
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
