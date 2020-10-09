package com.inomera.promotionstest.service;

import com.inomera.promotionstest.model.Promotion;
import com.inomera.promotionstest.model.PromotionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.tomcat.jni.OS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@Service
@Slf4j
public class PromotionApplyService {

    public Promotion applyPromotion(Promotion promotion) throws MalformedURLException {
        log.error("Promotion : " + promotion.getProductId() + " - " + promotion.getPayQty()+ " - " + promotion.getPromotionName());

        WebDriver driver = launchBrowser();

        openProductPage(driver, promotion);
        incrementToCart(driver, promotion);
        openCartPage(driver, promotion);

        if (PromotionType.COUPON.equals(promotion.getPromotionType())){
            setCoupon(driver, promotion);
        }

        promotion.setResult(isPromotionActive(driver, promotion));
        driver.close();
        return promotion;
    }

    public WebDriver launchBrowser(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("window-size=1200x600");
            try{   //GOOGLE_CHROME_SHIM GOOGLE_CHROME_BIN
                String binaryPath= EnvironmentUtils.getProcEnvironment().get("GOOGLE_CHROME_SHIM");
                System.out.println("Path: "+binaryPath);
                options.setBinary(binaryPath);
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
            }catch(Exception e){
                log.error("catch");
            }

        WebDriver driver=new ChromeDriver(options);
        driver.get("http://google.com");

        return driver;
    }

    private void setCoupon(WebDriver driver, Promotion promotion) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement navBarLogin = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"checkout_cart\"]/div[4]/div/div[1]/a"))));
        navBarLogin.click();

        waitForPageLoaded(driver);
        WebElement popup =  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"js-voucher-code-text\"]"))));
        popup.sendKeys(promotion.getCouponCode());


        waitForPageLoaded(driver);

        WebElement applyCoupon = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(" //*[@id=\"js-voucher-apply-btn\"]"))));
        applyCoupon.click();

    }

    private void openProductPage(WebDriver driver, Promotion promotion) {
        String baseUrl = promotion.getSiteUrl() + "p/" + promotion.getProductId();
        driver.get(baseUrl);
        waitForPageLoaded(driver);

//        if (promotion.getSiteUrl().contains("karaca.com")) {
//            WebDriverWait wait = new WebDriverWait(driver, 10);
//            WebElement lang = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"language-currency-form\"]/button"))));
//            lang.click();
//            waitForPageLoaded(driver);
//            driver.get(baseUrl);
//        }
    }

    private void incrementToCart(WebDriver driver, Promotion promotion) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        for (int i = 1; i < promotion.getProductQty(); i++) {

            WebElement navBarLogin = null;
            if (promotion.getSiteUrl().contains("emsan")) {
                navBarLogin = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"addToCartForm\"]/div/div[1]/div/span[2]/button"))));
            } else {
                navBarLogin = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("incrementButton"))));
            }
            navBarLogin.click();
            waitForPageLoaded(driver);
        }

        WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("addcart"))));
        popup.click();
        waitForPageLoaded(driver);
    }

    private void openCartPage(WebDriver driver, Promotion promotion) {
        String cartUrl = promotion.getSiteUrl() + "cart";
        driver.get(cartUrl);
        waitForPageLoaded(driver);
    }

    private double getShippingFee(WebDriver driver) {
        double shipping = 0;
        if (driver.findElement(By.xpath("//*[@id=\"checkout_cart\"]/div[4]/div/div[2]/div/table/tbody/tr[2]/td[1]")).getText().equalsIgnoreCase("Shipping Fee:")) {
            String d = driver.findElement(By.xpath("//*[@id=\"checkout_cart\"]/div[4]/div/div[2]/div/table/tbody/tr[2]/td[2]")).getText();
            d = d.substring(1, d.length());
            try {
                shipping = Double.valueOf(d);
            } catch (NumberFormatException e) {
                shipping = 0;
            }
        }
        return shipping;
    }

    private double getTotalPrice(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement total = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("cartTotalWithoutCurrency"))));
        String a = total.getAttribute("data-amount");

        return Double.valueOf(a);
    }

    private double getProductPrice(WebDriver driver) {

        WebElement price = driver.findElement(By.xpath("//*[@id=\"checkout_cart\"]/div[1]/div/div[4]/table/tbody/tr/td/div/div[5]"));
        String c = price.getText().replace("x", "");

        if (c.contains("TL")) {
            c = c.replace("TL", "").replace(",", ".").replace(" ", "");
        } else {
            c = c.substring(1, c.length());
        }

        return Double.valueOf(c.replace(" ","").replace(",",".").replace("x",""));
    }

    private boolean isPromotionActive(WebDriver driver, Promotion promotion) {
        double shippingFee = round(getShippingFee(driver),2);
        double totalPrice = round(getTotalPrice(driver),2);
        double productPrice = round(getProductPrice(driver),2);

        if (PromotionType.SHIPPING.equals(promotion.getPromotionType())){
            if (promotion.getSiteUrl().contains("karaca")){
                if (totalPrice >= 125){
                    if (shippingFee != 0){
                        return false;
                    }
                }
            }else {
                if (totalPrice >= 200){
                    if (shippingFee != 0){
                        return false;
                    }
                }
            }
        }else if (PromotionType.GIFT.equals(promotion.getPromotionType())){
            if (totalPrice != round(shippingFee + productPrice * promotion.getPayQty(),2)) {
                return false;
            }
        }else if (PromotionType.COUPON.equals(promotion.getPromotionType())){
            if(promotion.getDiscountPercent() != 0){
                double total = round(productPrice * promotion.getProductQty() + shippingFee, 2);
                total = total - total * promotion.getDiscountPercent() / 100;
                if(totalPrice != total){
                    return false;
                }
            }
        }else if (PromotionType.DISCOUNT.equals(promotion.getPromotionType())){
            double total = round(productPrice * promotion.getProductQty() + shippingFee, 2);
            total = total - total * promotion.getDiscountPercent() / 100;
            if(totalPrice != round(total,2)){
                return false;
            }

        }

        return false;
    }

    private void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println("Timeout waiting for Page Load Request to complete.");
        }
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
