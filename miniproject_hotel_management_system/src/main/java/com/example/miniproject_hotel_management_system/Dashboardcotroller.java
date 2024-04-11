package com.example.miniproject_hotel_management_system;

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



public class Dashboardcotroller implements Initializable
{
    @FXML
    private Button avialablerooms_logout_btn;


    @FXML
    private Button avialablerooms_addbtn;
    @FXML
    private Label Username;

    @FXML
    private Button avialablerooms_checkinbtn;

    @FXML
    private Button avialablerooms_clearbtn;

    @FXML
    private TableColumn<Roomdata, String> avialablerooms_col_price;

    @FXML
    private TableColumn<Roomdata, String> avialablerooms_col_roomnumber;

    @FXML
    private TableColumn<Roomdata, String> avialablerooms_col_roomtype;

    @FXML
    private TableColumn<Roomdata, String> avialablerooms_col_status;

    @FXML
    private Button avialablerooms_deletebtn;

    @FXML
    private Label dashboard_income;

    @FXML
    private Button staff_btn;

    @FXML
    private TextField avialablerooms_price;

    @FXML
    private TextField avialablerooms_roomnumber;

    @FXML
    private ComboBox<?> avialablerooms_roomtype;

    @FXML
    private TextField avialablerooms_search;

    @FXML
    private ComboBox<?> avialablerooms_status;

    @FXML
    private TableView<Roomdata> avialablerooms_tableview;

    @FXML
    private Button avialablerooms_updatebtn;

    @FXML
    private Button closebtn;

    @FXML
    private TableColumn<customerdata, String> customers_checkin;

    @FXML
    private TableColumn<customerdata, String> customers_checkout;

    @FXML
    private TableColumn<?, ?> customers_customernumber;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableColumn<customerdata, String> customers_name;

    @FXML
    private TableColumn<customerdata, String> customers_phonenumber;

    @FXML
    private TextField customers_search;

    @FXML
    private TableView<customerdata> customers_tableview;

    @FXML
    private TableColumn<customerdata, String> customers_totalpayment;

    @FXML
    private AreaChart<?, ?> dashboard_areachart;

    @FXML
    private Label dashboard_booktoday;


    @FXML
    private Button customers_btn;


    @FXML
    private AnchorPane availableroom_form;


    @FXML
    private Button availablerooms_btn;


    @FXML
    private Button dashboard_btn;




    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalincome;

    @FXML
    private Button minimizebtn;

    @FXML
    private AnchorPane main_form;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public ObservableList<Roomdata> availableroomslistdata()
    {
        ObservableList<Roomdata> listdata= FXCollections.observableArrayList();

        String sql ="SELECT * FROM room";

        connect =database.connectDb();

        try {

            Roomdata roomd;
            prepare=connect.prepareStatement(sql);
            result= prepare.executeQuery();

            while (result.next())
            {
                roomd=new Roomdata(result.getInt("roomnumber"),
                        result.getString("type"),
                        result.getString("status"),
                        result.getDouble("price"));

                listdata.add(roomd);
            }
        }catch (Exception e){e.printStackTrace();}
        return listdata;
    }

    private ObservableList<Roomdata> roomdatalist;
    public void availableroomsshowdata()
    {
        roomdatalist=availableroomslistdata();
        avialablerooms_col_roomnumber.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        avialablerooms_col_roomtype.setCellValueFactory(new PropertyValueFactory<>("type"));
        avialablerooms_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        avialablerooms_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        avialablerooms_tableview.setItems(roomdatalist);


    }

    public void availableroomsSelected()
    {
        Roomdata roood =avialablerooms_tableview.getSelectionModel().getSelectedItem();
        int num =avialablerooms_tableview.getSelectionModel().getFocusedIndex();

        if((num -1) < -1)
        {
            return;
        }

        avialablerooms_roomnumber.setText(String.valueOf(roood.getRoomnumber()));
        avialablerooms_price.setText(String.valueOf(roood.getPrice()));

    }

