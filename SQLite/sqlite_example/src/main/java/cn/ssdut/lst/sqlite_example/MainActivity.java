package cn.ssdut.lst.sqlite_example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView show;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView) findViewById(R.id.show);
        preferences = getSharedPreferences("test",MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void formatXml(View v) {
        Person p1 = new Person(1,"jack", "male", 23);
        Person p2 = new Person(2,"alice", "female", 22);
        Person[] list = new Person[2];
        list[0]=p1;
        list[1] = p2;
        XmlSerializer serializer = Xml.newSerializer();
        File file = new File(Environment.getExternalStorageDirectory()
                , "persons.xml");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            serializer.setOutput(fos,"utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "persons");//参数分别为命名空间和标签名
            for(Person p:list) {
                serializer.startTag(null, "person");
                serializer.attribute(null, "id", p.getId()+"");
                serializer.startTag(null, "name");
                serializer.text(p.getName());
                serializer.endTag(null, "name");
                serializer.startTag(null, "sex");
                serializer.text(p.getSex());
                serializer.endTag(null, "sex");
                serializer.startTag(null, "age");
                serializer.text(p.getAge()+"");
                serializer.endTag(null, "age");
                serializer.endTag(null, "person");
            }
            serializer.endTag(null, "persons");
            serializer.endDocument();
            Toast.makeText(this, "生成成功", Toast.LENGTH_SHORT).show();
            showXmlFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showXmlFile(File file) throws Exception {
        StringBuilder sb = new StringBuilder("");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(fis));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        show.setText(sb);
    }
    public void parseXml(View v) {
        List<Person> list= null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "persons.xml");
            FileInputStream fis = new FileInputStream(file);
            Person p= null;
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, "utf-8");
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("persons".equals(parser.getName())) {
                            list = new ArrayList<>();
                        } else if ("person".equals(parser.getName())) {
                            p = new Person();
                            String id = parser.getAttributeValue(0);
                            p.setId(Integer.parseInt(id));
                        } else if ("name".equals(parser.getName())) {
                            p.setName(parser.nextText());
                        }else if ("sex".equals(parser.getName())) {
                            p.setSex(parser.nextText());
                        }else if ("age".equals(parser.getName())) {
                            p.setAge(Integer.parseInt(parser.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("person".equals(parser.getName())) {
                            list.add(p);
                            p = null;
                        }
                        break;
                }
                type = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list.size()>0)
            for (Person p : list) {
                Toast.makeText(this, p.toString(), Toast.LENGTH_SHORT).show();
            }
        else
            Toast.makeText(this, "list is empty", Toast.LENGTH_SHORT).show();
    }
    public void writeToSP(View v){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日" +
                "hh:mm:ss");
        //存入当前时间
        editor.putString("time",sdf.format(new Date()));
        //存入一个随机数
        editor.putInt("random",(int)(Math.random()*100));
        editor.commit();
    }
    public void readFromSP(View v) {
        String time = preferences.getString("time",null);
        int randNum = preferences.getInt("random",0);
        String result = time ==null?"还未写入数据":"写入时间为："+time+"\n" +
                "上次生成的随机数为："+randNum;
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
    }
    public void openWordList(View view) {
        Intent t = new Intent(this, WordListActivty.class);
        startActivity(t);
    }
}
