package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Login extends Application {
    @FXML
    private TextField SSN_TEXT;
    @FXML
    private Text ERROR_TEXT;
    public static String ssn=null;
    public static String name=null;
    Parent root;
    @Override
    public void start(Stage primaryStage) throws Exception {
        DBUtility.connect();
        root = FXMLLoader.load(getClass().getResource("/sample/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 438, 255));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }

    public void stop() throws Exception {
        DBUtility.disconnect();
    }

    @FXML
    void LoginClick(MouseEvent event) {
        if (SSN_TEXT.getText().isEmpty()) {
            Alert_Error("Enter SSN For Login.!");
        } else {
            ResultSet str = null;
            try {
                str = DBUtility.getSSN(SSN_TEXT.getText());
                if (str.next()) {
                    name = str.getString(2) + " " + str.getString(3);
                    ssn = str.getString(1);
                    Stage stage = new Stage();
                    MyClass myClass = new MyClass();
                    ((Node) event.getSource()).getScene().getWindow().hide();
                    myClass.start(stage);
                    stage.show();

                } else
                    ERROR_TEXT.setText("ERROR : No Student Found");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

public void ExitClick()
{
    Platform.exit();
    System.exit(0);
}

    public void Alert_Error(String text)                        // function for error alert
    {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Error Dialog");
        alert1.setHeaderText(null);
        alert1.setContentText(text);
        alert1.show();
    }
}

