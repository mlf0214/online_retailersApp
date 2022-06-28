package com.example.online_retailersapp.bean;

import java.util.List;

public class AllUserAddress {

    private String msg;
    private int code;
    private List<DatasDTO> datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DatasDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasDTO> datas) {
        this.datas = datas;
    }

    public static class DatasDTO {
        private String user_id;
        private String address;
        private String phone;
        private int r_id;
        private String name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
