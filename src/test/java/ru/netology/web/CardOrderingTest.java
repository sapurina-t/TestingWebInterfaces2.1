package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderingTest {
   private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver=null;
    }

    @Test
    void shouldSendTheForm() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79121234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }

    @Test
    void shouldSendTheFormHyphenated() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Имя Фамилия-Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79121234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }

    @Test
    void shouldGiveAnErrorOnLatinLetters() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Name Surname");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79121234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldShowAnErrorWhenTheNameFieldIsEmpty() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Name Surname");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79121234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldGiveAnErrorWhenEnteringAnEmptyNameField() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79121234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldGiveAnErrorWhenThereAreNotEnoughDigits() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7912123456");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldGiveAnErrorWhenThereAreMoreDigits() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+791212345678");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }
    @Test
    void shouldShowAnErrorWhenThePhoneFieldIsEmpty() {

        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

}


