package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping(value = "/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping("/filter")
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam String calories) {
        String dateString = dateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime_ = LocalDateTime.parse(dateString, formatter);
        super.create(new Meal(null, dateTime_, description, Integer.parseInt(calories)));
    }
}
