package cn.lst.facerecog.entity;

import java.util.List;

/**
 * Created by lisongting on 2017/12/27.
 * 优图 JSON响应对应实体类
 */

public class ResponseQueryPersonInfo {

    /**
     * person_id : 9eb44387923528f97f8545d8bef906db
     * person_name : NicolasCage
     * face_ids : ["199d1efd19ce4ee67a7ec7655f859b1a"]
     * group_ids : ["tencent"]
     * tag : person tag
     * errorcode : 0
     * session_id : session_id
     * errormsg : ok
     */

    private String person_id;
    private String person_name;
    private String tag;
    private int errorcode;
    private String session_id;
    private String errormsg;
    private List<String> face_ids;
    private List<String> group_ids;

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<String> getFace_ids() {
        return face_ids;
    }

    public void setFace_ids(List<String> face_ids) {
        this.face_ids = face_ids;
    }

    public List<String> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<String> group_ids) {
        this.group_ids = group_ids;
    }

    @Override
    public String toString() {
        return "ResponseQueryPersonInfo{" +
                "person_id='" + person_id + '\'' +
                ", person_name='" + person_name + '\'' +
                ", tag='" + tag + '\'' +
                ", errorcode=" + errorcode +
                ", session_id='" + session_id + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", face_ids=" + face_ids +
                ", group_ids=" + group_ids +
                '}';
    }
}
