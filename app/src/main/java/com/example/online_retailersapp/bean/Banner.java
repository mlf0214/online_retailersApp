package com.example.online_retailersapp.bean;

import java.util.List;

public class Banner {

    private String msg;
    private Integer code;
    private List<DatasDTO> datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DatasDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasDTO> datas) {
        this.datas = datas;
    }

    public static class DatasDTO {
        private String b_id;
        private String b_uri;
        private String c_type;

        public String getB_id() {
            return b_id;
        }

        public void setB_id(String b_id) {
            this.b_id = b_id;
        }

        public String getB_uri() {
            return b_uri;
        }

        public void setB_uri(String b_uri) {
            this.b_uri = b_uri;
        }

        public String getC_type() {
            return c_type;
        }

        public void setC_type(String c_type) {
            this.c_type = c_type;
        }
    }
}
