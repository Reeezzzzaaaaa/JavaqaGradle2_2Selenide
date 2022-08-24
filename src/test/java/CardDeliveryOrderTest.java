import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {

    @BeforeEach
    void setUp() {

        open("http://localhost:9999/");
    }

    public String dateMeeting(int delay) {

        return LocalDate.now().plusDays(delay).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    }

    @Test
    void validTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        sleep(12000);
        $x("//*[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void cityEmptyTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[@data-test-id='city']//*[contains(text(), 'Поле')]").should(appear);
    }

    @Test
    void notAdministrativeCenterTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Карачев");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);
    }

    @Test
    void cityNotFromRussiaTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Токио");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);
    }

    @Test
    void dateMeeting4daysTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(4));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        sleep(12000);
        $x("//*[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void dateMeeting2daysTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(2));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    void nameEngTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Ivanov Petr");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Имя и Фамилия указаные неверно')]").should(appear);
    }

    @Test
    void nameSpecialCharacterTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил%");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Имя и Фамилия указаные неверно')]").should(appear);
    }

    @Test
    void nameEmptyTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[@data-test-id='name']//*[contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    void phone12SymbolTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+799912345678");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Телефон указан неверно')]").should(appear);
    }

    @Test
    void phone10SymbolTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+7999123456");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[contains(text(), 'Телефон указан неверно')]").should(appear);
    }

    @Test
    void phoneEmptyTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[@data-test-id='phone']//*[contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    void agreementTest() {

        Configuration.holdBrowserOpen=true;
        $x("//input[@placeholder=\"Город\"]").setValue("Брянск");
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(dateMeeting(3));
        $("[data-test-id=name] input").setValue("Иванов-Сидоров Михаил");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $x("//*[@class=\"button__content\"]").click();
        $x("//*[@name='agreement']/ancestor::label['input_invalid']").shouldBe(visible);
    }
}
