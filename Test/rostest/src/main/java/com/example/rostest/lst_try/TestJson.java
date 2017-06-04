package com.example.rostest.lst_try;


import org.json.simple.JSONObject;

/**
 * Created by lisongting on 2017/6/4.
 */

public class TestJson {

    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("op", "subscribe");
            jsonObject.put("topic", "/museum_position");
            jsonObject.put("test", "vzvz");
            System.out.print(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
