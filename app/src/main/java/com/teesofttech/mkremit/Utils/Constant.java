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
    public static String VENDING_ELECTRICITY_COMPLETE = Constant.Base() + "Vending/vending-electricity-complete";
    public static String VENDING_DATA_COMPLETE = Constant.Base() + "Vending/vending-data-complete";
    public static String VENDING_EDUCATION_COMPLETE = Constant.Base() + "Vending/vending-education-complete";
    public static String VENDING_CABLE_COMPLETE = Constant.Base() + "Vending/vending-cable-complete";


    public static String VENDING_AIRTIME_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-recharge-complete-by-wallet";
    public static String VENDING_ELECTRICITY_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-electricity-complete-by-wallet";
    public static String VENDING_DATA_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-data-complete-by-wallet";
    public static String VENDING_EDUCATION_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-education-complete-by-wallet";
    public static String VENDING_CABLE_COMPLETE_BY_WALLET = Constant.Base() + "Vending/vending-cable-complete-by-wallet";

    public static String VENDING_AIRTIME = Constant.Base() + "Buy/buy-recharge";
    public static String VENDING_DATA = Constant.Base() + "Buy/buy-data";
    public static String VENDING_EDUCATION = Constant.Base() + "Buy/buy-education";
    public static String VENDING_CABLE = Constant.Base() + "Buy/buy-cable";
    public static String VENDING_ELECTRICITY = Constant.Base() + "Buy/buy-electricity";


    public static String GETBANKDETAILS = Constant.Base() + "wallet/getWalletBalance";
    public static String FUNDWALLET = Constant.Base() + "Wallet/fundWallet";
    public static String GETWALLETHISTORY = Constant.Base() + "Wallet/getWalletByUserId";
    public static String GETTRANSACTIONS = Constant.Base() + "Transaction/getTransactionHistory";

    public static String SENDMONEY = Constant.Base() + "Wallet/initiatie-bank-transfer";

}
