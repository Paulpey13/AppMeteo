package app.appmeteo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class ReadJSON {

    public JSONObject Current_REQUEST;
    public JSONObject Daily_REQUEST;

    public < lang > ReadJSON(String city, String lang, String Unit, int intervals_Day) throws IOException {

        String getterCoords = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=ecd518321a98c4f4d44445b3caea8fd9&lang=" + lang + "&units=" + Unit;

        this.Current_REQUEST = readJsonFromUrl(getterCoords);

        String Long = Current_REQUEST.getJSONObject("coord").get("lon").toString();
        String Lat = Current_REQUEST.getJSONObject("coord").get("lat").toString();
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + Lat + "&lon=" + Long + "&lang=" + lang + "&exclude=hourly,minutely&appid=ecd518321a98c4f4d44445b3caea8fd9&units=" + Unit;

        if (intervals_Day == 0) {
            this.Daily_REQUEST = readJsonFromUrl(url).getJSONObject("current");
        }
        if (intervals_Day != 0) {

            JSONObject a = (JSONObject) readJsonFromUrl(url).getJSONArray("daily").get(intervals_Day - 1);
            this.Daily_REQUEST = a;

        }

    }

    public JSONObject getCurrentRequestResult() {
        return this.Current_REQUEST;
    }

    public JSONObject getDaily_REQUESTRequestResult() {
        return this.Daily_REQUEST;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        if (!url.isEmpty()) {
            InputStream is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
        } else

        {
            return null;
        }
    }

}
