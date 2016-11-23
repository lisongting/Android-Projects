package com.test.administrator.fragmenttest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22.
 */
public class BookContent {
    public static class Book{
        public Integer id;
        public String title;
        public String desc;
        public Book(Integer id,String title,String desc){
            this.id = id;
            this.title=title;
            this.desc=desc;
        }
        public String toString(){
            return title;
        }
    }

    public static List<Book> ITEMS = new ArrayList<>();
    public static Map<Integer,Book> ITEM_MAP = new HashMap<>();
    //使用静态初始化块
    static {
        addItem(new Book(1,"java讲义","javajavajavajavajavajavajavajavajava"));
        addItem(new Book(2,"android讲义","androidandroidandroidandroidandroidandroidandroidandroid"));
        addItem(new Book(3,"javaEE","hnbakojflfffffffacvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"));
    }
    public static void addItem(Book book){
        ITEMS.add(book);
        ITEM_MAP.put(book.id,book);
    }
}
