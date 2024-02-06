package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryStorage implements MealStorage {
    private static AtomicInteger counter = new AtomicInteger(0);
    private List<Meal> mealList;

    public MealMemoryStorage() {
        this.mealList = MealsUtil.getInitMealList();
        this.mealList.forEach((meal -> meal.setId(counter.incrementAndGet())));
    }

    public void addMeal(MealTo meal) {
        meal.setId(counter.incrementAndGet());
        this.mealList.add(new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    public void deleteMeal(int mealId) {
        Meal deleted = null;
        for (Meal meal : mealList) {
            if (meal.getId() == mealId) {
                deleted = meal;
                break;
            }
        }
        mealList.remove(deleted);
    }

    public void updateMeal(MealTo updatedMeal) {
        for (Meal meal : mealList) {
            if (meal.getId() == updatedMeal.getId()) {
                meal.setDateTime(updatedMeal.getDateTime());
                meal.setDescription(updatedMeal.getDescription());
                meal.setCalories(updatedMeal.getCalories());
                break;
            }
        }
    }

    public List<MealTo> getAllMeals() {
        return MealsUtil.filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }

    public MealTo getMealById(int mealId){
        for (Meal meal: mealList) {
            if (meal.getId() == mealId) {
                return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId());
            }
        }
        return null;
    }
}
