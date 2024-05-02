    package com.cafeteria.entity;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.Setter;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    @Getter
    @Setter
    public class Menu {
        private Date date;
        List<Meal> meals = new ArrayList<>();
        AllergyInfo allergyInfo;

        public Menu(Date date,List<Meal> meal, AllergyInfo allergyInfo) {
            this.date = date;
            this.meals = meal;
            this.allergyInfo = allergyInfo;
        }

        public List<Meal> getMeals() {
            return meals;
        }

        public void setMeals(List<Meal> meals) {
            this.meals = meals;
        }

        public AllergyInfo getAllergyInfo() {
            return allergyInfo;
        }

        public void setAllergyInfo(AllergyInfo allergyInfo) {
            this.allergyInfo = allergyInfo;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
//            sb.append("Date: ").append(date).append("\n");
//            sb.append("날짜: ").append(date).append("\n");
            sb.append("📅 날짜 📅\n").append(new SimpleDateFormat("yyyy-MM-dd").format(date)).append("\n");
            sb.append("🍽 메뉴 🍽\n");

//            sb.append("Meals: \n");
            for (Meal meal : meals) {
//                sb.append("  - Food Type: ").append(meal.getFoodType()).append("\n");
//                sb.append("    Food Name: ").append(meal.getFoodName()).append("\n");
                sb.append(meal.getFoodName()).append(" ");
//                sb.append("    Ingredients: ");
//                List<String> ingredients = meal.getIngredients();
//                for (int i = 0; i < ingredients.size(); i++) {
//                    sb.append(ingredients.get(i));
//                    if (i < ingredients.size() - 1) {
//                        sb.append(", ");
//                    }
//                }
//                sb.append("\n");
            }

//            sb.append("Allergy Info: ").append(allergyInfo.getAllergens());
//            sb.append(allergyInfo.getAllergens());
            sb.append("\n");
            return sb.toString();
        }
    }
