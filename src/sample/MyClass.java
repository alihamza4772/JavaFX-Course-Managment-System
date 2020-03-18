package sample;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyClass extends Application implements Initializable{
    @FXML
    private Text NAME;
    private URL url = null;
    private ResourceBundle resourceBundle = null;
    @FXML
    private TableView<EnrollCourse> Home_table;
boolean deletecheck = false;
    @FXML
    private TableColumn<EnrollCourse, String> subjid, subjname, subjgrade;
    private ObservableList<EnrollCourse> tableData;

    public MyClass()
    {

    }

    @Override
    public void start(Stage primarystage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/MyClass.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        primarystage.setScene(new Scene(root1));
        primarystage.setResizable(false);
        primarystage.initStyle(StageStyle.UNIFIED);
        primarystage.centerOnScreen();
    }

    public void main(String[] args) {

    }

    public void ok ()
    {
        NAME.setText(Login.name);
        tableData = FXCollections.observableArrayList();
        try {
            tableData = DBUtility.getAllData(Login.ssn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        subjid.setCellValueFactory(new PropertyValueFactory<EnrollCourse, String>("id"));
        subjname.setCellValueFactory(new PropertyValueFactory<EnrollCourse, String>("name"));
        subjgrade.setCellValueFactory(new PropertyValueFactory<EnrollCourse, String>("grade"));
        Home_table.setItems(null);
        Home_table.setItems(tableData); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       ok();
    }

    @FXML
    void ADD(ActionEvent event) throws Exception{
        Stage stage = new Stage();
       Register register = new Register();
        ((Node)event.getSource()).getScene().getWindow().hide();
        register.start(stage);
        stage.show();
    }

    @FXML
    void DELETE(ActionEvent event) throws Exception
    {
        if (deletecheck == true) {
            String name = Home_table.getSelectionModel().getSelectedItem().getName();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete the Entire Row.!");
            alert.setContentText("Are you ok with this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String check = DBUtility.removeEnroll(name,Login.ssn);
                if (check == name) {
                    Home_table.getItems().removeAll(Home_table.getSelectionModel().getSelectedItem());
                    Alert_Success("Data Deleted Successfuly.!");
                    ok();
                    deletecheck = false;
                }
            } else {
                return;
            }
        } else if (deletecheck == false) {
            Alert_Error("If You Want to delete Any Data Kindly Select that Row.!");
        }
    }

    @FXML
    void EXIT(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    @FXML
    void table_click(MouseEvent event) {
     deletecheck=true;
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
