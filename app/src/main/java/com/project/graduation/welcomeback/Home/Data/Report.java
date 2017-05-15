package com.project.graduation.welcomeback.Home.Data;


/**
 * Created by Ahmed on 5/5/2017.
 */

public class Report {

    private String photo;       //Photo of suspect/missing.
    private String name;        //Name of suspect/missing.
    private String age;            //Age of suspect/missing.
    private String gender;      //Gender of suspect/missing.
    private String location;    //Location of suspect/missing when they were seen/lost.
    private String moreInfo;    //Any additional info the reporter want to add.
    private String contactInfo; //Contact Information for the reporter to get back to.

    //empty constructor
    public Report(){

    }

    //full constructor
    public Report(String photo, String name, String age, String gender, String location,
                  String time, String moreInfo){
        this.photo = photo;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.moreInfo = moreInfo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

}
