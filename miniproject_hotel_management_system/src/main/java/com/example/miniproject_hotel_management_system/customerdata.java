package com.example.miniproject_hotel_management_system;

import java.util.Date;

public class customerdata
{
    private  Integer customernum;
    private  String name;
    private  String phonenumber;
    private  Double total;
    private Date checkin;
    private  Date checkout;



    public customerdata(Integer customernum,String name,String phonenumber,Double total,Date checkin,Date checkout)
    {
        this.customernum=customernum;
        this.name=name;
        this.phonenumber=phonenumber;
        this.total=total;
        this.checkin=checkin;
        this.checkout=checkout;
    }

    public Integer getCustomernum()
    {
        return  customernum;
    }
    public  String getName()
    {
        return name;
    }
    public  String getPhonenumber()
    {
        return  phonenumber;
    }
    public Double getTotal()
    {
        return total;
    }
    public  Date getCheckin()
    {
        return  checkin;
    }
    public Date getCheckout()
    {
        return checkout;
    }


}
