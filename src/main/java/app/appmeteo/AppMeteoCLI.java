package app.appmeteo;

import app.appmeteo.model.BookMark;
import app.appmeteo.model.ReadData;
import app.appmeteo.model.ReadJSON;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AppMeteoCLI {
    private static String global_lang = "en";
    private static String global_units = "metric";

    private static BookMark bookMark = new BookMark();

    public static void main(String[] args) throws IOException {
        getFavorite();
        getMenu();

    }

    private static void getMenu() throws IOException {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("Choose something please  : ");
        System.out.println("");
        System.out.println("Type 1 to manage your favourite ! ");
        System.out.println("");
        System.out.println("Type 2 to search what weather is like in a  city  ! ");
        System.out.println("");
        System.out.println("Type 3 to see the weather in your favourites   ! ");
        System.out.println("");
        System.out.println("Type 4 to  access the settings menu  ! ");
        System.out.println("");
        System.out.println("Type 5 to close app  ! ");
        System.out.println("");
        System.out.print("Please input  your choice : ");
        Scanner scanner = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        for (;;) {
            switch ( in .next()) {
                case "1":
                    System.out.println("Favorites cities Manager :  ");
                    System.out.println("");
                    getFavouritesCityManager();

                    break;
                case "2":
                    System.out.println("Searching engine  : ");
                    System.out.println("");
                    getQuery();
                    getMenu();

                    break;
                case "3":
                    getFavorite();
                    getMenu();
                    break;

                case "4":
                    getSettings();
                    break;

                case "5":
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please type 1, 2, 3, 4 or 5!");
                    break;
            }
        }
    }

    private static void getFavouritesCityManager() throws IOException {
        System.out.println("Type 1 to add a city in your bookmarks ! ");
        System.out.println("");
        System.out.println("Type 2 to remove a city from your bookmarks  ! ");
        System.out.println("");
        System.out.println("Type 3 to go back to the menu  ! ");
        System.out.println("");
        System.out.print("Please input  your choice : ");
        Scanner scanner = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        for (;;) {
            switch ( in .next()) {

                case "1":
                    addCity();
                    getFavouritesCityManager();
                    break;
                case "2":
                    removeCity();
                    getMenu();

                    break;
                case "3":

                    getMenu();
                    break;

                default:
                    System.out.println("Please enter a number between 1 and 3 !");
                    break;
            }
        }
    }

    private static void removeCity() throws IOException {
        System.out.println("Please enter the city to remove from yours bookmarks : ");
        Scanner removeCity = new Scanner(System.in);

        System.out.println("");
        System.out.print("Your choice : ");
        String city = "";

        city = removeCity.next();
        if (bookMark.ReadFile().contains(city)) {
            bookMark.Delete_Sequence(city);
            System.out.println(city + " has been successfully removed !");

        } else System.out.println(city + " was not in your bookmarks");

    }

    private static void addCity() throws IOException {
        Scanner cityPrompt = new Scanner(System.in);
        System.out.print("Enter the name of the city you want to add to your bookmarks :  ");

        for (;;) {

            String city = "";

            try {
                city = cityPrompt.next();

                if (city.length() > 1) {
                    ReadJSON readJSON = new ReadJSON(city, "fr", "metric", 0);

                    if (!bookMark.ReadFile().contains(city)) {
                        bookMark.AddBookMark(city);
                        System.out.println(city + " was successfully added");
                    } else {
                        System.out.println(city + " is all ready in bookmarks");
                    }

                    break;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Please enter a valid city name ! ");
            }

        }

    }

    private static void getSettings() throws IOException {

        System.out.println("Type 1 to change units  ! ");
        System.out.println("");
        System.out.println("Type 2 to change language ! ");
        System.out.println("");
        System.out.println("Type 3 to go back to the menu  ! ");

        System.out.println("");
        System.out.print("Please input  your choice : ");
        Scanner scanner = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        for (;;) {
            switch ( in .next()) {

                case "1":
                    System.out.println("The unit in which the data is currently expressed is : " + global_units);
                    changeUnits();
                    getMenu();
                    break;
                case "2":
                    System.out.println("The language is currently expressed is : " + global_lang.toUpperCase());
                    changeLang();
                    getMenu();
                    break;
                case "3":

                    getMenu();
                    break;

                default:
                    System.out.println("Please enter a number between 1 and 3 !");
                    break;
            }
        }

    }

    private static void changeUnits() {
        System.out.println("coming soon !");

    }

    private static void changeLang() {
        System.out.println("coming soon !");
    }

    public static void getFavorite() throws IOException {
        BookMark bookMark = new BookMark();

        System.out.println("Weather app ");
        System.out.println("  ");
        System.out.println("Have a look at the weather in your favourite cities : ");
        for (int i = 0; i < bookMark.ReadFile().size() - 1; i++) {
            System.out.println("  ");

            String city = (bookMark.ReadFile().get(i));
            if (city.length() > 1) {
                ReadJSON readJSON = new ReadJSON(city, global_lang, global_units, 0);

                ReadData dataWeathers = new ReadData(readJSON.getCurrentRequestResult(), readJSON.getDaily_REQUESTRequestResult());

                System.out.print("-" + dataWeathers.getName() + ": weather is " + dataWeathers.getWeather().toString().toLowerCase() + " , it's " + dataWeathers.getTemperature() + " degrees celsius , the wind blows at " + (int) dataWeathers.getWindDirection() / 50 + " km/h \n with  an angle of " + dataWeathers.getWindDirection() + "° ,there is " + dataWeathers.getCloudPercent() + " /100 clouds in sky  ," + getStringAboutVisibility(dataWeathers) + "\n humidity is about " + dataWeathers.getHumidity() + " percent ");
                System.out.println(" ");

            }
        }

    }
    public static String getStringAboutVisibility(ReadData dataWeathers) {
        if ((int) dataWeathers.getVisibility() > 750) return "excellent visibility";
        if ((int) dataWeathers.getVisibility() > 500) return "great visibility";
        if ((int) dataWeathers.getVisibility() > 200) return "medium visibility";
        if ((int) dataWeathers.getVisibility() > 50) return "bad visibility";

        return "";
    }

    public static void getQuery() throws IOException {
        Scanner cityPrompt = new Scanner(System.in);
        System.out.print("Enter the name of the city you want to know the weather for  ");
        for (;;) {
            System.out.println("");
            System.out.print("Your choice : ");
            String city = "";

            try {
                city = cityPrompt.next();

                if (city.length() > 1) {

                    ReadJSON readJSON = new ReadJSON(city, "fr", "metric", 0);

                    ReadData dataWeathers = new ReadData(readJSON.getCurrentRequestResult(), readJSON.getDaily_REQUESTRequestResult());

                    System.out.print("At " + city + ", weather is " + dataWeathers.getWeather().toString().toLowerCase() + " , it's " + dataWeathers.getTemperature() + " degrees celsius , the wind blows at " + (int) dataWeathers.getWindDirection() / 50 + " km/h \n with  an angle of " + dataWeathers.getWindDirection() + "° ,there is " + dataWeathers.getCloudPercent() + " /100 clouds in sky  ," + getStringAboutVisibility(dataWeathers) + "\n humidity is about " + dataWeathers.getHumidity() + " percent. ");
                    System.out.println(" ");
                    break;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Please enter a valid city name ! ");
            }

        }
    }

}