package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Matcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

public class Utils {

    //Метод, который ожидает появления конкретного представления на экране в течение указанного интервала времени.
    public static ViewAction waitDisplayed(final int viewId, final long millis) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> to be displayed during " + millis + " millis.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                // Вызов внутреннего метода perform
                performWaitDisplayed(uiController, view, viewId, millis, getDescription());
            }
        };
    }

    //Метод, проверяющий состояние видимости элемента UI. Метод возвращает true, если представление видно на экране, иначе — false.
    public static boolean isViewVisible(View view) {
        return view.isShown(); // Check if the view is currently visible on the screen
    }

    // Внутренний метод perform
    private static void performWaitDisplayed(UiController uiController, View view, int viewId, long millis, String description) {
        // Ждем, пока основной поток не станет пустым
        uiController.loopMainThreadUntilIdle();

        // Запоминает начальное время запуска ожидания.
        final long startTime = System.currentTimeMillis();
        // Вычисляет время окончания ожидания
        final long endTime = startTime + millis;

        // Выполняем цикл, пока текущее время меньше времени окончания ожидания
        do {
            // Проходимся по всем дочерним представлениям
            for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                // Если представление имеет соответствующий ID и отображается
                if (child.getId() == viewId && isViewVisible(child)) {
                    // Возвращаемся, так как представление отображено
                    return;
                }
            }
            // Ждем 50 миллисекунд перед следующей итерацией
            uiController.loopMainThreadForAtLeast(50);
        } while (System.currentTimeMillis() < endTime);

// Если время истекло, генерируем исключение PerformException
        throw new PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(new TimeoutException())
                .build();
    }

    //Возвращает текущую дату в формате ДД.ММ.ГГГГ
    public static String currentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Возвращает вчерашнюю дату
    public static String dateInPast() {
        return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Возвращает дату ровно через один год относительно текущей даты
    public static String dateMore1Years() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Возвращает дату ровно через месяц относительно текущей даты.
    public static String dateMore1Month() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
