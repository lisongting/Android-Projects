package com.lst.wanandroid.core.bean.project;

import com.lst.wanandroid.core.bean.BaseResponse;

public class ProjectListResponse extends BaseResponse {

    private ProjectListData data;

    public ProjectListData getData() {
        return data;
    }

    public void setData(ProjectListData data) {
        this.data = data;
    }
}
