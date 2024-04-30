package tentenCafeteria.src.main.java;

import repository.MenuRepository;
import repository.SalesRepository;
import tentenCafeteria.src.main.java.entity.AllergyInfo;
import tentenCafeteria.src.main.java.entity.Customer;
import tentenCafeteria.src.main.java.entity.Menu;

import java.util.Date;
import java.util.List;
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
        Date endDate = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000); // 1 week later
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
    public Customer buyCoupon(Customer customer) {

        customer.setCoupon(customer.getCoupon() + 10);
        System.out.println("쿠폰 10장을 구매하였습니다.");
        return customer;
    }
    public AllergyInfo compareAllergy(AllergyInfo allergyInfo) {
        for (Menu menu : weeklyMenu) {
            AllergyInfo menuAllergyInfo = menu.getAllergyInfo();
            if (menuAllergyInfo != null && menuAllergyInfo.getAllergens().contains(allergyInfo)) {
                return allergyInfo;
            }
        }
        return null;
    }
}