    public void availableroomsupdate() {
        String type1 = (String) avialablerooms_roomtype.getSelectionModel().getSelectedItem();
        String status1 = (String) avialablerooms_status.getSelectionModel().getSelectedItem();
        String price1 = avialablerooms_price.getText();
        String roomNum = avialablerooms_roomnumber.getText();

        String sql = "UPDATE room SET type = '"
                + type1 + "', status = '" + status1 + "', price ='" + price1
                + "' WHERE roomNumber = '" + roomNum + "'";



        connect = database.connectDb();

        try {

            Alert alert;

            if (type1 == null || status1 == null || price1 == null || roomNum == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the date first");
                alert.showAndWait();


            }
            else
            {
                prepare = connect.prepareStatement(sql);
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                prepare.executeUpdate(sql);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void availableroomssearch()
    {
        FilteredList<Roomdata> filter=new FilteredList<>(roomdatalist,e ->true);
        avialablerooms_search.textProperty().addListener((Observable, oldvalue, newvalue) ->
        {
            filter.setPredicate(predicateroomdata ->{
               if(newvalue == null){
                   return  true;
               }

               String searchkey =newvalue.toLowerCase();

               if (predicateroomdata.getRoomnumber().toString().contains(searchkey)){
                   return true;
               } else if (predicateroomdata.getroomtype().toLowerCase().contains(searchkey)){
                   return true;
               } else if(predicateroomdata.getPrice().toString().contains(searchkey)){
                   return true;
               } else if (predicateroomdata.getStatus().toString().contains(searchkey)){
                   return true;
               }else return false;
            });
        });

        SortedList<Roomdata> sortedList =new SortedList<>(filter);
        sortedList.comparatorProperty().bind(avialablerooms_tableview.comparatorProperty());
        avialablerooms_tableview.setItems(sortedList);

    }



    public  void dashboardAdd()
    {
        String sql="INSERT INTO room(roomnumber,type,status,price) VALUES(?,?,?,?)";
        connect=database.connectDb();
        try
        {

            String roomnumber = avialablerooms_roomnumber.getText();
            String type = (String) avialablerooms_roomtype.getSelectionModel().getSelectedItem();
            String status = (String) avialablerooms_status.getSelectionModel().getSelectedItem();
            String price = avialablerooms_price.getText();

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, roomnumber);
            prepare.setString(2, type);
            prepare.setString(3, status);
            prepare.setString(4, price);



            Alert alert;

            if (roomnumber == null || type == null || status == null || price == null)
            {
                alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank feilds");
                alert.showAndWait();
            }
            else
            {
                String check ="Select roomnumber  FROM room WHERE roomnumber ='"+roomnumber+"'";

                prepare=connect.prepareStatement(check);
                result= prepare.executeQuery();

                if(result.next())
                {
                    alert=new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room#"+roomnumber+"Was Already Exists!");
                    alert.showAndWait();
                }
                else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, roomnumber);
                    prepare.setString(2, type);
                    prepare.setString(3, status);
                    prepare.setString(4, price);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succefully Added!");
                    alert.showAndWait();


                    availableroomsshowdata();
                    availableroomsclear();
                }
            }

        }catch (Exception e){e.printStackTrace();}
    }


    public  void  availableroomsdelete()
    {
        String type1= (String) avialablerooms_roomtype.getSelectionModel().getSelectedItem();
        String status1 =(String) avialablerooms_status.getSelectionModel().getSelectedItem();
        String price1 =avialablerooms_price.getText();
        String roomNum=avialablerooms_roomnumber.getText();

        String deleteData  ="DELETE FROM room WHERE roomnumber='"+roomNum+"'";

        connect =database.connectDb();

        try {
            Alert alert;
            if (type1 == null || status1 == null || price1 == null || roomNum == null)
            {
                alert =new Alert(AlertType.ERROR);
                alert.setTitle("Error Messege");
                alert.setHeaderText(null);
                alert.setContentText("Please select the data first");
                alert.showAndWait();
            }else
            {


                alert =new Alert(AlertType.CONFIRMATION);
                alert.setTitle("conformation Messege");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Romm#"+roomNum+"?");
                alert.showAndWait();

                Optional<ButtonType> option =alert.showAndWait();

                if (option.get().equals(ButtonType.OK))
                {
                    statement =connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert =new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information messege");
                    alert.setHeaderText(null);
                    alert.setContentText("Succefully Deleted!");
                    alert.showAndWait();

                    availableroomsshowdata();

                    availableroomsclear();



                }
                else
                {
                    return;
                }
            }
            statement =connect.createStatement();

        }catch (Exception e){e.printStackTrace();}
    }


