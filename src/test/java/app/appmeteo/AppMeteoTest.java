package app.appmeteo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.service.query.NodeQuery;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AppMeteoTest {

    @Start
    public void start(Stage primaryStage) throws Exception {
        Parent roots = FXMLLoader.load(getClass().getResource("/app/appmeteo/view/AppMeteoDay.fxml"));
        primaryStage.setScene(new Scene(roots));
        primaryStage.show();
    }

    @Test
    public void testRobotTestTitle (FxRobot robot){
        Labeled labeled = robot.lookup("#Lbl_Title").queryLabeled();
        Assertions.assertThat(labeled).hasText("MyAppMeteo");
    }


    @Test
    public void testRobotCity(FxRobot robot ) {
        TextField textField=robot.lookup("#Input_text").query();
        Labeled label=robot.lookup("#Lb_City_name").queryLabeled();
        robot.clickOn("#Input_text");
        robot.write("Londres");
        robot.clickOn("#Bt_Search");
        Assertions.assertThat(label).hasText(" : Londres");

    }
    @Test
    public void testRobotFavorites(FxRobot robot){
        TableView tableView=robot.lookup("#Table_BookMark").queryTableView();
        robot.clickOn("#Input_BookMarks");
        robot.write("Prague");
        robot.clickOn("#Bt_Search2");
        Assertions.assertThat(tableView).hasTableCell("Prague");

    }
    @Test
    public void testRobotdeleteSelectedCity(FxRobot robot){
        Button button=robot.lookup("#deleteSelectedCity").queryButton();
        Assertions.assertThat(button).hasText("Supprimer une ville selectionnée");
    }


    @Test
    public  void testRobotBt_Display_mode(FxRobot robot){
        robot.clickOn("#Bt_Display_mode");
    }



}