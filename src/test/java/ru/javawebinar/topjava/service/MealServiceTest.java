package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();
        assertThat(service.get(testedId, USER_ID)).usingRecursiveComparison().isEqualTo(tested);
    }

    @Test
    public void getSomeoneElseMeal() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();
        assertThrows(NotFoundException.class, () -> service.get(testedId, ADMIN_ID));
    }

    @Test
    public void delete() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();

        service.delete(testedId, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(testedId, USER_ID));
    }

    @Test
    public void deleteSomeoneElseMeal() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();
        assertThrows(NotFoundException.class, () -> service.delete(testedId, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2020, 1, 31);
        LocalDate endDate = LocalDate.of(2020, 1, 31);
        List<Meal> filtered = service.getBetweenInclusive(startDate, endDate, USER_ID);
        assertThat(filtered).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(Arrays.asList(meal7, meal6, meal5, meal4));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertThat(all).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").isEqualTo(Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1));
    }

    @Test
    public void update() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();
        Meal updated = getUpdated();
        updated.setId(testedId);
        service.update(updated, USER_ID);
        assertThat(service.get(testedId, USER_ID)).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    public void updateSomeoneElseMeal() {
        Meal tested = service.create(getNew(), USER_ID);
        int testedId = service.create(tested, USER_ID).getId();
        Meal updated = getUpdated();
        updated.setId(testedId);
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(meal1), USER_ID));
    }
}