package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;

import java.time.Duration;
import java.util.UUID;

public class registerSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private static boolean isFirstTest = true;

    @Before
    public void setUp() {
        if (isFirstTest) {
            try {
                Thread.sleep(2000);
                isFirstTest = false;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @After
    public void tearDown() {
        // تأخير بين الاختبارات
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Given("User navigates to the registration page")
    public void userNavigatesToTheRegistrationPage() {
        String browser = System.getenv("BROWSER");
        if (browser == null) browser = "chrome";

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @And("Entered date of birth {string}")
    public void entered_date_of_brith(String birth) {
        waitForElement(By.xpath("//*[@id=\"dp\"]")).sendKeys(birth);
    }

    @And("Entered first name {string}")
    public void iHaveEnteredFirstName(String firstN) {
        driver.findElement(By.id("member_firstname")).sendKeys(firstN);
    }

    @And("Entered last name {string}")
    public void enteredLastName(String lastN) {
        driver.findElement(By.cssSelector("#member_lastname")).sendKeys(lastN);
    }

    @And("Entered Email Adress {string}")
    public void enteredEmailAdress(String eEmail) {
        driver.findElement(By.cssSelector("[name=EmailAddress]")).sendKeys(eEmail);

    }

    @And("Entered confirmed Email Adress {string}")
    public void enteredConfirmedEmailAdress(String cEmail) {
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(cEmail);
    }

    @And("Entered password {string}")
    public void enteredPassword(String ePassword) {
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(ePassword);

    }

    @And("Entered Retyped password {string}")
    public void enteredRetypedPassword(String cPassword) {
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(cPassword);

    }

    @And("Checked box for marketing communications")
    public void checkedBoxForMarketingCommunications() {
        driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[10]/div/div/div[1]/div/label/span[3]")).click();

    }

    @And("Checked box for Terms and Conditions {string}")
    public void checkedBoxForTermsAndConditions(String accepted) {
        if (accepted.equalsIgnoreCase("true")) {
            driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[1]/label/span[3]")).click();
        }

    }

    @And("Checked the box for age over 18")
    public void checkedTheBoxForAgeOver() {
        driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[2]/label/span[3]")).click();

    }

    @And("Checked box for Code of Ethics")
    public void checkedBoxForCodeOfEthics() {
        driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[7]/label/span[3]")).click();

    }

    @When("Press for confirm and join")
    public void pressForConfirmAndJoin() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        waitForElement(By.xpath("//*[@id=\"signup_form\"]/div[12]/input")).click();
    }
    }

//    @Then("the application is successful")
//    public void theApplicationIsSuccessful() {
//        WebElement confirmatioHeader = driver.findElement(By.xpath("/html/body/div/div[2]/div/h2"));
//        String actualHeader = confirmatioHeader.getText();
//        String expactedHeader ="THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
//        assertEquals(actualHeader, expactedHeader);
//    }





    @Then("the application not successful")
    public void theApplicationNotSuccessful() {
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[5]/div[2]/div/span/span"));
        String actalerrorMessage = errorMessage.getText();
        String expactederrorMessage ="Last Name is required";
        assertEquals(actalerrorMessage, expactederrorMessage);
        driver.quit();

    }

   @Then("the application result should be {string}")
    public void theApplicationResultShouldBe(String expectedResult) {
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement errorMessage;
        String actualMessage;
        switch (expectedResult.toLowerCase()) {

            case "missing_last_name":
                // Check if error message for missing last name is displayed
                errorMessage = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[5]/div[2]/div/span/span"));
                actualMessage = errorMessage.getText();
                String expectedLastNameError = "Last Name is required";
                assertEquals(actualMessage, expectedLastNameError);
                break;

            case "password_mismatch":
                // Check if error message for password mismatch is displayed
                errorMessage = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[8]/div/div[2]/div[2]/div/span/span"));
                actualMessage = errorMessage.getText();
                String expectedPasswordMismatchError = "Password did not match";
                assertEquals(actualMessage, expectedPasswordMismatchError);
                break;

            case "terms_not_accepted":
                // Check if error message for Terms and Conditions not accepted is displayed
                //errorMessage = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[1]/span/span"));
                errorMessage = waitForElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[1]/span/span"));
                actualMessage = errorMessage.getText();
                System.out.println(actualMessage);
                String expectedErrorMessage = "You must confirm that you have read and accepted our Terms and Conditions";
                assertEquals(actualMessage, expectedErrorMessage);

                break;

            default:
                // If no match found
                throw new AssertionError("Unexpected result: " + expectedResult);
        }
    }


}



