package com.lst.airhockeyimprovemallet.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lisongting on 2018/2/24.
 */

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                builder.append(nextLine);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return builder.toString();
    }
}

