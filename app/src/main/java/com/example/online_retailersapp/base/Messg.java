package com.example.online_retailersapp.base;

import java.util.Map;

public class Messg {
    private int r_id;


    private String targCard;
    private String c_id;
    private String allprice;
    private String banner_type;
    private String json;
    private String numbers;
    private Map<String,Integer> numbermap;
    private Map<String,String> priceMap;

    public Map<String, Integer> getNumbermap() {
        return numbermap;
    }

    public String getTargCard() {
        return targCard;
    }

    public void setTargCard(String targCard) {
        this.targCard = targCard;
    }

    public void setNumbermap(Map<String, Integer> numbermap) {
        this.numbermap = numbermap;
    }

    public Map<String, String> getPriceMap() {
        return priceMap;
    }

    public void setPriceMap(Map<String, String> priceMap) {
        this.priceMap = priceMap;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }
}
