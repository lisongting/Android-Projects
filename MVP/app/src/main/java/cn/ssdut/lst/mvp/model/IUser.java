package cn.ssdut.lst.mvp.model;

/**
 * Created by Administrator on 2017/3/12.
 */

public interface IUser {
    String getName();
    String getPassword();
    int checkUserValidity(String name,String password);

}
