package com.example.miniproject_hotel_management_system;

public class Roomdata
{
    private  Integer roomnumber;
    private  String roomtype;
    private  String status;
    private  double price;

    public Roomdata(Integer roomnumber,String roomtype,String status,double price)
    {
        this.roomnumber=roomnumber;
        this.roomtype=roomtype;
        this.status=status;
        this.price=price;
    }

    public  Integer getRoomnumber()
    {
        return roomnumber;
    }
    public String getroomtype()
    {
        return  roomtype;
    }
    public  String getStatus()
    {
        return status;
    }
    public Double getPrice()
    {
        return price;
    }


}
