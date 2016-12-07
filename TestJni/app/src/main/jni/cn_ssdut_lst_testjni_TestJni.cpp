//
// Created by Administrator on 2016/12/6.
//
# include<stdio.h>
# include<stdlib.h>
# include<cn_ssdut_lst_testjni_TestJni.h>
# include<Add.h>
Add *pCAdd = NULL;
JNIEXPORT jboolean JNICALL Java_cn_ssdut_lst_testjni_TestJni_Init(JNIEnv *env, jobject obj){
    if(pCAdd ==null ){
        pCAdd = new CAdd;
    }
    return pCAdd!=null;
}

JNIEXPORT jint JNICALL Java_cn_ssdut_lst_testjni_TestJni_Add(JNIEnv *env, jobject obj, jint x, jint y){
    int res = -1;
    if(pCAdd != null){
        res = pCAdd->Add(x,y);
    }
    return res;
}

JNIEXPORT void JNICALL Java_cn_ssdut_lst_testjni_TestJni_Destroy(JNIEnv *env, jobject obj){
    if(pCAdd !=null){
        pCAdd =null;
    }
}
