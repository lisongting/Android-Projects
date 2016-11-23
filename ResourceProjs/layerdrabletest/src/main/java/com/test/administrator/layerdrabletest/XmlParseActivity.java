package com.test.administrator.layerdrabletest;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/23.
 */
public class XmlParseActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_p_layout);
        XmlResourceParser parser = getResources().getXml(R.xml.heroes);
        try{
            StringBuilder sb = new StringBuilder("");
            //如果还没有到xml文档的结尾处
            while(parser.getEventType()!=XmlResourceParser.END_DOCUMENT){
                //如果遇到开始标签
                if(parser.getEventType()==XmlResourceParser.START_TAG){
                    String tagName  = parser.getName();
                    if(tagName.equals("hero")){
                        String name = parser.getAttributeValue(null,"name");
                        sb.append("英雄：");
                        sb.append(name);
                        Log.d("tag","解析name标签");
                        String position = parser.getAttributeValue(1);
                        sb.append("位置：");
                        sb.append(position);
                        Log.d("tag","解析位置标签");
                        sb.append("台词：");
                        sb.append(parser.nextText());
                        Log.d("tag","台词");
                    }
                    sb.append("\n");
                }else{
                    Log.d("tag","没有匹配START_TAG");
                }
                parser.next();//获取解析器的下一个事件
            }
            TextView show =(TextView)findViewById(R.id.textView3);
            show.setText(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
