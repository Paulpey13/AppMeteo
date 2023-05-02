package app.appmeteo.model;

import org.json.JSONObject;

public class ReadData {

    public JSONObject Current_REQUEST;
    public JSONObject Daily_REQUEST;

    public ReadData(JSONObject jsonCurrentData, JSONObject jsonDailyData) {

        this.Current_REQUEST = jsonCurrentData;
        this.Daily_REQUEST = jsonDailyData;

    }

    public Object getLon() {
        return this.Current_REQUEST.getJSONObject("coord").get("lon");
    }

    public Object getWeather() {
        return this.Daily_REQUEST.getJSONArray("weather").getJSONObject(0).get("main");
    }

    public Object getWeatherDesc() {
        return this.Daily_REQUEST.getJSONArray("weather").getJSONObject(0).get("description");
    }

    public Object getWindSpeed() {
        return this.Daily_REQUEST.get("wind_speed");
    }

    public Object getWindDirection() {
        return this.Current_REQUEST.getJSONObject("wind").get("deg");
    }

    public Object getId() {
        return this.Current_REQUEST.get("id");
    }

    public Object getName() {
        return this.Current_REQUEST.get("name");
    }

    public String getTemperature() {

        if (this.Daily_REQUEST.get("temp").getClass().toString().equals("class org.json.JSONObject")) {
            return this.Daily_REQUEST.getJSONObject("temp").get("day").toString();
        }

        return this.Daily_REQUEST.get("temp").toString();

    }

    public String getCloudPercent() {
        return this.Current_REQUEST.getJSONObject("clouds").get("all").toString();
    }

    public Object getVisibility() {
        return this.Current_REQUEST.get("visibility");
    }

    public int getHumidity() {
        return (int) this.Current_REQUEST.getJSONObject("main").get("humidity");
    }

}
