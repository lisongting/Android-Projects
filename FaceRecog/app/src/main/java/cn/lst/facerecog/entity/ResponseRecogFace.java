package cn.lst.facerecog.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lisongting on 2017/12/27.
 */

public class ResponseRecogFace {

    /**
     * session_id : session_id
     * candidates : [{"person_id":"person3","face_id":"1031567119985213439","confidence":54.90695571899414,"\u201ctag\u201d":"\u201cnew tag\u201d"},{"person_id":"person1","face_id":"1031587105968553983","confidence":54.86775207519531,"\u201ctag\u201d":"\u201cnew tag\u201d"}]
     * errorcode : 0
     * errormsg : OK
     */

    private String session_id;
    private int errorcode;
    private String errormsg;
    private List<CandidatesBean> candidates;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<CandidatesBean> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidatesBean> candidates) {
        this.candidates = candidates;
    }

    public static class CandidatesBean {
        /**
         * person_id : person3
         * face_id : 1031567119985213439
         * confidence : 54.90695571899414
         * “tag” : “new tag”
         */

        private String person_id;
        private String face_id;
        private double confidence;
        @SerializedName("“tag”")
        private String _$Tag209; // FIXME check this code

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getFace_id() {
            return face_id;
        }

        public void setFace_id(String face_id) {
            this.face_id = face_id;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public String get_$Tag209() {
            return _$Tag209;
        }

        public void set_$Tag209(String _$Tag209) {
            this._$Tag209 = _$Tag209;
        }

        @Override
        public String toString() {
            return "CandidatesBean{" +
                    "person_id='" + person_id + '\'' +
                    ", face_id='" + face_id + '\'' +
                    ", confidence=" + confidence +
                    ", _$Tag209='" + _$Tag209 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResponseRecogFace{" +
                "session_id='" + session_id + '\'' +
                ", errorcode=" + errorcode +
                ", errormsg='" + errormsg + '\'' +
                ", candidates=" + candidates +
                '}';
    }
}
