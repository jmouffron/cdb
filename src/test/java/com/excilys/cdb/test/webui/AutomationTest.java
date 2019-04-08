package com.excilys.cdb.test.webui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationTest {

  static WebDriver firefoxDriver;
  static WebDriver chromeDriver;

  static List<WebDriver> driverList;

  Long id = 900L;
  String name = "name";
  String companyName = "Asus";
  Timestamp introduced = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
  Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now());
  Long companyId = 1L;

  @BeforeEach
  static void setUp() {
    System.setProperty("webdriver.gecko.driver", "/home/Documents/geckodriver-v0.24.0-linux64");
    System.setProperty("webdriver.chrome.driver", "/home/Documents/chromedriver_linux64");

    firefoxDriver = new FirefoxDriver();
    chromeDriver = new ChromeDriver();

    driverList = new ArrayList<WebDriver>();

    driverList.add(firefoxDriver);
    driverList.add(chromeDriver);
  }

  @AfterEach
  static void tearDown() {
    driverList.forEach(driver -> {
      driver.close();
      driver.quit();
    });
  }

  @Test
  public void driverDashBoardTest() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/dashboard");
      assertEquals("Title check failed!", "Computer Database | Dashboard", driver.getTitle());
      driver.close();
    });

  }

  @Test
  public void givenComputerPerPage_whenChangingPerPageDashBoard_thenSuceed() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/");

      WebElement group = driver.findElement(By.cssSelector("div[role='group']"));
      List<WebElement> buttons = group.findElements(By.tagName("button"));
      WebElement perPageButton10 = buttons.stream().filter(button -> button.getText().equals("10")).findFirst().get();
      perPageButton10.click();

      List<WebElement> results = driver.findElements(By.cssSelector("#result tr"));

      assertEquals(10, results.size());

      WebElement perPageButton50 = buttons.stream().filter(button -> button.getText().equals("50")).findFirst().get();
      perPageButton50.click();

      results = driver.findElements(By.cssSelector("#result tr"));

      assertEquals(50, results.size());

      WebElement perPageButton100 = buttons.stream().filter(button -> button.getText().equals("0")).findFirst().get();
      perPageButton100.click();

      results = driver.findElements(By.cssSelector("#result tr"));

      assertEquals(100, results.size());

    });

  }

  @Test
  public void givenPage_whenPaginateDashBoard_thenSuceed() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/");

      List<WebElement> liGroup = driver.findElements(By.cssSelector(".pagination li a"));
      WebElement nextPage = liGroup.stream().filter(a -> a.getAttribute("aria-label").equals("next")).findFirst().get();
      nextPage.click();
      assertEquals("http://localhost:8080/cdb/?page=2&search=", driver.getCurrentUrl());

      liGroup = driver.findElements(By.cssSelector(".pagination li a"));
      WebElement previousPage = liGroup.stream().filter(a -> a.getAttribute("aria-label").equals("previous"))
          .findFirst().get();
      previousPage.click();
      assertEquals("http://localhost:8080/cdb/?page=1&search=", driver.getCurrentUrl());
    });

  }

  @Test
  public void driverEditComputerTest() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/editComputer");

      WebElement nameInput = driver.findElement(By.name("computerName"));
      WebElement introInput = driver.findElement(By.name("introduced"));
      WebElement discoInput = driver.findElement(By.name("discontinued"));
      Select select = new Select(driver.findElement(By.id("companyId")));
      WebElement form = driver.findElement(By.cssSelector("form"));

      WebElement successNote = driver.findElement(By.id("success"));
      WebElement dangerNote = driver.findElement(By.id("danger"));

      nameInput.sendKeys(name);
      introInput.sendKeys(introduced.toString());
      discoInput.sendKeys(discontinued.toString());

      select.deselectAll();
      select.selectByVisibleText(companyName);

      form.submit();

      (new WebDriverWait(driver, 10)).until(thisDriver -> thisDriver.getTitle().toLowerCase().matches("dashboard"));

      assertEquals("Title check failed!", "Computer Database | Edit Computer", driver.getTitle());
      assertEquals("Create action failed!", "Computer " + name + " succesfully created!", successNote.getText());
      assertFalse(dangerNote.getText().isEmpty() || dangerNote.getText() != null);

    });

  }

  @Test
  public void driverAddComputerTest() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/addComputer");

      WebElement nameInput = driver.findElement(By.name("computerName"));
      WebElement introInput = driver.findElement(By.name("introduced"));
      WebElement discoInput = driver.findElement(By.name("discontinued"));
      Select select = new Select(driver.findElement(By.id("companyId")));
      WebElement form = driver.findElement(By.cssSelector("computerName"));

      WebElement successNote = driver.findElement(By.id("success"));
      WebElement dangerNote = driver.findElement(By.id("danger"));

      nameInput.sendKeys(name);
      introInput.sendKeys(introduced.toString());
      discoInput.sendKeys(discontinued.toString());

      select.deselectAll();
      select.deselectByVisibleText(companyName);

      form.submit();

      (new WebDriverWait(driver, 10)).until(thisDriver -> thisDriver.getTitle().toLowerCase().matches("dashboard"));

      assertEquals("Title check failed!", "Computer Database | Dashboard", driver.getTitle());
      assertEquals("Create action failed!", "Computer " + name + " succesfully created!", successNote.getText());
      assertTrue(!successNote.getText().isEmpty() || successNote.getText() == null);
      assertFalse(dangerNote.getText().isEmpty() || dangerNote.getText() != null);
    });

  }

  @Test
  public void driverDeleteComputerTest() {

    driverList.forEach(driver -> {
      driver.navigate().to("http://localhost:8080/cdb/addComputer");

      WebElement nameInput = driver.findElement(By.name("computerName"));
      WebElement introInput = driver.findElement(By.name("introduced"));
      WebElement discoInput = driver.findElement(By.name("discontinued"));
      Select select = new Select(driver.findElement(By.id("companyId")));
      WebElement form = driver.findElement(By.cssSelector("computerName"));

      WebElement successNote = driver.findElement(By.id("success"));
      WebElement dangerNote = driver.findElement(By.id("danger"));

      nameInput.sendKeys(name);
      introInput.sendKeys(introduced.toString());
      discoInput.sendKeys(discontinued.toString());

      select.deselectAll();
      select.deselectByVisibleText(companyName);

      form.submit();

      (new WebDriverWait(driver, 10)).until(thisDriver -> 
        thisDriver.getTitle().matches("Computer Database | Dashboard")
      );

      assertEquals("Title check failed!", "Computer Database | Delete Computer", driver.getTitle());
      assertEquals("Create action failed!", "Computer " + name + " succesfully created!", successNote.getText());
      assertFalse(dangerNote.getText().isEmpty() || dangerNote.getText() != null);
    });

  }

}