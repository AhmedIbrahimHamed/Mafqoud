package com.project.graduation.welcomeback.Home.Data;

import java.util.ArrayList;

/**
 * Created by Ahmed on 5/12/2017.
 */

public interface DataManger {

    void saveData(ArrayList<String> data);

    ArrayList<String> getData();
}
