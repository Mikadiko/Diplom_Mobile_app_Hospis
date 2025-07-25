package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.Utils;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.ThematicArticle;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainPageTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    ThematicArticle thematicArticle = new ThematicArticle();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 5000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Отображение блока новостей TC-8'")
    @Test
    public void displayingNewsBlock() {
        mainPage.isDisplayedButtonProfile();
    }

    @Description("Переход на страницу 'Все новости' через текст 'ALL NEWS' TC-13")
    @Test
    public void openPageNewsThroughAllNewsTab() {
        mainPage.setButtonAllNews();
        newsPage.checkNews();
    }

    @Description("Переход на страницу 'News' с главной страницы через бургер меню TC-14")
    @Test
    public void openPageNews() {
        appBar.switchToNews();
        newsPage.checkNews();
    }

    @Description("Переход на страницу 'About' с главной страницы через бургер меню TC-15")
    @Test
    public void openPageAboutApplication() {
        mainPage.isDisplayedButtonProfile();
        appBar.AboutApp();
    }

    @Description("Переход на страницу 'Love is all' через иконку бабочки TC-18")
    @Test
    public void shouldOpenPageWithThematicArticles() {
        appBar.pageOurMission();
        thematicArticle.textScreenCheckIsDisplayed();
    }

    @Description("Выход из приложения, когда открыта главная страница TC-19")
    @Test
    public void logOut() {
        mainPage.isDisplayedButtonProfile();
        appBar.logOut();
    }

    @Description("Выход из приложения, когда открыта страница 'News' со всеми новостями TC-20")
    @Test
    public void exitAppWhenTheNewsPageOpen() {
        appBar.switchToNews();
        appBar.logOut();
    }

}
