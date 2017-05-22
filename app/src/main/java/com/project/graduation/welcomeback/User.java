package com.project.graduation.welcomeback;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.author;
import static com.project.graduation.welcomeback.R.string.email;

/**
 * This class represent the current user account.
 * Created by ayyad on 4/25/2017.
 */

public class User {
    private String mName;          //the name of the user

    private List<String> mMissingReportsRefrences = new ArrayList<>(); // the user's missings reports refrences that s/he made.
    private List<String> mSuspectsReportsRefrences = new ArrayList<>(); // the user's suspects reports refrences that s/he made.

    public User() {

    }

    /**
     * the first constructor of the user class
     * @param name the name of the current user
     * @param missingReportsRefrences the missing reports references that current user made.
     * @param suspectsReportsRefrences the missing reports references that current user made.
     **/
    public User(String name, List<String> missingReportsRefrences,
                List<String> suspectsReportsRefrences) {
        mName = name;

        mMissingReportsRefrences = missingReportsRefrences;
        mSuspectsReportsRefrences = suspectsReportsRefrences;
    }

    /**
     * the second constructor of the user class
     * @param name the name of the current user
     **/
    public User(String name) {
        mName = name;
        mMissingReportsRefrences = new ArrayList<>();
        mSuspectsReportsRefrences = new ArrayList<>();
    }

    /** getters and setters methods for the class fields. **/

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        this.mName = name;
    }

    public List<String> getmMissingReportsRefrences() {
        return mMissingReportsRefrences;
    }

    public void setmMissingReportsRefrences(List<String> mMissingReportsRefrences) {
        this.mMissingReportsRefrences = mMissingReportsRefrences;
    }

    public List<String> getmSuspectsReportsRefrences() {
        return mSuspectsReportsRefrences;
    }

    public void setmSuspectsReportsRefrences(List<String> mSuspectsReportsRefrences) {
        this.mSuspectsReportsRefrences = mSuspectsReportsRefrences;
    }

    public void addSuspect(String report) {
        mSuspectsReportsRefrences.add(report);
    }

    public void addMissing(String report) {
        mMissingReportsRefrences.add(report);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mName", mName);
        result.put("mMissingReportsRefrences", mMissingReportsRefrences);
        result.put("mSuspectsReportsRefrences", mSuspectsReportsRefrences);
        return result;
    }

}