package cn.lst.facerecog.entity;

import java.util.List;

/**
 * Created by lisongting on 2017/12/27.
 * 优图 JSON响应对应实体类
 */

public class ResponseCreatePerson {


    /**
     * person_id : person0
     * suc_group : 1
     * suc_face : 1
     * errorcode : 0
     * session_id : session_id
     * face_id : “1009550071676600319”
     * group_ids : ["tencent"]
     * errormsg : ok
     */

    private String person_id;
    private int suc_group;
    private int suc_face;
    private int errorcode;
    private String session_id;
    private String face_id;
    private String errormsg;
    private List<String> group_ids;

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public int getSuc_group() {
        return suc_group;
    }

    public void setSuc_group(int suc_group) {
        this.suc_group = suc_group;
    }

    public int getSuc_face() {
        return suc_face;
    }

    public void setSuc_face(int suc_face) {
        this.suc_face = suc_face;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<String> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<String> group_ids) {
        this.group_ids = group_ids;
    }

    @Override
    public String toString() {
        return "ResponseCreatePerson{" +
                "person_id='" + person_id + '\'' +
                ", suc_group=" + suc_group +
                ", suc_face=" + suc_face +
                ", errorcode=" + errorcode +
                ", session_id='" + session_id + '\'' +
                ", face_id='" + face_id + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", group_ids=" + group_ids +
                '}';
    }
}
