package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Meal found = get(userId, meal.getId());
        if (found != null) {
            return repository.computeIfPresent(found.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal found = get(userId, id);
        if (found != null) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal found = repository.get(id);
        if (found != null) {
            if (found.getUserId() != userId) return null;
            return found;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilterByPredicate(repository.values(), meal -> meal.getUserId() == userId);
    }

    @Override
    public List<Meal> getFilteredMealsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        return getFilterByPredicate(repository.values(), meal -> meal.getUserId() == userId &&
                DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getFilterByPredicate(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

