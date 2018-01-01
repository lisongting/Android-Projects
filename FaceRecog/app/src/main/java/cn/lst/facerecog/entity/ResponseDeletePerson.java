package cn.lst.facerecog.entity;

/**
 * Created by lisongting on 2017/12/27.
 * 优图 JSON响应对应实体类
 */

public class ResponseDeletePerson {

    /**
     * deleted : 1
     * person_id : “person0”
     * errorcode : 0
     * session_id : session_id
     * errormsg : ok
     */

    private int deleted;
    private String person_id;
    private int errorcode;
    private String session_id;
    private String errormsg;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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

    @Override
    public String toString() {
        return "ResponseDeletePerson{" +
                "deleted=" + deleted +
                ", person_id='" + person_id + '\'' +
                ", errorcode=" + errorcode +
                ", session_id='" + session_id + '\'' +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }
}
