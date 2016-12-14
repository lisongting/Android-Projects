package cn.ssdut.lst.contactsreadertest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 * 传入二维String数组对Maker进行初始化,得到几个关键数组
 */
public class Maker {

    private static final int DATA_ROW=600;
    private static final int DATA_COL=4;
    private String[][] str;
    private String[] str_gender = new String[DATA_ROW];
    private String[] str_name = new String[DATA_ROW];
    private String[] str_detail = new String[DATA_ROW];
    int[] int_keys = new int[DATA_ROW];
    int[][] int_keys2 = new int[DATA_ROW][5];


    public Maker(String[][] str){
        this.str = str;

    }

    public void init(){
        //对关键数组进行初始化
        for(int i=0;i<DATA_ROW;i++){
            if(str[i][0]!=null){
                for(int j=0;j<DATA_COL;j++){
                    if(j==0){
                        str_gender[i] = str[i][j];
                    }else if(j==1){
                        str_name[i] = str[i][j];
                    }else if(j==2){
                        str_detail[i] = str[i][j];
                    }else if(j==3){
                        int_keys[i] = Integer.parseInt(str[i][j]);
                        String tmp = str[i][j];
                        for(int k=0;k<5;k++){
                            int_keys2[i][k]  = tmp.charAt(k)-48;
                        }
                    }
                }
            }else{
                break;
            }
        }

    }
    public List<Integer> findName(String gender,int array[]){
        List<Integer> result ;
        result = find(gender,array,int_keys,int_keys2);
        return result;
    }
    //根据5个有效位去进行匹配
    public List<Integer> find(String gender,int array[], int int_keys[], int int_keys2[][]){
        int count = 5;//5个有效位
        List<Integer> result = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(array[i]==0){
                count--;
            }
        }
        if(count==5){
            //先把array数组转化为5位的整数形式
            StringBuilder tmp=new StringBuilder();
            for(int i=0;i<5;i++){
                tmp.append(array[i]);
            }
            int value = Integer.parseInt(tmp.toString());
            //直接匹配
            if(gender=="fe"){//如果性别是女,则从0开始查询到300
                for(int i=0;i<(DATA_ROW/2);i++){
                    if(value==int_keys[i]){
                        result.add(int_keys[i]);
                    }
                }
            }else if(gender =="ml"){
                for(int i=(DATA_ROW/2);i<DATA_ROW;i++){
                    if(value==int_keys[i]){
                        result.add(int_keys[i]);
                    }
                }
            }

            //直接匹配失败,则变成4位去找
            if(result.isEmpty()){
                //将array的每一位都依次变成0,循环5次,按照4位的条件去找
                for(int i=0;i<5;i++){
                    int t = array[i];
                    array[i]=0;
                    //按照4位的形式进行对比
                    int[] index = new int[4];
                    for(int j=0,k=0;j<5&&k<4;j++){
                        if(array[j]!=0){
                            index[k]=j;
                            k++;
                        }
                    }
                    if(gender=="fe"){
                        for(int j=0;j<(DATA_ROW)/2;j++){
                            if(int_keys2[j][index[0]]==array[index[0]]
                                    &&int_keys2[j][index[1]]==array[index[1]]
                                    &&int_keys2[j][index[2]]==array[index[2]]
                                    &&int_keys2[j][index[3]]==array[index[3]]){
                                result.add(int_keys[j]);//如果直接匹配到,则加入到结果集合中
                            }
                        }
                    }else if(gender=="ml"){
                        for(int j=(DATA_ROW)/2;j<DATA_ROW;j++){
                            if(int_keys2[j][index[0]]==array[index[0]]
                                    &&int_keys2[j][index[1]]==array[index[1]]
                                    &&int_keys2[j][index[2]]==array[index[2]]
                                    &&int_keys2[j][index[3]]==array[index[3]]){
                                result.add(int_keys[j]);//如果直接匹配到,则加入到结果集合中
                            }
                        }
                    }

                    array[i]=t;
                }
                //如果按照4位的条件去找依然失败,则变成3位,按照3位的条件去匹配
                if(result.isEmpty()){
                    //5位的数,取两位为0,这种情况会生成10个值
                    for(int i=0;i<4;i++){
                        int t1 = array[i];
                        array[i]=0;
                        for(int j=i+1;j<5;j++){
                            int t2 = array[j];
                            array[j]=0;
                            result = findByThree(gender,array,int_keys,int_keys2,result);
                            array[j] = t2;
                        }
                        array[i] = t1;
                    }
                }
            }
        }else if(count==4){
            result = findByFour(gender,array,int_keys,int_keys2,result);
        }else if(count==3){
            result = findByThree(gender,array,int_keys,int_keys2,result);
        }else{
            System.out.println("输入条件异常");
        }
        return result;
    }

    //根据4个有效位进行匹配
    public List<Integer> findByFour(String gender,int array[],int int_keys[],int int_keys2[][],List<Integer> result){
        int[] index = new int[4];//用来标记四位数字在数组中的下标
        for(int i=0,j=0;i<5&&j<4;i++){
            if(array[i]!=0){
                index[j]=i;
                j++;
            }
        }
        int id0 = index[0];int id1 = index[1];int id2 = index[2];int id3 = index[3];//各有效位的下标
        int a = array[id0];int b = array[id1];int c = array[id2];int d = array[id3];//各有效位的值
        //第一轮,直接匹配
        if (gender == "fe") {
            for(int i=0;i<(DATA_ROW)/2;i++){
                if(int_keys2[i][id0]==a
                        &&int_keys2[i][id1]==b
                        &&int_keys2[i][id2]==c
                        &&int_keys2[i][id3]==d){
                    result.add(int_keys[i]);//如果直接匹配到,则加入到结果集合中
                }
            }
        }else if(gender=="ml"){
            for(int i=(DATA_ROW)/2;i<DATA_ROW;i++){
                if(int_keys2[i][id0]==a
                        &&int_keys2[i][id1]==b
                        &&int_keys2[i][id2]==c
                        &&int_keys2[i][id3]==d){
                    result.add(int_keys[i]);//如果直接匹配到,则加入到结果集合中
                }
            }
        }

        //如果第一轮没有匹配到,则将4位有效位依次赋值0,转变为3位的值,进行3位匹配算法
        if(result.isEmpty()){
            for(int i=0;i<4;i++){
                int tmp = array[i];
                array[index[i]]=0;
                result = findByThree(gender,array,int_keys,int_keys2,result);
                array[index[i]] = tmp;
            }
        }else{
            return result;
        }
        return result;

    }

    //根据3个有效位进行匹配
    public List<Integer> findByThree(String gender,int array[],int int_keys[],int int_keys2[][],List<Integer> result){
        int[] index = new int[3];//用来标记三位数字在数组中的下标
        for(int i=0,j=0;i<5&&j<3;i++){
            if(array[i]!=0){
                index[j]=i;
                j++;
            }
        }
        int id0 = index[0];int id1 = index[1];int id2 = index[2];//各有效位的下标
        int a = array[id0];int b = array[id1];int c = array[id2];//各有效位的值
        if(a==0||b==0||c==0){
            System.out.println("条件异常");
            return result;
        }
        //第一轮:直接匹配,3个位置的数一一对应相等
        if(gender=="fe"){
            for(int i=0;i<(DATA_ROW)/2;i++){
                if(int_keys2[i][id0]==a&&int_keys2[i][id1]==b&&int_keys2[i][id2]==c){
                    result.add(int_keys[i]);//如果直接匹配到,则加入到结果集合中
                }
            }
        }else if(gender=="ml"){
            for(int i=(DATA_ROW)/2;i<DATA_ROW;i++){
                if(int_keys2[i][id0]==a&&int_keys2[i][id1]==b&&int_keys2[i][id2]==c){
                    result.add(int_keys[i]);//如果直接匹配到,则加入到结果集合中
                }
            }
        }

        //如果没有找到结果,则进行第二轮:任意两个位置一样的数对应相等
        if(result.isEmpty()){
            if(gender=="fe"){
                for(int i=0;i<(DATA_ROW)/2;i++){
                    if(int_keys2[i][id0]==a&&int_keys2[i][id1]==b)
                        result.add(int_keys[i]);
                    if(int_keys2[i][id0]==a&&int_keys2[i][id2]==c)
                        result.add(int_keys[i]);
                    if(int_keys2[i][id1]==b&&int_keys2[i][id2]==c)
                        result.add(int_keys[i]);
                }
            }else if(gender=="ml"){
                for(int i=(DATA_ROW)/2;i<DATA_ROW;i++){
                    if(int_keys2[i][id0]==a&&int_keys2[i][id1]==b)
                        result.add(int_keys[i]);
                    if(int_keys2[i][id0]==a&&int_keys2[i][id2]==c)
                        result.add(int_keys[i]);
                    if(int_keys2[i][id1]==b&&int_keys2[i][id2]==c)
                        result.add(int_keys[i]);
                }
            }

            //数据共600条，男，女各300条，由于数据规模的原因，这里可能也会匹配不到数
            //所以这里再加一个搜索轮次
            //(若男女的数据各有600条，则上面的步骤一定会找到匹配的结果。已经过穷举测试)
            if(result.isEmpty()){
                if(gender=="fe"){
                    for(int i=0;i<(DATA_ROW)/2;i++){
                        //这里条件已经放得很宽了
                        if(int_keys2[i][id0]==a||int_keys2[i][id1]==b||int_keys2[i][id2]==c)
                            result.add(int_keys[i]);
                    }
                }else if(gender=="ml"){
                    for(int i=(DATA_ROW)/2;i<DATA_ROW;i++){
                        //这里条件已经放得很宽了
                        if(int_keys2[i][id0]==a||int_keys2[i][id1]==b||int_keys2[i][id2]==c)
                            result.add(int_keys[i]);
                    }
                }

            }
        }

        return result;
    }
}
