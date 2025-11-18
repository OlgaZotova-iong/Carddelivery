package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    private String generateDate(int daysToAdd, String pattern) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysToAdd);
        return futureDate.format(DateTimeFormatter.ofPattern(pattern));
    }


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestSuccessOrder() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно! Встреча успешно забронирована на " + planningDate));

    }
}

