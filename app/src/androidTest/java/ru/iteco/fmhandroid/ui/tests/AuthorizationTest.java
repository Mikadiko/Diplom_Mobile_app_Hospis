package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.bloco.faker.Faker;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.Utils;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    Faker faker = new Faker();

    private String validLogin = "login2";
    private String validPassword = "password2";
    private String invalidLogin = faker.internet.password();
    private String invalidPassword = faker.name.firstName() + faker.number.between(1, 10);


    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 15000));
        if (mainPage.isDisplayedButtonProfile()) {
            appBar.logOut();
        }
    }

    @Description("Отображение всех необходимых элеменов на экране авторизации TC-2")
    @Test
    public void visiblElAuthScreen() {
        authorizationPage.visibilityElement();
        authorizationPage.visibilityLoginElement();
        authorizationPage.visibilityPasswordElement();
    }

    @Description("Авторизация с валидными данными TC-3")
    @Test
    public void successfulAuthorization() {
        authorizationPage.visibilityElement();
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();
        mainPage.isDisplayedButtonProfile();
    }

    @Description("Авторизация с незарегистрированными данными TC-4")
    @Test
    public void authFailedLoginAndPassword() {
        authorizationPage.inputInFieldLogin(invalidLogin);
        authorizationPage.inputInFieldPassword(invalidPassword);
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с незаполненным логином TC-5")
    @Test
    public void authorizationEmptyUsername() {
        authorizationPage.inputInFieldPassword(validPassword);
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с незаполненным паролем TC-6")
    @Test
    public void authorizationEmptyPassword() {
        authorizationPage.inputInFieldLogin(validLogin);
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация без ввода логина и пароля TC-7")
    @Test
    public void authorizationEmptyLoginAndPassword() {
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }
}
