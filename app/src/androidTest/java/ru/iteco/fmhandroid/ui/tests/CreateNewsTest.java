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
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.CreateNews;
import ru.iteco.fmhandroid.ui.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreateNewsTest {
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    CreateNews createNews = new CreateNews();
    EditNews editNews = new EditNews();
    NewsPage newsPage = new NewsPage();
    Faker faker = new Faker();
    private String choosingСategory = "Объявление";
    private String invalidCategory = faker.book.genre();
    private String randomTitle = faker.food.ingredient();
    private String addendumTime = "21:20";
    private String addendumDescription = "cucaracha";

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    // Тест должен упасть, если есть такой же заголовок новости
    //Тест упадет, если список новостей очень длинный, а ваша новость самая последняя
    @Description("Успешное создание новой карточки TC-23")
    @Test
    public void successfulNewsCreation() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
    }

    @Description("Создание пустой карточки TC-25")
    @Test
    public void shouldStayOnNewsCreationScreenWhenCreatingNewsWithEmptyFields() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание карточки с выдуманной категорией TC-26")
    @Test
    public void creatingCardWithFictionalCategory() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(invalidCategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание карточки без указания дня TC-27")
    @Test
    public void creatingCardWithoutSpecifyingTheDay() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание карточки без указания времени TC-30")
    @Test
    public void creatingCardWithoutSpecifyingTheTime() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        createNews.verifyNewsCreationFormDisplayed();
    }

    /**
     * тест падает с ошибкой AmbiguousViewMatcherException
     * тест пытается взаимодействовать с элементом интерфейса (значок удаления),
     * который имеет несколько экземпляров в иерархии представлений
     * тест не знает какой именно элемент использовать, так как у них одинаковые идентификаторы
     */
    @Description("Удаление новости TC-52")
    @Test
    public void shouldDeleteNews() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.deleteNews();
        controlPanelNews.checkDoesNotExistNews(randomTitle);
    }
    /**
    * тест аналогично падает с ошибкой AmbiguousViewMatcherException
    * тест пытается взаимодействовать с элементом интерфейса (значок редактирования),
    * который имеет несколько экземпляров в иерархии представлений
    * тест не знает какой именно элемент использовать, так как у них одинаковые идентификаторы
    */
    @Description("Редактирование категории новости и сохранение изменений TC-57")
    @Test
    public void shouldEditCategoryOfNews() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.pressEditPanelNews();
        editNews.editCategory(randomTitle);
        editNews.pressSave();
    }

    /**
    * все ПОСЛЕДУЮЩИЕ тесты, связанные с редактированием, будут также падать
    * причина - та же. На примере с редактированием заголовка:
     */
    @Description("Редактирование заголовка новости и сохранение изменений TC-58")
    @Test
    public void editingTheNewsHeadline() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory(choosingСategory);
        // Добавление заголовка новости
        createNews.addTitle(randomTitle);
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime(addendumTime);
        // Добавление описания
        createNews.addDescription(addendumDescription);
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.pressEditPanelNews();
        editNews.editTitle(randomTitle);
        editNews.pressSave();
    }
}
