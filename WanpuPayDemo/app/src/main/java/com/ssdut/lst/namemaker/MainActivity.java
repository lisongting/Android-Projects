package com.ssdut.lst.namemaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    private int[]array;//根据用户的兴趣爱好，形成一个长度为5的数组
    private static final int DATA_ROW=600;
    private static final int DATA_COL=4;
    private EditText editText1;//第一个编辑框,中文名
    private EditText editText2;//第二个编辑框,拼音名
    private RadioGroup radioGroup;
    private Maker maker;
    private PayClass pay;
    static  String[][] str ;
    private SQLiteDatabase db=null;
    private Spinner spinner_color;
    private Spinner spinner_fruit;
    private Spinner spinner_action;
    private Spinner spinner_music;
    private Spinner spinner_watch;
    final String CREATE_TABLE = "create table names(_id integer  primary key autoincrement,"
            +"gender varchar(10),"
            + "name varchar(30),"
            +"detail varchar(150),"
            +"key varchar(10));";
    String input_name;
    String input_name2;
    String sex ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText)findViewById(R.id.name);
        editText2 = (EditText)findViewById(R.id.name2);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        array=new int[]{0,0,0,0,0};
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedID){
                if(checkedID==R.id.male)
                    sex = "ml";
                else if(checkedID==R.id.female)
                    sex = "fe";
            }
        });
        str= new String[DATA_ROW][DATA_COL];
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/My.db3",null);
        getData(str);
        if(!isExist()){
            //如果数据表不存在，则启动一个线程，建表，插入数据
            db.execSQL(CREATE_TABLE);
            new InitDatabaseThread(db,str).start();//在新线程中插入数据
        }
        spinnerInit();
        maker = new Maker(str);
        maker.init();


    }
    //用这个函数获取data.txt 文件中的600条数据,存入str数组
    public void getData(String[][] str){
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        InputStreamReader inputStreamReader=null;
        try{
            inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try{
            String line;
            int i=0;
            while((line = reader.readLine())!=null){
                str[i] = line.split("_");
                i++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void spinnerInit(){
        spinner_color = (Spinner)findViewById(R.id.spinner_color);
        spinner_fruit= (Spinner)findViewById(R.id.spinner_fruit);
        spinner_action= (Spinner)findViewById(R.id.spinner_action);
        spinner_music= (Spinner)findViewById(R.id.spinner_music);
        spinner_watch= (Spinner)findViewById(R.id.spinner_watch);
        spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int COL_NUM=0;
                int value=0;
                String[] colors = getResources().getStringArray(R.array.colors);
                switch(colors[position]){
                    case "黑色":value=8;break;
                    case "白色":value=1;break;
                    case "紫色":value=2;break;
                    case "红色":value=7;break;
                    case "黄色":value=3;break;
                    case "绿色":value=5;break;
                    case "蓝色":value=6;break;
                    case "橙色":value=4;break;
                    default:value=0;break;
                }
                array[COL_NUM] = value;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_fruit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int COL_NUM=1;
                int value=0;
                String[] fruits = getResources().getStringArray(R.array.fruits);
                switch(fruits[position]){
                    case "西瓜":value=3;break;
                    case "香蕉":value=7;break;
                    case "草莓":value=1;break;
                    case "葡萄":value=2;break;
                    case "梨":value=5;break;
                    case "苹果":value=6;break;
                    case "桃":value=8;break;
                    case "橘子":value=4;break;
                    default:value=0;break;
                }
                array[COL_NUM] = value;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int COL_NUM=2;
                int value=0;
                String[] actions = getResources().getStringArray(R.array.actions);
                switch(actions[position]){
                    case "足球":value=7;break;
                    case "篮球":value=3;break;
                    case "乒乓球":value=6;break;
                    case "羽毛球":value=5;break;
                    case "登山":value=8;break;
                    case "跑步":value=2;break;
                    case "游泳":value=1;break;
                    case "滑冰":value=4;break;
                    default:value=0;break;
                }
                array[COL_NUM] = value;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_music.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int COL_NUM=3;
                int value=0;
                String[] musics = getResources().getStringArray(R.array.music);
                switch(musics[position]){
                    case "流行音乐":value=6;break;
                    case "摇滚音乐":value=7;break;
                    case "民谣":value=4;break;
                    case "说唱":value=2;break;
                    case "轻音乐":value=5;break;
                    case "电子音乐":value=1;break;
                    case "古典乐":value=8;break;
                    case "爵士乐":value=3;break;
                    default:value=0;break;
                }
                array[COL_NUM] = value;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_watch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int COL_NUM=4;
                int value=0;
                String[] watches = getResources().getStringArray(R.array.watch);
                switch(watches[position]){
                    case "悬疑\\推理":value=1;break;
                    case "自然\\科学":value=4;break;
                    case "动漫\\神话":value=5;break;
                    case "战争\\枪战":value=3;break;
                    case "言情\\喜剧":value=6;break;
                    case "动作\\格斗":value=7;break;
                    case "灾难\\惊悚":value=8;break;
                    case "古装\\武侠":value=2;break;
                    default:value=0;break;
                }
                array[COL_NUM] = value;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean isExist(){
        //检查是否已经有数据库了，如果没有数据库，则进行数据库初始化操作
        boolean exits=false;
        String sql = "select * from sqlite_master where name='names'";
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.getCount()!=0){
                exits = true;
            }
        return exits;
    }

    public void makeName(View sourse){

        int flag=5 ;
        input_name = editText1.getText().toString();//输入的中文名
        input_name2 = editText2.getText().toString();//输入的拼音名
        pay= new PayClass(this,maker,sex,array,db,input_name);
        AlertDialog.Builder dialogBuilder;
        for(int i=0;i<5;i++){
            if(array[i]==0)
                flag--;
        }
        if(input_name==""||input_name2==""||sex==null){
            Toast.makeText(this,"亲，请将信息输入完整哦 ", Toast.LENGTH_SHORT).show();
        }
        else{
            if(flag<3){
                Toast.makeText(this,"亲，请选择至少3项喜好<@_@> 已选择:"+flag+"项", Toast.LENGTH_SHORT).show();
            }else{
                dialogBuilder = new AlertDialog.Builder(this)
                        .setTitle("温馨提示")
                        .setMessage("即将支付金额：1.00元,是否继续？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pay.run();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"支付已取消",Toast.LENGTH_SHORT).show();
                            }
                        });
                dialogBuilder.show();
            }
        }
    }


}
