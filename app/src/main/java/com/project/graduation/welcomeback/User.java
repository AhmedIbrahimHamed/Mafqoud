package com.project.graduation.welcomeback;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the current user account.
 * Created by ayyad on 4/25/2017.
 */

public class User {
    private String mName;          //the name of the user
    private String mEmail;        //the email of the user
    private String mPassword;     //the user's password

    private List<DatabaseReference> mMissingReportsRefrences; // the user's missings reports refrences that s/he made.
    private List<DatabaseReference> mSuspectsReportsRefrences; // the user's suspects reports refrences that s/he made.

    /**
     * the first constructor of the user class
     * @param name the name of the current user
     * @param email the email of the current user
     * @param password the password of the current user
     * @param missingReportsRefrences the missing reports references that current user made.
     * @param suspectsReportsRefrences the missing reports references that current user made.
     **/
    public User(String name, String email, String password,
                List<DatabaseReference> missingReportsRefrences, List<DatabaseReference> suspectsReportsRefrences) {
        mName = name;
        mEmail = email;
        mPassword = password;
        mMissingReportsRefrences = missingReportsRefrences;
        mSuspectsReportsRefrences = suspectsReportsRefrences;
    }

    /**
     * the second constructor of the user class
     * @param name the name of the current user
     * @param email the email of the current user
     * @param password the password of the current user
     **/
    public User(String name, String email, String password ) {
        mName = name;
        mEmail = email;
        mPassword = password;
        mMissingReportsRefrences = new ArrayList<>();
        mSuspectsReportsRefrences = new ArrayList<>();
    }

    /** getters and setters methods for the class fields. **/

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }


    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public List<DatabaseReference> getmMissingReportsRefrences() {
        return mMissingReportsRefrences;
    }

    public void setmMissingReportsRefrences(List<DatabaseReference> mMissingReportsRefrences) {
        this.mMissingReportsRefrences = mMissingReportsRefrences;
    }

    public List<DatabaseReference> getmSuspectsReportsRefrences() {
        return mSuspectsReportsRefrences;
    }

    public void setmSuspectsReportsRefrences(List<DatabaseReference> mSuspectsReportsRefrences) {
        this.mSuspectsReportsRefrences = mSuspectsReportsRefrences;
    }
}