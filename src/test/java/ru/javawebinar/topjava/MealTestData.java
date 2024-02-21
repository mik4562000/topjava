package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int NOT_FOUND_ID = 200003;

    public static final Meal meal1 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal meal8 = new Meal(START_SEQ + 10, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 450);
    public static final Meal meal9 = new Meal(START_SEQ + 11, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Lunch", 950);
    public static final Meal meal10 = new Meal(START_SEQ + 12, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Dinner", 450);

    public static final Meal duplicatedMeal1 = new Meal(meal1.getDateTime(), "Завтрак2", 1500);

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2024, Month.FEBRUARY, 19, 10, 0), "New meal", 250);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2024, Month.FEBRUARY, 18, 14, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(10);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
