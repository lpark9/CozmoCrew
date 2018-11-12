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
 * Editted by Eun Bin Lee on 11/12/2018.
 * This class Model is created to setup basic university lists from the json file.
 */

public class Model {
    public static String HSname;
    public static String GPA = "4.0";
    public static String selected = new String();
    public static String websiteSelected = new String();
    public static String rankingSelected = new String();

    public static String tuitionSelected = new String();
    public static String rateSelected = new String();
    public static String totalSelected = new String();
    public static String deadlineSelected = new String();

    public static ArrayList<String> myColleges = new ArrayList<>(Arrays.asList("University of Buea"));
    public static ArrayList<String> collegeList = new ArrayList<>();
    public static ArrayList<String> rankingList = new ArrayList<>();
    public static ArrayList<String> websiteList = new ArrayList<>();

    public static ArrayList<String> rateList = new ArrayList<>();
    public static ArrayList<String> tuitionList = new ArrayList<>();
    public static ArrayList<String> totalList = new ArrayList<>();
    public static ArrayList<String> deadlineList = new ArrayList<>();


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
                String item_2 = jsonObj.getString("website");
                websiteList.add(item_2);
                String item_3 = jsonObj.getString("ranking");
                rankingList.add(item_3);
                String item_4 = jsonObj.getString("acceptance");
                rateList.add(item_4);
                String item_5 = jsonObj.getString("tuition");
                tuitionList.add(item_5);
                String item_6 = jsonObj.getString("total");
                totalList.add(item_6);
                String item_7 = jsonObj.getString("deadline");
                deadlineList.add(item_7);
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

