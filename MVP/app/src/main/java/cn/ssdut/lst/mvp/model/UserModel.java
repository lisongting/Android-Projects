package cn.ssdut.lst.mvp.model;

/**
 * Created by Administrator on 2017/3/12.
 */

public class UserModel implements IUser {
    String name;
    String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String password) {
        if (name==null||password==null||!name.equals(getName())||!password.equals(getPassword())){
            return -1;
        }
        return 1;
    }
}
