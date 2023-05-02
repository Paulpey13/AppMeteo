package app.appmeteo.model;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class User {

    private String units;
    private String lang;
    private JSONObject TextForLang;

    public User() throws Exception {
        this.lang = getUserSettings().get(0);
        this.units = getUserSettings().get(1);
        this.TextForLang = getLangConfig(getUserSettings().get(0));
    }

    public String getLang() {
        return lang;
    }

    public String getUnits() {
        return units;
    }

    public JSONObject getLangConfig(String lang) throws Exception {
        String jsonString = readFileAsString("./src/main/resources/app/appmeteo/data/lang/" + lang + ".json");

        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject;
    }

    public String getTRAD_PleaseEnterAValidCity() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Please_Enter_A_Valid_City_Name").toString());
    }

    public String getTRAD_AddFavoriteCity() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Add_Favorite_City").toString());
    }

    public String getTRAD_Enter_your_city() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Enter_your_city").toString());
    }

    public String getTRAD_City() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("City").toString());
    }

    public String getTRAD_Temperature() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Temperature").toString());
    }

    public String getTRAD_Weather() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Weather").toString());
    }

    public String getTRAD_DeleteSelectedCity() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Delete_Selected_City").toString());
    }

    public String getTRAD_Error() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Error").toString());
    }

    public String getTRAD_MaxSevenDays() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Max_seven_days").toString());
    }

    public String getTRAD_Settings() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Settings").toString());
    }

    public String getTRAD_IsAllReadyIn() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Is_all_ready_in").toString());
    }

    public String getTRAD_About() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("About").toString());
    }

    public String getTRAD_ThisApp() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("ThisApp").toString());
    }
    
    public  String getTRAD_Imperial() throws UnsupportedEncodingException {
        return stringToUTF8(this.TextForLang.get("Imperial").toString());}
    public  String getTRAD_Metric() throws UnsupportedEncodingException {return stringToUTF8(this.TextForLang.get("Metric").toString());}

    public  String getTRAD_Standard() throws UnsupportedEncodingException {return stringToUTF8(this.TextForLang.get("Standard").toString());}

    private String stringToUTF8(String stringToChange) throws UnsupportedEncodingException {
        byte ptext[] = stringToChange.getBytes("ISO-8859-1");
        return new String(ptext, "UTF-8");
    }

    public ArrayList < String > getUserSettings() throws Exception {
        String file = "./src/main/resources/app/appmeteo/data/UserSettings.txt";
        String json = readFileAsString(file);
        String Language = json.substring(0, 2);
        String units = json.substring(3);

        ArrayList ListOfSettings = new ArrayList();
        ListOfSettings.add(Language);
        ListOfSettings.add(units);

        return ListOfSettings;
    }

    public void setUserSettings(String lang, String unit) throws Exception {
        Write(lang + "," + unit);
    }

    public void Write(String str) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/resources/app/appmeteo/data/UserSettings.txt"));
        writer.write(str);

        writer.close();
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
