package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealStorage {
    void addMeal(MealTo meal);

    void deleteMeal(int mealId);

    void updateMeal(MealTo updatedMeal);

    List<MealTo> getAllMeals();

    MealTo getMealById(int mealId);
}
