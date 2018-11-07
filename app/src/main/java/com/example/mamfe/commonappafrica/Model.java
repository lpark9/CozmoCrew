package com.example.mamfe.commonappafrica;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Myo Thiha on 4/5/2018.
 * This class Model is created to setup basic university lists from the json file.
 */

public class Model {
    public static String HSname;
    public static String GPA = "4.0";
    public static String selected = new String();
    public static ArrayList<String> myColleges = new ArrayList<>(Arrays.asList("University of Buea"));
    public static ArrayList<String> collegeList = new ArrayList<>();
    /**
    *   populateStockList
    *   This method gets the list of college list from the json file.
    */
    public static void populateStockList(Context context) {
        String json = loadJSONFromAsset(context);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String item = jsonObj.getString("id");
                collegeList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
    *   loadJSONFromAsset
    *   This method loads json file "MOCK_DATA" from the asset
    *   @return    String    returns the strings json file
    */
    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("MOCK_DATA.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
