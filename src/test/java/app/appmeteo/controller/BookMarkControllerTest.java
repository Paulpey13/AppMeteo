package app.appmeteo.controller;

import app.appmeteo.model.ReadData;
import app.appmeteo.model.ReadJSON;
import app.appmeteo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMarkControllerTest {

    User user=new User();
    BookMarkController bookMarkController=new BookMarkController("Venise");
    ReadJSON jsonResponse = new ReadJSON("Venise",user.getLang(),user.getUnits(),0);
    ReadData readData = new ReadData(jsonResponse.Current_REQUEST, jsonResponse.Daily_REQUEST);



    BookMarkControllerTest() throws Exception {
    }

    @Test
    void getCity() {
        Object expected="Venise";
        Object actual =bookMarkController.getCity();
        assertEquals(expected,actual);
    }

    @Test
    void getTemperature() {
        Object expected=readData.getTemperature();
        Object actual=bookMarkController.getTemperature();
        assertEquals(expected,actual);
    }

    @Test
    void getWeather() {
        String nom=readData.getWeatherDesc().toString();
        Object expected=nom.substring(0, 1).toUpperCase() + nom.substring(1);
        Object actual=bookMarkController.getWeather();
        assertEquals(expected,actual);
    }


}