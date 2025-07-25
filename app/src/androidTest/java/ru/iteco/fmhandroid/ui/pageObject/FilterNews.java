package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.Utils.waitDisplayed;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.Utils;

public class FilterNews {
    private final ViewInteraction dataFrom = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    private final ViewInteraction dataTo = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    private final ViewInteraction buttonFilter = onView(withId(R.id.filter_button));
    private final int filter = R.id.filter_button;

    private final int message = android.R.id.message;

    public int getFilter() {
        return filter;
    }


    @Step("Установка начальной даты фильтра")
    public void setDateFromFilter(String dateFrom) {
        Allure.step("Установка начальной даты");
        dataFrom.check(matches(isDisplayed()));
        dataFrom.perform(replaceText(dateFrom), closeSoftKeyboard());
    }

    @Step("Установка конечной даты фильтра")
    public void setDateToFilter(String dateTo) {
        Allure.step("Установка конечной даты");
        dataTo.check(matches(isDisplayed()));
        dataTo.perform(replaceText(dateTo), closeSoftKeyboard());
    }

    @Step("Подтверждение фильтрации")
    public void confirmFilter() {
        Allure.step("Нажать на кнопку 'Фильтровать");
        onView(isRoot()).perform(waitDisplayed(filter, 10000));
        buttonFilter.check(matches(isDisplayed()));
        buttonFilter.perform(click());
    }

    @Step("Проверка отображения ошибки 'Неверно указан период дат'")
    public void checkErrorFilterNews(String text) {
        Allure.step("Проверка отображения ошибки");
        onView(isRoot()).perform(Utils.waitDisplayed(message, 5000));
        onView(allOf(withId(message), withText(text), isDisplayed()))
                .check(matches(withText(text)));
    }
}