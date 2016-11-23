package com.test.administrator.sdfileexplorer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    ListView listView ;
    TextView textView;
    File currentParent;
    File[] currentFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list);
        textView = (TextView)findViewById(R.id.path);
        //final File root = Environment.getExternalStorageDirectory();//获取SD卡的根路径
        final File root = this.getFilesDir();
        try{
            Toast.makeText(MainActivity.this, "根路径："+root.getCanonicalPath(), Toast.LENGTH_SHORT).show();
            Log.d("tag",root.getCanonicalPath());

        }catch(Exception e){
            e.printStackTrace();
        }
        if(root.exists()){
            currentParent = root;
            currentFiles = root.listFiles();
            if(currentFiles==null)
            Log.d("tag","currentFile为空");
            else
            Log.d("tag","文件列表下文件数目："+currentFiles.length);
            inflateListView(currentFiles);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                //如果用户单击了文件，则不做任何处理
                if(currentFiles[position].isFile()) return;
                //如果用户单击了文件夹，则获取文件夹内的文件列表
                File[] tmp =currentFiles[position].listFiles();
                if(tmp==null||tmp.length==0){
                    Toast.makeText(MainActivity.this, "当前文件夹下的内容不可访问或没有文件", Toast.LENGTH_SHORT).show();
                }else{
                    //获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position];
                    currentFiles = tmp;//保存当前父文件夹内的全部文件和文件夹
                    inflateListView(currentFiles);
                }
            }
        });
        Button parent = (Button) findViewById(R.id.parent);
        parent.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               try{
                   if(!currentParent.getCanonicalPath().equals(root)){
                       currentParent = currentParent.getParentFile();
                       currentFiles = currentParent.listFiles();
                       inflateListView(currentFiles);
                   }
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
        });
    }

    private void inflateListView(File[] files){
        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<files.length;i++){
            Map<String,Object> listItem = new HashMap<>();
            if(files[i].isDirectory()){
                listItem.put("icon",R.drawable.folder);
            }else{
                listItem.put("icon",R.drawable.file);
            }
            listItem.put("fileName",files[i].getName());
            listItems.add(listItem);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.line,
                new String[]{"icon","fileName"},new int[]{R.id.icon,R.id.file_name});
        listView.setAdapter(adapter);
        try{
            textView.setText("当前路径为:"+currentParent.getCanonicalPath());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
