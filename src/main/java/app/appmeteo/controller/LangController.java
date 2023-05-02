package app.appmeteo.controller;

import app.appmeteo.model.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LangController implements Initializable {


    public Button Standard;
    public Button Imperial;
    public Button Metric;
    private Stage s;

    public Button RO;
    public Button RU;
    public Button NL;
    public Button FR;
    public Button IT;
    public Button HU;
    public Button ES;
    public Button EN;
    public Button PT;
    public Button DE;
    private ArrayList<Button> ButtonsLang ;
    private ArrayList<Button> ButtonsUnits ;

    public LangController() throws Exception {
        this.s = new Stage();






    }

    AppMeteoController appMeteoController = new AppMeteoController();
    User user = new User();

    public void changeLang(String lang) throws Exception {
        String units = user.getUnits();
        user.setUserSettings(lang, units);
        Imperial.setText(user.getTRAD_Imperial());
        Standard.setText(user.getTRAD_Standard());
        Metric.setText(user.getTRAD_Metric());


        appMeteoController.updateRequestResult();

    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/app/appmeteo/view/settingsMenu.fxml"));
        s.setTitle(user.getTRAD_Settings());

        s.setScene(new Scene(root));
        s.setResizable(false);
        s.show();
    }
    public void changeUnit(String unit) throws Exception {
        String lang = user.getLang();
        user.setUserSettings(lang, unit);
        appMeteoController.updateRequestResult();
        Imperial.setText(user.getTRAD_Imperial());
        Standard.setText(user.getTRAD_Standard());
        Metric.setText(user.getTRAD_Metric());

    }

    public void SetRu() throws Exception {
        changeLang("ru");
        setButtonChecked(RU);

    }

    public void SetRo() throws Exception {
        changeLang("ro");
        setButtonChecked(RO);


    }

    public void SetFr() throws Exception {
        changeLang("fr");
        setButtonChecked(FR);


    }
    public void SetDe() throws Exception {
        changeLang("de");
        setButtonChecked(DE);


    }
    public void SetIt() throws Exception {
        changeLang("it");
        setButtonChecked(IT);


    }
    public void SetHu() throws Exception {

        changeLang("hu");
        setButtonChecked(HU);


    }
    public void SetNl() throws Exception {
        changeLang("nl");
        setButtonChecked(NL);



    }
    public void SetPt() throws Exception {
        changeLang("pt");
        setButtonChecked(PT);


    }
    public void SetEs() throws Exception {
        changeLang("es");
        setButtonChecked(ES);


    }
    public void SetGb() throws Exception {
        changeLang("gb");
        setButtonChecked(EN);


    }

    public void setImp() throws Exception {
        setButtonChecked(Imperial);

        changeUnit("imperial");


    }

    public void setStd() throws Exception {
        setButtonChecked(Standard);

        changeUnit("standard");


    }

    public void setMet() throws Exception {
        setButtonChecked(Metric);

        changeUnit("metric");

    }


    public void setButtonChecked(Button button) {



        Boolean isButtonIsALangButton = ButtonsLang.contains(button);


        button.setStyle("-fx-background-color : #3A3E8F;");

        if (isButtonIsALangButton) {
            for (Button b : ButtonsLang) {
                if (b != button) {
                    b.setStyle("-fx-background-color : #A2D6CF;");


                }

            }

        } else {
            for (Button b : ButtonsUnits) {
                if (b != button) {
                    b.setStyle("-fx-background-color : #A2D6CF;");

                }

            }

        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Imperial.setText(user.getTRAD_Imperial());
        Standard.setText(user.getTRAD_Standard());
        Metric.setText(user.getTRAD_Metric());
        ButtonsLang = new ArrayList();
        ButtonsLang.add(DE);
        ButtonsLang.add(EN);
        ButtonsLang.add(FR);
        ButtonsLang.add(RO);
        ButtonsLang.add(RU);
        ButtonsLang.add(IT);
        ButtonsLang.add(NL);
        ButtonsLang.add(ES);
        ButtonsLang.add(HU);
        ButtonsLang.add(PT);

        ButtonsUnits = new ArrayList();

        ButtonsUnits.add(Imperial);
        ButtonsUnits.add(Metric);
        ButtonsUnits.add(Standard);

    }
}