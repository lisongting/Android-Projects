package cn.lst.facerecog.entity;

import java.util.List;

/**
 * Created by lisongting on 2017/12/27.
 */

public class ResponseDeleteFace {

    /**
     * deleted : 1
     * face_ids : ["1006991173632458751"]
     * errorcode : 0
     * session_id : session_id
     * errormsg : ok
     */

    private int deleted;
    private int errorcode;
    private String session_id;
    private String errormsg;
    private List<String> face_ids;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    @Override
    public String toString() {
        return "ResponseDeleteFace{" +
                "deleted=" + deleted +
                ", errorcode=" + errorcode +
                ", session_id='" + session_id + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", face_ids=" + face_ids +
                '}';
    }
}
