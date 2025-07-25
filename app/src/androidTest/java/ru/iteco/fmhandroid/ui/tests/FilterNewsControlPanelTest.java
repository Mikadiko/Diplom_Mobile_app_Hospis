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
import ru.iteco.fmhandroid.ui.pageObject.FilterNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsControlPanelTest {
    AppBar appBar = new AppBar();
    FilterNews filterNews = new FilterNews();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();

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

    @Description("Фильтрация новостей по одной дате 'ОТ' TC-43")
    @Test
    public void filterNewsByCategoryAndDateFrom() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.openFormFilterNews();
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.confirmFilter();
        filterNews.checkErrorFilterNews("Wrong period");
    }

    @Description("Фильтрация новостей по одной дате 'ДО' TC-44")
    @Test
    public void filterNewsByCategoryAndDateTo() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.openFormFilterNews();
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        filterNews.checkErrorFilterNews("Wrong period");
    }
}
