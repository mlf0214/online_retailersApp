package com.example.online_retailersapp.bean;

import java.util.List;

public class Commoditys {

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
        private Integer c_id;
        private Double c_price;
        private String c_uri;
        private String c_advertisement;
        private String c_name;
        private String c_type;

        public Integer getC_id() {
            return c_id;
        }

        public void setC_id(Integer c_id) {
            this.c_id = c_id;
        }

        public Double getC_price() {
            return c_price;
        }

        public void setC_price(Double c_price) {
            this.c_price = c_price;
        }

        public String getC_uri() {
            return c_uri;
        }

        public void setC_uri(String c_uri) {
            this.c_uri = c_uri;
        }

        public String getC_advertisement() {
            return c_advertisement;
        }

        public void setC_advertisement(String c_advertisement) {
            this.c_advertisement = c_advertisement;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_type() {
            return c_type;
        }

        public void setC_type(String c_type) {
            this.c_type = c_type;
        }
    }
}
