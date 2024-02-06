package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealToList = MealsUtil.filteredByStreams(
                MealsUtil.getInitMealList(),
                LocalTime.MIN, LocalTime.MAX,
                MealsUtil.CALORIES_PER_DAY);
        request.setAttribute("meals", mealToList);
        log.debug("forward to meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}