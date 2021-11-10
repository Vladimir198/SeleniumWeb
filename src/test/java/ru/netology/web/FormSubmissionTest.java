package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormSubmissionTest {
    private WebDriver driver;
    
    // используется, чтобы сигнализировать, что аннотированный метод 
    // должен быть выполнен перед всеми тестами в текущем тестовом классе.
    @BeforeAll
    static void setUpAll()  {
        WebDriverManager.chromedriver().setup();
       // System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
    }

    // используется для обозначения того, что
    // аннотированный метод должен выполняться перед каждым @Test
    @BeforeEach
    void setUp() {
        //driver = new ChromeDriver();
        //Включение headless режима при использовании
        // selenium необходимо реализовать в коде во время создания экземпляра webdriver:
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    //используется для обозначения того, что аннотированный
    // метод должен выполняться после каждого @Test
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("input__control")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! " +
                "Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldCheckboxIsChecked() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("checkbox")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__control")).isSelected());
    }

    @Test
    void shouldNegativeFamily() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("input__control")).sendKeys("Pavel Petrov");
        driver.findElement(By.name("phone")).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только " +
                "русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldEmptyNameInputBox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.name("phone")).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNegativePhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("input__control")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("259270000000");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldEmptyPhoneInputBox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("input__control")).sendKeys("Василий");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNegativeCheckboxColor() {
        driver.get("http://localhost:9999");
        driver.findElement(By.className("input__control")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+79270000000");
        driver.findElement(By.className("button")).click();
        String color = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", color);
    }


}
