package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class Register extends Application implements Initializable {
    @FXML
    private ComboBox coursebox;

    public Register()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/Register.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        primarystage.setScene(new Scene(root1));
        primarystage.setResizable(false);
        primarystage.initStyle(StageStyle.UNIFIED);
        primarystage.centerOnScreen();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sectionlist = null;
        try {
            sectionlist = DBUtility.getcourses(Login.ssn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        coursebox.setItems(sectionlist);
    }

    @FXML
    void AddCourse(ActionEvent event) {
        if ( coursebox.getSelectionModel().isEmpty() == true || coursebox.getSelectionModel().isEmpty() == true) {
            Alert_Error("Choose Values from the box!");
        } else {
            int check = 0;
            try {
                check = DBUtility.addcourse(coursebox.getSelectionModel().getSelectedItem().toString(), Login.ssn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (check != 0) {
                Alert_Success("Data Added Successfully.!");
                Stage stage = new Stage();
                MyClass myClass= new MyClass();
                ((Node)event.getSource()).getScene().getWindow().hide();
                try {
                    myClass.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stage.show();

            } else {
                Alert_Error("Data Didn't Added Successfully.!");
            }
        }
    }
    public void Alert_Error(String text)                        // function for error alert
    {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Error Dialog");
        alert1.setHeaderText(null);
        alert1.setContentText(text);
        alert1.show();
    }

    public void Alert_Success(String msg)                     // function for Success alert
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
