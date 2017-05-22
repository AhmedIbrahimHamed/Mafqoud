package com.project.graduation.welcomeback.Home.Data;


import com.firebase.client.core.Repo;

import java.util.ArrayList;

/**
 * Created by Ahmed on 5/5/2017.
 */

public class Report {

    private String photoUrl;         //Photo URL of the suspect/missing.
    private String name;            //Name of suspect/missing.
    private String age;             //Age of suspect/missing.
    private String gender;          //Gender of suspect/missing.
    private String location;        //Location of suspect/missing when they were seen/lost.
    private String moreInfo;        //Any additional info the reporter want to add.
    private String contactInfo;     //Contact Information for the reporter to get back to.
    private Boolean found;          //Used to identify if the photo is found or not

    //empty constructor
    public Report(){

    }

    public Report(ArrayList<String> reportInfo){
        this.name = reportInfo.get(0);
        this.age = reportInfo.get(1);
        this.gender = reportInfo.get(2);
        this.location = reportInfo.get(3);
        this.contactInfo = reportInfo.get(4);
        this.moreInfo = reportInfo.get(5);
        this.photoUrl = reportInfo.get(6);
        this.found = false;
    }

    //full constructor
    public Report(String photo, String name, String age, String gender, String location,
                  String moreInfo,String contactInfo){
        this.photoUrl = photo;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.moreInfo = moreInfo;
        this.contactInfo = contactInfo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photoUrl;
    }

    public void setPhoto(String photo) {
        this.photoUrl = photo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        found = found;
    }
}
