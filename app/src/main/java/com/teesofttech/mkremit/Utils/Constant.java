package com.teesofttech.mkremit.Utils;

/**
 * Created by Babatunde on 10/08/2017.
 */

public class Constant {
    public static String Base() {

        return "http://api.mkremit.com/api/";
    }

    public static String Base2() {

        return "http://api.mkremit.com/";
    }

    public static String Base3() {

        return "http://api.mkremit.com";
    }

    public static String FAILED = "";
    public static String SUCCESS = "";
    public static String DATA = "";
    public static String EMPTY = "EMPTY";
    public static String EmptyMessage = "Please all the empty(s) !!!";
    public static String REGISTER = Constant.Base() + "user/register";
    public static String LOGIN = Constant.Base() + "user/login";
    public static String VENDING_AIRTIME_COMPLETE = Constant.Base() + "Vending/vending-recharge-complete";
    public static String VENDING_AIRTIME_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-recharge-complete-by-wallet";
    public static String VENDING_AIRTIME = Constant.Base() + "Buy/buy-recharge";
    public static String VENDING_DATA = Constant.Base() + "Buy/buy-data";
    public static String VENDING_ELECTRICITY = Constant.Base() + "Buy/buy-electricity";
    public static String GETBANKDETAILS = Constant.Base() + "wallet/getWalletBalance";


}
