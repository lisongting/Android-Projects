package cn.lst.facerecog.entity;

import java.util.List;

/**
 * Created by lisongting on 2017/12/27.
 */

public class ResponseAddFace {

    /**
     * added : 1
     * face_ids : ["1001331646826348543"]
     * ret_codes : [0]
     * errorcode : 0
     * session_id : session_id
     * errormsg : ok
     */

    private int added;
    private int errorcode;
    private String session_id;
    private String errormsg;
    private List<String> face_ids;
    private List<Integer> ret_codes;

    public int getAdded() {
        return added;
    }

    public void setAdded(int added) {
        this.added = added;
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

    public List<Integer> getRet_codes() {
        return ret_codes;
    }

    public void setRet_codes(List<Integer> ret_codes) {
        this.ret_codes = ret_codes;
    }

    @Override
    public String toString() {
        return "ResponseAddFace{" +
                "added=" + added +
                ", errorcode=" + errorcode +
                ", session_id='" + session_id + '\'' +
                ", errormsg='" + errormsg + '\'' +
                ", face_ids=" + face_ids +
                ", ret_codes=" + ret_codes +
                '}';
    }
}
