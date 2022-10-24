package com.example.democontentprovider;

import java.io.Serializable;

public class Contact implements Serializable {
    String phone,name;

    public String getPhone() {
        return phone;
    }
    @Override
    public String toString(){
        return "Name: "+name+"\nPhone: "+phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Contact(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }
}