    public void availableroomsclear()
    {
        avialablerooms_roomnumber.setText("");
        avialablerooms_roomtype.getSelectionModel().clearSelection();
        avialablerooms_status.getSelectionModel().clearSelection();
        avialablerooms_price.setText("");
    }

    private String type[]={"Single room","Double room","Triplyte room","Quad room","King room"};

    public  void  availableroomsroomtype()
    {
        List<String> listData =new ArrayList<>();

        for(String data: type)
        {
            listData.add(data);

        }

        ObservableList list = FXCollections.observableArrayList(listData);
        avialablerooms_roomtype.setItems(list);
    }


    private String status[]={"Available","Not Available","Occupied"};


    public  void  availableroomstatus()
    {
        List<String> listData =new ArrayList<>();

        for(String data:status)
        {
            listData.add(data);

        }

        ObservableList list = FXCollections.observableArrayList(listData);
        avialablerooms_status.setItems(list);
    }

    public ObservableList<customerdata> customerlistdata()
    {
        ObservableList<customerdata> listdata =FXCollections.observableArrayList();

        String sql="SELECT * FROM customer";

        connect =database.connectDb();
        try{
            prepare =connect.prepareStatement(sql);
            result =prepare.executeQuery();

            customerdata custd;

            while(result.next())
            {
                custd=new customerdata(result.getInt("customer_id"),
                        result.getString("name"),
                        result.getString("phonenumber"),
                        result.getDouble("total"),
                        result.getDate("checkin"),
                        result.getDate("checkout"));

                listdata.add(custd);

            }
        }catch (Exception e){e.printStackTrace();}
        return listdata;
    }

    private ObservableList<customerdata> listcustomerdata;
    public void customershowdata()
    {
        listcustomerdata=customerlistdata();

        customers_customernumber.setCellValueFactory(new PropertyValueFactory<>("customernum"));
        customers_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        customers_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        customers_totalpayment.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_checkin.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        customers_checkout.setCellValueFactory(new PropertyValueFactory<>("checkout"));

        customers_tableview.setItems(listcustomerdata);
    }

    public void customersearch() {
        FilteredList<customerdata> filter = new FilteredList<>(listcustomerdata, e -> true);
        customers_search.textProperty().addListener((Observable, oldvalue, newvalue) -> {
            filter.setPredicate(predicatecustomer -> {
                if (newvalue == null ) {
                    return true;
                }
                String searchKey = newvalue.toLowerCase();

                if (predicatecustomer.getCustomernum().toString().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicatecustomer.getName().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicatecustomer.getTotal().toString().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicatecustomer.getPhonenumber().toLowerCase().contains(searchKey)){
                    return true;
                }else  if(predicatecustomer.getCheckin().toString().toLowerCase().contains(searchKey)){
                    return true;
                } else if (predicatecustomer.getCheckout().toString().toLowerCase().contains(searchKey)) {
                    return true;
                }else return false;
            });
        });

        SortedList<customerdata> sortlist = new SortedList<>(filter);
        sortlist.comparatorProperty().bind(customers_tableview.comparatorProperty());
        customers_tableview.setItems(sortlist);
    }


    private  double x=0;
    private double y=0;

