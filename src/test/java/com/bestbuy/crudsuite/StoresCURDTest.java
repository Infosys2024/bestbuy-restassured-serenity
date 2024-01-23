package com.bestbuy.crudsuite;

import com.bestbuy.bestbuyinfo.StoreSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCURDTest extends TestBase {
    static String name = TestUtils.getRandomValue() + "John";
    static String type = TestUtils.getRandomValue() + "BigBox";
    static String address = TestUtils.getRandomValue() + "Sanskrit";
    static String address2 = TestUtils.getRandomValue() + "Northolt";
    static String city = TestUtils.getRandomValue() + "London";
    static String state = TestUtils.getRandomValue() + "UK";
    static String zip = TestUtils.getRandomValue() + "5664432";
    static int lat = Integer.parseInt(TestUtils.getRandomValue());
    static int lng = Integer.parseInt(TestUtils.getRandomValue());
    static String hours = TestUtils.getRandomValue();
    static int storeID;

    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response = storeSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(201);
        storeID = response.log().all().extract().path("id");

    }

    @Title("Verify if the store was added to application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = storeSteps.getStoreInfoByStoreName(storeID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test003() {
        ValidatableResponse response = storeSteps.updateStore(storeID, name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(200);
        storeID = response.log().all().extract().path("id");

        HashMap<String, Object> productMap = storeSteps.getStoreInfoByStoreName(storeID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the Store and verify if the store has been deleted")
    @Test
    public void test004() {
        storeSteps.deleteStore(storeID).statusCode(200);
        storeSteps.getStoreById(storeID).statusCode(404);

    }

}