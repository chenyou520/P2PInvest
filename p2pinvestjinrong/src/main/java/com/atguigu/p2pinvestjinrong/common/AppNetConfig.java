package com.atguigu.p2pinvestjinrong.common;

public class AppNetConfig {

    public static final String IPADDRESS = "192.168.0.103";

    public static final String BASE_URL = "http://" + IPADDRESS + ":8080/P2PInvest/";

    public static final String PRODUCT = BASE_URL + "product";//访问“全部理财”产品

    public static final String LOGIN = BASE_URL + "login";//登录

    public static final String INDEX = BASE_URL + "index";//访问“homeFragment”

    public static final String USERREGISTER = BASE_URL + "UserRegister";//访问“homeFragment”

    public static final String FEEDBACK = BASE_URL + "FeedBack";//注册

    public static final String UPDATE = BASE_URL + "update.json";//更新应用
}