    public void logout()
    {


        try
        {
            Alert alert=new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Conformation Messege");
            alert.setHeaderText(null);
            alert.setContentText("Are You Sure You Want To Logout?");
            Optional<ButtonType> option =alert.showAndWait();

            if(option.get().equals(ButtonType.OK))
            {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) ->
                {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->
                {
                    stage.setX(event.getSceneX() - x);
                    stage.setY(event.getSceneY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

                avialablerooms_logout_btn.getScene().getWindow().hide();



            }else
            {
                return;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public  void close()
    {
        System.exit(0);
    }

    public void minimize()
    {
        Stage stage =(Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void availableroomscheckin()
    {
        try
        {
            Parent root =FXMLLoader.load(getClass().getResource("checkin.fxml"));
            Stage stage =new Stage();
            Scene scene =new Scene(root);

            stage.setMinHeight(494+15);
            stage.setMinWidth(398+15);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void staff()
    {
        try
        {
            Parent root =FXMLLoader.load(getClass().getResource("staff.fxml"));
            Stage stage =new Stage();
            Scene scene =new Scene(root);

            stage.setMinHeight(400);
            stage.setMinWidth(600);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private  int count =0;
    public  void dashboardcountbooktoday()
    {
        Date date=new Date();
        java.sql.Date sqldate =new java.sql.Date(date.getTime());

        String sql ="SELECT COUNT(id) FROM  customer WHERE checkin = '" +sqldate+"'";

        connect =database.connectDb();

        count=0;

        try{
            prepare =connect.prepareStatement(sql);
            result= prepare.executeQuery();

            while(result.next())
            {
                count=result.getInt("COUNT(id)");
            }
            System.out.println(count);



        }catch (Exception e){e.printStackTrace();}
    }

    public void  dashboarddisplaybooktoday()
    {
        dashboardcountbooktoday();
        dashboard_booktoday.setText(String.valueOf(count));

    }

    private double sumtoday = 0;
    public  void dashboardsumincometoday()
    {
        Date date = new Date();
        java.sql.Date sqldate =new java.sql.Date(date.getTime());

        String sql="SELECT SUM(total) FROM customer_receipt WHERE date ='"+sqldate+"'";

        connect =database.connectDb();

        try {
            prepare =connect.prepareStatement(sql);
            result =prepare.executeQuery();

            while (result.next())
            {
                sumtoday =result.getDouble("SUM(total)");

            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void  dashboarddisplayincometoday()
    {
        dashboardsumincometoday();
        dashboard_income.setText("$"+String.valueOf(sumtoday));
    }

    private double overall =0;

    public  void dashboardsumtotalincome()
    {
        String sql="SELECT SUM(total) FROM customer_receipt";

        connect =database.connectDb();

        try {
            prepare =connect.prepareStatement(sql);
            result =prepare.executeQuery();

            while (result.next())
            {
                overall=result.getDouble("SUM(total)");
            }

        }catch (Exception e){e.printStackTrace();}
    }

    public void  dashboardtotalincome()
    {
        dashboardsumtotalincome();
        dashboard_totalincome.setText("$"+String.valueOf(overall));
    }

    public void dashboardchart()
    {
        dashboard_areachart.getData().clear();

        String sql="SELECT date,total FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 8";

        connect =database.connectDb();

        XYChart.Series chart =new XYChart.Series();

        try {
            prepare =connect.prepareStatement(sql);
            result=prepare.executeQuery();

            while (result.next())
            {
                chart.getData().add(new XYChart.Data(result.getString(1),result.getInt(2)));


            }

            dashboard_areachart.getData().add(chart);

        }catch (Exception e){e.printStackTrace();}
    }

    public void  defaultnavbtn()
    {
        dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#5068c9,#bc59e4)");

    }

    public  void displayusername()
    {
        Username.setText(getdata.username);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dashboarddisplaybooktoday();
        dashboardchart();
        availableroomsroomtype();
        availableroomstatus();
        availableroomsshowdata();
        customershowdata();
        dashboarddisplayincometoday();
        dashboardtotalincome();
        defaultnavbtn();
        displayusername();

    }

    public void switchform(javafx.event.ActionEvent event) {

        if (event.getSource() == dashboard_btn)
        {
            dashboard_form.setVisible(true);
            customers_form.setVisible(false);
            availableroom_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#5068c9,#bc59e4)");
            customers_btn.setStyle("-fx-background-color:transparent");
            availablerooms_btn.setStyle("-fx-background-color:transparent");

            dashboarddisplayincometoday();
            dashboarddisplaybooktoday();
            dashboardtotalincome();
            dashboardchart();


        } else if (event.getSource() == availablerooms_btn)
        {
            dashboard_form.setVisible(false);
            customers_form.setVisible(false);
            availableroom_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");
            availablerooms_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#5068c9,#bc59e4)");


            availableroomssearch();
            availableroomsshowdata();

        } else if (event.getSource() == customers_btn)
        {
            dashboard_form.setVisible(false);
            customers_form.setVisible(true);
            availableroom_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#5068c9,#bc59e4)");
            availablerooms_btn.setStyle("-fx-background-color:transparent");

            customersearch();
            customershowdata();
        }
    }
}
