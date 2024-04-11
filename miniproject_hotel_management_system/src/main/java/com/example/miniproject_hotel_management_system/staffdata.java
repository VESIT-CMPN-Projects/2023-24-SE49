package com.example.miniproject_hotel_management_system;

public class staffdata
{
    private  String Name;
    private  String Emp_id;
    private  String Gender;
    private  String Contact_no;
    private String Postion;
    private Double salary;

    public  staffdata(String Name,String Emp_id,String Gender,String Contact_no,String Position,Double salary)
    {
        this.Name=Name;
        this.Emp_id=Emp_id;
        this.Gender=Gender;
        this.Contact_no=Contact_no;
        this.Postion=Position;
        this.salary=salary;
    }

    public String getName()
    {
        return Name;
    }

    public  String getEmp_id()
    {
        return  Emp_id;
    }
    public  String getGender()
    {
        return  Gender;
    }
    public String getContact_no()
    {
        return Contact_no;
    }
    public String getPostion()
    {
        return Postion;
    }

    public Double getSalary()
    {
        return salary;
    }
}
