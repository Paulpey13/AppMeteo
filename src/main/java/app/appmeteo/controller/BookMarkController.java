package app.appmeteo.controller;

import app.appmeteo.model.ReadData;
import app.appmeteo.model.ReadJSON;
import app.appmeteo.model.User;

import java.io.IOException;

public class BookMarkController extends AppMeteoController {
    private String city;
    private String temperature;
    private String weather;

    public BookMarkController(String city) throws IOException {
        super();

        try {
            User user = new User();
            ReadData readData = new ReadData(new ReadJSON(city, user.getLang(), user.getUnits(), 0).getCurrentRequestResult(), new ReadJSON(city, user.getLang(), user.getUnits(), 0).getDaily_REQUESTRequestResult());

            this.city = city;

            this.temperature = readData.getTemperature();

            this.weather = readData.getWeatherDesc().toString();

            this.weather = this.weather.substring(0, 1).toUpperCase() + this.weather.substring(1);

        } catch (Exception e) {

        }

    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }
}