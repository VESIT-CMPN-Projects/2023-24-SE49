package com.example.miniproject_hotel_management_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class Logincontroller implements Initializable
{

    @FXML
    private Button close;

    @FXML
    private Button loginbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private StackPane stack_form;

    @FXML
    private TextField username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet  result;

    private  double x=0;
    private double y=0;



    public  void login()
    {
        String  user= username.getText();
        String pass=password.getText();
        String sql = "SELECT * FROM admin WHERE  username= ? and password= ?";

        connect =database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,user);
            prepare.setString(2,pass);
            result=prepare.executeQuery();

            Alert alert;

            if (user.isEmpty() || pass.isEmpty())
            {
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Messeging");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else {

                if (result.next())
                {
                    getdata.username=username.getText();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    loginbtn.getScene().getWindow().hide();


                    Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                   root.setOnMousePressed((MouseEvent event) ->{
                       x=event.getSceneX();
                       y=event.getSceneX();
                   });

                   root.setOnMouseDragged((MouseEvent event) ->
                   {
                       stage.setX(event.getSceneX() -x);
                       stage.setY(event.getSceneY() -y);
                   });
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        }catch (Exception e) {e.printStackTrace();}


    }
    public  void exit()
    {
        System.exit(0);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
}
