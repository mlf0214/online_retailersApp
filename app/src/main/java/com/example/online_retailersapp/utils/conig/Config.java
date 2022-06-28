package com.example.online_retailersapp.utils.conig;

import com.example.online_retailersapp.utils.Shape;

public class Config {
//    public static final String HOST="http://192.168.31.152:8080";
//    public static final String HOST="http://192.168.1.102:8080";
//    public static final String HOST="http://192.168.42.12:8080";
//    public static final String HOST="http://10.1.33.42:8080";
    public static final String HOST= "http://"+Shape.getValue("host","");
//    public static final String HOST="http://192.168.43.68:8080";
    public static final String login="/user/login";
    public static final String register="/user/register";
    public static final String setPassword="/user/setPassword";
    public static final String setUserData="/user/setUserData";//设置用户信息
    public static final String findAllUserOrders="/user/findAllUserOrders";//查询用户所有订单
    public static final String findAllUserCards="/user/findAllUserCards";//查询用户购物车信息
    public static final String commitOrder="/user/commitOrder";  //生成订单(未支付)
    public static final String paymentUserOrder="/user/paymentUserOrder";//更新已支付订单

    public static final String findAllCommodity="/commodity/findAllCommodity/";//根据类别查询商品列表
    public static final String findCommodity="/commodity/findCommodity/";//根据ID查询商品详情
    public static final String findCommodityOrder="/commodity/findCommodityOrder";//根据类别和 升降序ASC DESC

    public static final String findRecommend="/commodity/findRecommend";//查询推荐商品
            //SELECT * FROM `tableName` ORDER BY RAND() LIMIT 8
    public static final String findBanner="/commodity/banner";//查询首页轮播图
    public static final String addOrder="/user/addOrder";//添加购物车信息
    public static final String selectAllCards="/user/selectAllCards";//查询所有购物车信息


    public static final String batchDeleteCards="/card/batchDeleteCards";//批量删除购车信息
    public static final String findUserData="/user/findUserData";//查询用户信息
    public static final String addUserAddress="/user/addUserAddress";//添加一条收货信息
    public static final String updateUserAddress="/user/updateUserAddress";//修改一条收货信息
    public static final String finAllUserAddress="/user/finAllUserAddress";//查询用户所有收货信息
    public static final String deleteAllUserAddress="/user/deleteUserAddress";//删除地址信息


    public static final String uploadImage="/user/uploadImage";//上传图片到服务器
    public static final String findUserAddressById="/user/findUserAddressById";
    public static final String addUserAddOrders="/user/addUserAddOrders";
    public static final String setUserDataNoImg="/user/setUserDataNoImg";
}
