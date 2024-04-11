package com.example.miniproject_hotel_management_system;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class staffcontroller implements Initializable
{

    @FXML
    private Button Addstaffmember;

    @FXML
    private Button Close;

    @FXML
    private Button Minimize;

    @FXML
    private Button Staffmemberlist;

    @FXML
    private Button Workallocation;

    @FXML
    private AnchorPane MAIN_FORM;

    public void addstaffmember()
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("addstaffmember.fxml"));
            Stage stage =new Stage();
            Scene scene =new Scene(root);

            stage.setMinHeight(440);
            stage.setMinWidth(480);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void staffmemberlist()
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("staffmemberlist.fxml"));
            Stage stage =new Stage();
            Scene scene =new Scene(root);

            stage.setMinHeight(900);
            stage.setMinWidth(422);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public  void close()
    {
        System.exit(0);
    }

    public void MINIMIZE()
    {
        Stage stage =(Stage) MAIN_FORM.getScene().getWindow();
        stage.setIconified(true);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
