package com.example.testmultiframeworks;

import com.example.testmultiframeworks.mvp.IModel;

/**
 * Created by lisongting on 2017/6/25.
 */

public class MockLoginModel implements IModel{

    @Override
    public boolean login(String userName, String password) {
        if (userName.equals("lst") && password.equals(123)) {
            return true;
        } else {
            return false;
        }
    }
}
