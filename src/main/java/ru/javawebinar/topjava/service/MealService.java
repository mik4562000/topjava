package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public void delete(int userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public List<MealTo> getAll(int userId, int authUserCaloriesPerDay) {
        return MealsUtil.getTos(repository.getAll(userId), authUserCaloriesPerDay);
    }

    public List<MealTo> getFilteredMealsByDateRange(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int authUserCaloriesPerDay) {
        LocalDate startDateNotNull = startDate == null ? LocalDate.MIN : startDate;
        LocalDate endDateNotNull = endDate == null ? LocalDate.MAX : endDate;
        LocalTime startTimeNotNull = startTime == null ? LocalTime.MIN : startTime;
        LocalTime endTimeNotNull = endTime == null ? LocalTime.MAX : endTime;
        return MealsUtil.getFilteredTos(repository.getFilteredMealsByDateRange(userId, startDateNotNull, endDateNotNull), authUserCaloriesPerDay, startTimeNotNull, endTimeNotNull);
    }
}