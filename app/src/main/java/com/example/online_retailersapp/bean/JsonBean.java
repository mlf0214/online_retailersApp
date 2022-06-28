package com.example.online_retailersapp.bean;

import java.util.List;

public class JsonBean {
    private List<DatasDTO> datas;
    private Double allprice;

    public List<DatasDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasDTO> datas) {
        this.datas = datas;
    }

    public Double getAllprice() {
        return allprice;
    }

    public void setAllprice(Double allprice) {
        this.allprice = allprice;
    }

    public static class DatasDTO {
        private Integer c_count;
        private Integer c_id;
        private String c_name;
        private String c_price;
        private String img_uri;
        private Integer user_id;

        public Integer getC_count() {
            return c_count;
        }

        public void setC_count(Integer c_count) {
            this.c_count = c_count;
        }

        public Integer getC_id() {
            return c_id;
        }

        public void setC_id(Integer c_id) {
            this.c_id = c_id;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_price() {
            return c_price;
        }

        public void setC_price(String c_price) {
            this.c_price = c_price;
        }

        public String getImg_uri() {
            return img_uri;
        }

        public void setImg_uri(String img_uri) {
            this.img_uri = img_uri;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }
    }
}
