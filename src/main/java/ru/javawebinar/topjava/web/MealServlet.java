package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealMemoryStorage;
import ru.javawebinar.topjava.service.MealStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEAL_LIST = "/meals.jsp";
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage storage;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public MealServlet() {
        super();
        this.storage = new MealMemoryStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            forward = MEAL_LIST;
            request.setAttribute("meals", this.storage.getAllMeals());
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            this.storage.deleteMeal(mealId);
            forward = MEAL_LIST;
            request.setAttribute("meals", this.storage.getAllMeals());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            MealTo meal = this.storage.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else {
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        log.debug("forward to {}", forward);
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealTo meal = new MealTo();
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        meal.setDateTime(dateTime);

        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            this.storage.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            this.storage.updateMeal(meal);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MEAL_LIST);
        request.setAttribute("meals", this.storage.getAllMeals());
        requestDispatcher.forward(request, response);
    }
}
