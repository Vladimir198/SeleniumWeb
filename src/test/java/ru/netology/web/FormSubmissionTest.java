package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormSubmissionTest {
    private WebDriver driver;
    
    // используется, чтобы сигнализировать, что аннотированный метод 
    // должен быть выполнен перед всеми тестами в текущем тестовом классе.
    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
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
        String text = driver.findElement(By.tagName("p")).getText();
        assertEquals("Ваша заявка успешно отправлена! " +
                "Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

}