package com.example.online_retailersapp.bean;

import java.util.List;

public class AllordersBean {

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
        private Integer order_id;
        private Integer user_id;
        private String price;
        private String phone;
        private String address;
        private String order_no;
        private String c_name;
        private String number;
        private String img_uri;

        public String getImg_uri() {
            return img_uri;
        }

        public void setImg_uri(String img_uri) {
            this.img_uri = img_uri;
        }

        public Integer getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Integer order_id) {
            this.order_id = order_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
