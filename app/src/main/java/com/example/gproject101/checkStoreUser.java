package com.example.gproject101;

public class checkStoreUser {

    private String name;
    private String phoneNumber;
    private String address;

    public checkStoreUser(){
        name = "";
        phoneNumber= "";
        address= "";
    }

    public checkStoreUser(String name, String phoneNumber, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
