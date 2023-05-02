package app.appmeteo.controller;

import app.appmeteo.model.BookMark;
import app.appmeteo.model.ReadData;
import app.appmeteo.model.ReadJSON;
import app.appmeteo.model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.json.JSONException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AppMeteoController implements Initializable {

    public Pane Main_container;
    public AnchorPane BookMarks_Container;

    public Image IMG_MODE_day;
    public Image IMG_MODE_night;
    public ImageView Image_mode;
    private int currentMode = 0;

    public javafx.scene.control.TextField Input_text;
    public javafx.scene.control.TextField Input_BookMarks;
    public javafx.scene.control.Label Lb_Weather_Desc;
    public javafx.scene.control.Label Lb_City_name;
    public javafx.scene.control.Label Lb_wind;
    public javafx.scene.control.Label Lb_Temperature;
    public javafx.scene.control.Label timerClock;
    public DatePicker Date_picker;
    public javafx.scene.control.Button deleteSelectedCity;
    @FXML
    public TableView < BookMarkController > Table_BookMark;
    @FXML
    public ObservableList < BookMarkController > tableRowsInformations = FXCollections.observableArrayList();

    public AnchorPane imgSunrise;
    public AnchorPane imgSun;
    public AnchorPane imgSunset;
    public AnchorPane imgMoon;

    private boolean isQuestHadToBeSent = false;

    private int IntervalsDays;
    private static String cityQuest;

    private ArrayList < String > ArraysOfQuest;


    public AppMeteoController() {
        this.ArraysOfQuest = new ArrayList < > ();
        ArraysOfQuest.add("Marseille");
        ArraysOfQuest.add("0");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalDateTime now = LocalDateTime.now();

        int hour = now.getHour();

        if(hour > 20 || hour < 6){
            setNightMode();
        }




        Date date = Calendar.getInstance().getTime();


        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        Date_picker.setPromptText(today);

        /*Default city and date are Marseille and today */
        ReadJSON readJSON = null;
        try {

            User us = new User();
            readJSON = new ReadJSON("Marseille", us.getLang(), us.getUnits(), 0);
            ArraysOfQuest.add("Marseille");
            ArraysOfQuest.add("0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert readJSON != null;
        ReadData readData = new ReadData(readJSON.getCurrentRequestResult(), readJSON.getDaily_REQUESTRequestResult());

        Lb_wind.setText(" : " + readData.getWindSpeed().toString());
        Lb_City_name.setText(" : " + readData.getName().toString());
        Lb_Temperature.setText(" : " + readData.getTemperature());
        Lb_Weather_Desc.setText(" : " + readData.getWeatherDesc().toString().substring(0, 1).toUpperCase() + readData.getWeatherDesc().toString().substring(1));

        try {
            manageBookMarks();
        } catch (IOException e) {
            e.printStackTrace();
        }

        changeImageByHour();
        launchTimerClock();
    }

    public void manageBookMarks() throws IOException {

        Table_BookMark.getItems().clear();
        Table_BookMark.getColumns().clear();
        try {


            User user = new User();

            TableColumn cityColumn = new TableColumn(stringToUTF8(user.getTRAD_City()));
            cityColumn.setCellValueFactory(new PropertyValueFactory < > ("city"));
            cityColumn.setMinWidth(90);

            TableColumn temperatureColumn = new TableColumn(stringToUTF8(user.getTRAD_Temperature()));
            temperatureColumn.setCellValueFactory(new PropertyValueFactory < > ("temperature"));
            temperatureColumn.setMinWidth(110);

            TableColumn weatherColumn = new TableColumn(stringToUTF8(user.getTRAD_Weather()));
            weatherColumn.setCellValueFactory(new PropertyValueFactory < > ("weather"));
            weatherColumn.setMinWidth(157);

            Table_BookMark.getColumns().addAll(cityColumn, temperatureColumn, weatherColumn);


            try {

                BookMark bookMark = new BookMark();

                for (String city:
                        bookMark.ReadFile()) {

                    BookMarkController bookMarkController = new BookMarkController(city);
                    tableRowsInformations.add(bookMarkController);

                }

                Table_BookMark.getItems().addAll(this.tableRowsInformations);

            } catch (IOException e) {
                e.printStackTrace();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void launchTimerClock() {
        final DateFormat format = DateFormat.getDateTimeInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        final Calendar cal = Calendar.getInstance();
                        timerClock.setText(format.format(cal.getTime()));
                    }
                }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void changeImageByHour() {
        LocalDateTime now = LocalDateTime.now();

        int hour = now.getHour();

        if(now.getMinute() == 0 && now.getSecond() == 0 && (hour > 6 || hour > 20) ){
            setNightMode();

        }

        if(now.getMinute() == 0 && now.getSecond() == 0 &&hour > 20 || hour < 6 ){
            setNightMode();

        }

        if ((20 <= hour && hour <= 24) || (0 <= hour && hour < 6)) {
            this.setImageSun(0, 0, 0, 1);
        }

        if (6 <= hour && hour < 12) {
            this.setImageSun(1, 0.2, 0.2, 0);
        }

        if (12 <= hour && hour <= 16) {

            this.setImageSun(0.2, 1, 0.2, 0);

        }

        if (16 < hour && hour < 20) {

            this.setImageSun(0.2, 0.2, 1, 0);
        }

    }

    public void setImageSun(double sunrise, double sun, double sunset, double moon) {
        this.imgSunrise.setOpacity(sunrise);
        this.imgSun.setOpacity(sun);
        this.imgSunset.setOpacity(sunset);
        this.imgMoon.setOpacity(moon);
    }

    public void AddBookMarks() throws IOException {
        try {
            User user = new User();

            String cityFromBookMarkInput = Input_BookMarks.getText().trim().toLowerCase();

            cityFromBookMarkInput = cityFromBookMarkInput.substring(0, 1).toUpperCase() + cityFromBookMarkInput.substring(1);

            BookMark bookMark = new BookMark();

            if (cityFromBookMarkInput.length() == 0 || cityFromBookMarkInput == "" || cityFromBookMarkInput == null) {
                JOptionPane.showMessageDialog(null, user.getTRAD_PleaseEnterAValidCity(), user.getTRAD_Error(), 1);
            } else {

                if (bookMark.ReadFile().contains(cityFromBookMarkInput)) {
                    JOptionPane.showMessageDialog(null, cityFromBookMarkInput + user.getTRAD_IsAllReadyIn(), user.getTRAD_Error(), 1);
                    Input_BookMarks.setText("");
                }

                try {
                    ReadJSON readJSON = new ReadJSON(cityFromBookMarkInput, "en", "metric", 0);
                    ReadData readData = new ReadData(readJSON.getCurrentRequestResult(), readJSON.getDaily_REQUESTRequestResult());
                    if (!bookMark.ReadFile().contains(cityFromBookMarkInput)) {
                        bookMark.AddBookMark(cityFromBookMarkInput);

                        tableRowsInformations.add(new BookMarkController(cityFromBookMarkInput));
                        Table_BookMark.getItems().removeAll(this.tableRowsInformations);
                        Table_BookMark.getItems().addAll(this.tableRowsInformations);
                    }

                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null, user.getTRAD_PleaseEnterAValidCity(), user.getTRAD_Error(), 1);
                    Input_BookMarks.setText("");
                }
            }

            Input_BookMarks.setText("");
        } catch (Exception e) {
        }
    }

    public void deleteRowFromTable(javafx.event.ActionEvent actionEvent) throws IOException {

        ArrayList < BookMarkController > arrayForSelectedItems = new ArrayList < > (Table_BookMark.getSelectionModel().getSelectedItems());
        ObservableList < BookMarkController > tableRowInformationsTemp = tableRowsInformations;
        for (BookMarkController data: arrayForSelectedItems) {
            for (BookMarkController objectOfBookMark:
                    tableRowsInformations) {
                if (objectOfBookMark.getCity().equals(data.getCity())) {
                    tableRowInformationsTemp.remove(objectOfBookMark);
                }
            }
            new BookMark().Delete_Sequence(data.getCity());
        }

        this.tableRowsInformations.removeAll(tableRowsInformations);

        this.tableRowsInformations = tableRowInformationsTemp;

        Table_BookMark.getItems().removeAll(Table_BookMark.getSelectionModel().getSelectedItems());
    }

    public void ChangeMode() {



        if (currentMode == 0 ) {
            setNightMode();
            currentMode = 1 ;

        } else {
            setDayMode();
            currentMode = 0 ;

        }

    }

    public void setNightMode() {
        BookMarks_Container.setStyle("-fx-background-color : #302E30FF;");
        Table_BookMark.setStyle("-fx-background-color : #302E30FF;" );
        Input_BookMarks.setStyle("-fx-border-color: #A2D6CF;");
        Input_text.setStyle(
                "-fx-border-color: #A2D6CF;");
        ;
        Date_picker.setStyle("-fx-color : #302E30FF;" +
                "-fx-border-color: #A2D6CF;");
        Image_mode.setImage(null);
        Image_mode.setImage(IMG_MODE_night);

        Main_container.setStyle("");
        Main_container.setStyle("-fx-background-color : #302E30FF");


    }

    public void setDayMode() {
        Image_mode.setImage(IMG_MODE_day);


        BackgroundFill BackGroundNight = new BackgroundFill(Color.WHITE, null, null);
        Background byNight = new Background(BackGroundNight);
        Main_container.setBackground(byNight);
        BookMarks_Container.setStyle("-fx-background-color : WHITE;");
        Table_BookMark.setStyle("-fx-border-color: #A2D6CF;");
        Input_text.setStyle("-fx-border-color: #A2D6CF;");
        Input_BookMarks.setStyle("-fx-border-color: #A2D6CF;");
        Date_picker.setStyle("-fx-border-color: #A2D6CF;");
        BackgroundFill b = new BackgroundFill(Color.WHITE,null,null);
        Date_picker.setBackground(new Background(b));

    }

    public void changeLang() throws Exception {

        LangController langController = new LangController();
        langController.show();

        isQuestHadToBeSent = true;

    }

    public void updateRequestResult() throws Exception, IOException {
        this.IntervalsDays = 0;

        if (Date_picker != null) if (Date_picker.getValue() != null) {

            this.IntervalsDays = Period.between((LocalDate) Date_picker.getChronology().dateNow(), Date_picker.getValue()).getDays();

        }

        ArraysOfQuest.add(1, String.valueOf(IntervalsDays));


        try {
            User us = new User();
            if (!Input_text.getText().equals("")) ArraysOfQuest.set(0, Input_text.getText());
            ReadJSON readJSON = new ReadJSON(ArraysOfQuest.get(0),us.getLang() ,us.getUnits(),Integer.parseInt(ArraysOfQuest.get(1)));
            ReadData readData = new ReadData(readJSON.getCurrentRequestResult(),readJSON.getDaily_REQUESTRequestResult());

            MakeQuest(ArraysOfQuest.get(0), Integer.parseInt(ArraysOfQuest.get(1)));
            //this.tableRowsInformations.removeAll(tableRowsInformations);
            //manageBookMarks();

        }
        catch (NullPointerException E){

        }
        catch (FileNotFoundException e) {
        
        }

    }



    @FXML
    public void MakeQuest() throws Exception {

        this.IntervalsDays = 0;

        if (Date_picker.getValue() != null) {

            this.IntervalsDays = Period.between((LocalDate) Date_picker.getChronology().dateNow(), Date_picker.getValue()).getDays();

        }


        cityQuest = "Marseille";
        if(Input_text.getText().length() > 0){

            cityQuest = Input_text.getText().toString();}

        MakeQuest(cityQuest, IntervalsDays);

    }

    public void MakeQuest(String City, int Days) throws Exception {

        User us = new User();

        try {
            ReadJSON readJSON = new ReadJSON(City, us.getLang(), us.getUnits(), Days);
            ReadData readData = new ReadData(readJSON.getCurrentRequestResult(), readJSON.getDaily_REQUESTRequestResult());

            Lb_wind.setText(" : " + readData.getWindSpeed().toString());
            Lb_City_name.setText(" : " + readData.getName().toString());
            Lb_Temperature.setText(" : " + readData.getTemperature());
            Lb_Weather_Desc.setText(" : " + readData.getWeatherDesc().toString().substring(0, 1).toUpperCase() + readData.getWeatherDesc().toString().substring(1));

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, us.getTRAD_PleaseEnterAValidCity(), us.getTRAD_Error(), 1);
        } catch (JSONException e) {
            JOptionPane.showMessageDialog(null, us.getTRAD_MaxSevenDays(), us.getTRAD_Error(), 1);
        } catch (NullPointerException e) {

        }catch (IOException e) {

        }

    }

    public void updateText() throws Exception {

        try {

            this.tableRowsInformations.removeAll(tableRowsInformations);

            User user = new User();

            Input_text.setPromptText(user.getTRAD_Enter_your_city());
            Input_BookMarks.setPromptText(user.getTRAD_AddFavoriteCity());
            deleteSelectedCity.setText(user.getTRAD_DeleteSelectedCity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String City = "";
        if(Input_text.getText() == null || Input_text.getText() == "")City = "Marseille";
        else City = Input_text.getText();


        if (isQuestHadToBeSent) {
            MakeQuest(City, IntervalsDays);

            manageBookMarks();
        }

        isQuestHadToBeSent = false ;
    }

    public void MakeQuestWithEnter(KeyEvent keyEvent) throws Exception {
        if(keyEvent.getCode() == KeyCode.ENTER){
            MakeQuest();
        }

    }

    public void AddBookMarksWithEnter(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER){
            AddBookMarks();
        }
    }

    public void About(MouseEvent mouseEvent) throws Exception {
        User us = new User();


        JOptionPane.showMessageDialog(null,us.getTRAD_ThisApp() ,us.getTRAD_About(),1);
    }

    private String stringToUTF8(String stringToChange) throws UnsupportedEncodingException {
        byte ptext[] = stringToChange.getBytes("ISO-8859-1");
        return new String(ptext, "UTF-8");
    }
}
