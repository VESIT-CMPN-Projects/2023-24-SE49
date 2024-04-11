package com.example.miniproject_hotel_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application
{
    private  double x=0;
    private  double y=0;

    @Override
    public void start(Stage stage) throws Exception
    {
       Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
       Scene scene =new Scene(root);
       root.setOnMousePressed((MouseEvent event) ->
       {
           x=event.getSceneX();
           y=event.getSceneX();
       });

       root.setOnMouseDragged((MouseEvent event) ->
       {
           stage.setX(event.getSceneX() -x);
           stage.setY(event.getSceneY() -y);

           stage.setOpacity(.4);
       });

       root.setOnMouseReleased((MouseEvent event) ->
       {
           stage.setOpacity(.8);
       });


       stage.initStyle(StageStyle.TRANSPARENT);
       stage.setScene(scene);
       stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
