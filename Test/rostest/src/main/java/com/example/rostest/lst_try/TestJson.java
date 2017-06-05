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
            jsonObject.put("boolValue", true);
            System.out.print(jsonObject.toString());
            boolean b = (boolean) jsonObject.get("boolValue");

            System.out.println(b);
            System.out.println(jsonObject.get("boolValue"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
