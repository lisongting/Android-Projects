package com.paperfish.aitalk;

/**
 * Created by lisongting on 2017/7/28.
 */

public class AIUIParams {

    /**
     * interact : {"interact_timeout":"60000","result_timeout":"5000"}
     * global : {"scene":"main","clean_dialog_history":"auto"}
     * vad : {"vad_enable":"1","engine_type":"meta","res_type":"assets","res_path":"vad/meta_vad_16k.jet"}
     * iat : {"sample_rate":"16000"}
     * speech : {"data_source":"sdk"}
     */

    private InteractBean interact;
    private GlobalBean global;
    private VadBean vad;
    private IatBean iat;
    private SpeechBean speech;

    @Override
    public String toString() {
        return "AIUIParams{" +
                "interact=" + interact +
                ", global=" + global +
                ", vad=" + vad +
                ", iat=" + iat +
                ", speech=" + speech +
                '}';
    }

    public InteractBean getInteract() {
        return interact;
    }

    public void setInteract(InteractBean interact) {
        this.interact = interact;
    }

    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

    public VadBean getVad() {
        return vad;
    }

    public void setVad(VadBean vad) {
        this.vad = vad;
    }

    public IatBean getIat() {
        return iat;
    }

    public void setIat(IatBean iat) {
        this.iat = iat;
    }

    public SpeechBean getSpeech() {
        return speech;
    }

    public void setSpeech(SpeechBean speech) {
        this.speech = speech;
    }

    public static class InteractBean {
        @Override
        public String toString() {
            return "InteractBean{" +
                    "interact_timeout='" + interact_timeout + '\'' +
                    ", result_timeout='" + result_timeout + '\'' +
                    '}';
        }

        public InteractBean(String interact_timeout, String result_timeout) {
            this.interact_timeout = interact_timeout;
            this.result_timeout = result_timeout;
        }

        /**
         * interact_timeout : 60000
         * result_timeout : 5000
         */

        private String interact_timeout;
        private String result_timeout;

        public String getInteract_timeout() {
            return interact_timeout;
        }

        public void setInteract_timeout(String interact_timeout) {
            this.interact_timeout = interact_timeout;
        }

        public String getResult_timeout() {
            return result_timeout;
        }

        public void setResult_timeout(String result_timeout) {
            this.result_timeout = result_timeout;
        }
    }

    public static class GlobalBean {
        @Override
        public String toString() {
            return "GlobalBean{" +
                    "scene='" + scene + '\'' +
                    ", clean_dialog_history='" + clean_dialog_history + '\'' +
                    '}';
        }

        public GlobalBean(String scene, String clean_dialog_history) {
            this.scene = scene;
            this.clean_dialog_history = clean_dialog_history;
        }

        /**
         * scene : main
         * clean_dialog_history : auto
         */

        private String scene;
        private String clean_dialog_history;

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getClean_dialog_history() {
            return clean_dialog_history;
        }

        public void setClean_dialog_history(String clean_dialog_history) {
            this.clean_dialog_history = clean_dialog_history;
        }
    }

    public static class VadBean {
        /**
         * vad_enable : 1
         * engine_type : meta
         * res_type : assets
         * res_path : vad/meta_vad_16k.jet
         */

        private String vad_enable;
        private String engine_type;
        private String res_type;
        private String res_path;

        public VadBean(String vad_enable, String engine_type, String res_type, String res_path) {
            this.vad_enable = vad_enable;
            this.engine_type = engine_type;
            this.res_type = res_type;
            this.res_path = res_path;
        }

        @Override
        public String toString() {
            return "VadBean{" +
                    "vad_enable='" + vad_enable + '\'' +
                    ", engine_type='" + engine_type + '\'' +
                    ", res_type='" + res_type + '\'' +
                    ", res_path='" + res_path + '\'' +
                    '}';
        }

        public String getVad_enable() {
            return vad_enable;
        }

        public void setVad_enable(String vad_enable) {
            this.vad_enable = vad_enable;
        }

        public String getEngine_type() {
            return engine_type;
        }

        public void setEngine_type(String engine_type) {
            this.engine_type = engine_type;
        }

        public String getRes_type() {
            return res_type;
        }

        public void setRes_type(String res_type) {
            this.res_type = res_type;
        }

        public String getRes_path() {
            return res_path;
        }

        public void setRes_path(String res_path) {
            this.res_path = res_path;
        }
    }

    public static class IatBean {
        /**
         * sample_rate : 16000
         */

        private String sample_rate;

        public IatBean(String sample_rate) {
            this.sample_rate = sample_rate;
        }

        @Override
        public String toString() {
            return "IatBean{" +
                    "sample_rate='" + sample_rate + '\'' +
                    '}';
        }

        public String getSample_rate() {
            return sample_rate;
        }

        public void setSample_rate(String sample_rate) {
            this.sample_rate = sample_rate;
        }
    }

    public static class SpeechBean {
        public SpeechBean(String data_source) {
            this.data_source = data_source;
        }

        @Override
        public String toString() {
            return "SpeechBean{" +
                    "data_source='" + data_source + '\'' +
                    '}';
        }

        /**
         * data_source : sdk
         */

        private String data_source;

        public String getData_source() {
            return data_source;
        }

        public void setData_source(String data_source) {
            this.data_source = data_source;
        }
    }
}
