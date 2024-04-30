package tentenCafeteria.src.main.java.repository;



import tentenCafeteria.src.main.java.entity.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuRepository {
    private List<Menu> menuList;

    public MenuRepository() {

        menuList = new ArrayList<>();
        Menu menu1 = new Menu();
        menu1.setDate(new Date());

        menuList.add(menu1);

    }

    public List<Menu> readMenuInfo(Date startDate, Date endDate) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menuList) {
            if (!menu.getDate().before(startDate) && !menu.getDate().after(endDate)) {
                result.add(menu);
            }
        }
        return result;
    }
}
