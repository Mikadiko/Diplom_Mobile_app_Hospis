package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.Utils;

public class NewsPage {
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    FilterNews filterNewsPage = new FilterNews();
    private final int buttonControlPanelNews = R.id.edit_news_material_button;
    private final int containerPageNews = R.id.container_list_news_include;
    public ViewInteraction textViewNewsOnPageNews = onView((withText("News")));
    private final int buttonFilterNews = R.id.filter_news_material_button;

    public int getContainerNews() {
        return containerPageNews;
    }

    @Step("Проверка видимости 'Новости'")
    public void checkNews() {
        Allure.step("Проверка видимости элемента с текстом 'News'");
        onView(withId(containerPageNews)).check(matches(ViewMatchers.isDisplayed()));
        textViewNewsOnPageNews.check(matches(withText("News")));
    }


    @Step("Нажатие на кнопку фильтрации новостей")
    public void openFormFilterNews() {
        Allure.step("Нажатие на кнопку фильтрации новостей");
        // Проверяем, что элемент видим и можно на него нажать
        onView(withId(buttonFilterNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonFilterNews)).perform(ViewActions.click());
        // Ожидание загрузки формы
        onView(isRoot()).perform(Utils.waitDisplayed(filterNewsPage.getFilter(), 5000));
    }

    @Step("Переход на 'Панель управления'")
    public void switchControlPanelNews() {
        Allure.step("Переход на 'Control panel'");
        //  Переход на страницу с панелью управления
        onView(isRoot()).perform(Utils.waitDisplayed(buttonControlPanelNews, 5000));
        // Проверяем, что элемент видно и что можно нажать
        onView(withId(buttonControlPanelNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonControlPanelNews)).perform(ViewActions.click());
        // Ожидаем загрузку панели управления
        onView(isRoot()).perform(Utils.waitDisplayed(controlPanelNews.getButtonAddNews(), 5000));
    }

}