package com.example.rahul.asg4_rxs161630;

//Model for the list view
public class ListData {
    String name,email,phone, lname;
    int imgResId;
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }
    public String getEmail() {
        return email;
    }

    public String getLName() {
        return lname;
    }

    public void setLName(String Name) {
        lname = Name;
    }

    public void setEmail(String Email) {
        email = Email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String Phone) {
        phone = Phone;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
