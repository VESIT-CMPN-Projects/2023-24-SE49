package com.example.miniproject_hotel_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.jar.Attributes;

public class CheckInController implements Initializable
{
    @FXML
    void customercheckin(ActionEvent event) {

    }


    @FXML
    private TextField Name;

    @FXML
    private DatePicker checkin_date;
    @FXML
    private TextField Email;

    @FXML
    private TextField Phonenumber;

    @FXML
    private ComboBox<?> Roomnumber;

    @FXML
    private ComboBox<?> Roomtype;

    @FXML
    private AnchorPane checkin_form;

    @FXML
    private DatePicker checkout_date;

    @FXML
    private Button close;

    @FXML
    private Label Total;


    @FXML
    private Label totalday;

    @FXML
    void displaytotal(ActionEvent event) {

    }

    @FXML
    void roomnumberlist(ActionEvent event) {

    }

    @FXML
    void rooomtypelist(ActionEvent event) {

    }


    @FXML
    private Label customernumber;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private  Statement statement;

    public void customercheckin()
    {
        String insertcustomer ="INSERT INTO customer (customer_id,name,total,phonenumber,roomnumber,roomtype,checkin,checkout,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        connect=database.connectDb();
        try
        {
            String customernum=customernumber.getText();
            String name = Name.getText();
            String phonenumber = Phonenumber.getText();
            String roomnumber = (String) Roomnumber.getSelectionModel().getSelectedItem();
            String roomtype= (String) Roomtype.getSelectionModel().getSelectedItem();
            String Checkindate=String.valueOf(checkin_date.getValue());
            String Checkoutdate=String.valueOf(checkout_date.getValue());
            String email=Email.getText();

            Alert alert;

            if (customernum ==null || name==null || phonenumber==null || roomnumber==null || roomtype==null || Checkindate==null || Checkoutdate==null || email==null)
            {
                alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Messege");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else
            {
                alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conformation Messege");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");

                Optional<ButtonType> option =alert.showAndWait();
                String totalc=String.valueOf(totalp);
                if (option.get().equals(ButtonType.OK))
                {
                    prepare=connect.prepareStatement(insertcustomer);
                    prepare.setString(1,customernum);
                    prepare.setString(2,name);
                    prepare.setString(3,totalc);
                    prepare.setString(4,phonenumber);
                    prepare.setString(5,roomnumber);
                    prepare.setString(6,roomtype);
                    prepare.setString(7,Checkindate);
                    prepare.setString(8,Checkoutdate);
                    prepare.setString(9,email);
                    prepare.executeUpdate();

                    String Date=String.valueOf(checkin_date.getValue());
                    String customern=customernumber.getText();


                    String customerreceipt="INSERT INTO customer_receipt (date,customer_num,total)"
                            +"VALUES(?,?,?)";

                    prepare =connect.prepareStatement(customerreceipt);
                    prepare.setString(1,Date);
                    prepare.setString(2,customern);
                    prepare.setString(3,totalc);

                    prepare.execute();

                    String sqleidtstatus="UPDATE room SET status='Occupied' WHERE Roomnumber ='"+roomnumber+"'";

                    statement = connect.createStatement();
                    statement.executeUpdate(sqleidtstatus);

                    alert.setTitle("Information Messege");
                    alert.setHeaderText(null);
                    alert.setContentText("Succefully check-in");
                    alert.showAndWait();

                    reset();
                }
                else
                {
                    return;
                }
            }

        }catch (Exception e){e.printStackTrace();}
    }
    public void rooomtypelist()
    {
        String listtype ="SELECT * FROM room WHERE  status ='Available'";

        connect =database.connectDb();

        try
        {
            ObservableList listdata = FXCollections.observableArrayList();
            prepare =connect.prepareStatement(listtype);
            result =prepare.executeQuery();

            while (result.next())
            {
                listdata.add(result.getString("type"));
            }

            Roomtype.setItems(listdata);
            roomnumberlist();
        }catch (Exception e){e.printStackTrace();}
    }

    public  void  roomnumberlist()
    {

        String item =(String) Roomtype.getSelectionModel().getSelectedItem();
        String availableroomnumber="SELECT * FROM room WHERE type ='"+item+"' and status='Available'";
        connect=database.connectDb();

        try
        {
            ObservableList listdata =FXCollections.observableArrayList();
            prepare =connect.prepareStatement(availableroomnumber);
            result =prepare.executeQuery();

            while (result.next())
            {
                listdata.add(result.getString("roomnumber"));

            }

            Roomnumber.setItems(listdata);
        }catch (Exception e){e.printStackTrace();}

    }





    public  void  customernumber()
    {
        String customername="SELECT customer_id FROM customer";

        connect=database.connectDb();
        try
        {
            prepare =connect.prepareStatement(customername);
            result=prepare.executeQuery();

            while (result.next())
            {
                getdata.customernum=result.getInt("customer_id");
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public  void reset()
    {
        Name.setText("");
        Phonenumber.setText("");
        Email.setText("");
        Roomtype.getSelectionModel().clearSelection();
        Roomnumber.getSelectionModel().clearSelection();
        totalday.setText("---");
        Total.setText("$0.0");
    }

    public  void totaldays()
    {
        Alert alert;

        if(checkout_date.getValue().isAfter(checkin_date.getValue()))
        {
            getdata.Totaldays = checkout_date.getValue().compareTo(checkin_date.getValue());

        }else
        {

            alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Messege");
            alert.setHeaderText(null);
            alert.setContentText("Invalid check-out date ");
            alert.showAndWait();

        }
    }

    private  double totalp =0;
    public void displaytotal()
    {
        totaldays();
        String totald = String.valueOf(getdata.Totaldays);
        totalday.setText(totald);
        String selectitem = (String)Roomnumber.getSelectionModel().getSelectedItem();

        String sql ="SELECT * FROM room WHERE roomnumber ='"+selectitem+"'";

        double pricedata =0;

        connect=database.connectDb();

        try
        {
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();

            while (result.next())
            {
                pricedata =result.getDouble("price");
            }

            totalp =(float) ((pricedata)*getdata.Totaldays);
            System.out.println("Total payment: "+ totalp);
            System.out.println("pricedata: " + pricedata);

            Total.setText("$" + String.valueOf(totalp));

        }catch (Exception e){e.printStackTrace();}

    }

    public  void displaycustomernumeber()
    {
        customernumber();
        customernumber.setText(String.valueOf(getdata.customernum + 1));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displaycustomernumeber();
        rooomtypelist();
        roomnumberlist();
    }
}
