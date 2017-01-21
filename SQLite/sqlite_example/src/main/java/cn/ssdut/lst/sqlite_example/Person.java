package cn.ssdut.lst.sqlite_example;

/**
 * Created by Administrator on 2017/1/20.
 */

public class Person {
    private int id;
    private String name;
    private String sex;
    private int age;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Person() {

    }
    public Person(int id, String name, String sex, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return "Person [id=" + id + ", age=" + age + ", name=" + name
                + ", sex=" + sex + "]";
    }
}
