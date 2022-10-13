package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SubmitFormsTest {
    SelenideElement form = $(".form");

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999");
    }


    @Test
    public void shouldValidNameTest() {
        form.$("[data-test-id=name] input").setValue("Носырев Иван");
        form.$("[data-test-id=phone] input").setValue("+79552464622");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldValidNameNameTest1() {
        form.$("[data-test-id=name] input").setValue("Анна-Мария Лагнесс");
        form.$("[data-test-id=phone] input").setValue("+79211234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    public void shouldInvalidNameTest2() {
        form.$("[data-test-id=name] input").setValue("Ivan Popov");
        form.$("[data-test-id=phone] input").setValue("+79199552643");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //баг, не принимает "ё".
    @Test
    public void shouldInvalidNameTest3() {
        form.$("[data-test-id=name] input").setValue("Носырёв Иван");
        form.$("[data-test-id=phone] input").setValue("+79199552643");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    public void shouldInvalidNameTest4() {
        form.$("[data-test-id=name] input").setValue("+79199552643");
        form.$("[data-test-id=phone] input").setValue("Дмитрий Корягин");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    public void shouldInvalidPhoneTest() {
        form.$("[data-test-id=name] input").setValue("АНАСТАСИЯ ФЕДОРОВА");
        form.$("[data-test-id=phone] input").setValue("89199552643");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    public void shouldInvalidPhoneTest1() {
        form.$("[data-test-id=name] input").setValue("Анфиса Чехова");
        form.$("[data-test-id=phone] input").setValue("+790112345678");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldInvalidPhoneTest2() {
        form.$("[data-test-id=name] input").setValue("Дядя Вася");
        form.$("[data-test-id=phone] input").setValue("Нет");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    public void shouldEmptyInputTest() {
        form.$("[data-test-id=phone] input").setValue("+79199552643");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldEmptyInputTest1() {
        form.$("[data-test-id=name] input").setValue("Негодов Михаил");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldEmptyCheckBoxTest() {
        form.$("[data-test-id=name] input").setValue("Гагарин Юрий");
        form.$("[data-test-id=phone] input").setValue("+79211234567");
        form.$("button.button").click();
        form.$("[data-test-id=agreement]").should(cssClass("input_invalid"));
    }
}
