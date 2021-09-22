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
    public static String GET_CATEGORIES = Constant.Base() + "service-types/";
    public static String REGISTER_AGENT = Constant.Base() + "users/create-agent";
    public static String REGISTER = Constant.Base() + "user/register";
    public static String LOGIN = Constant.Base() + "user/login";
    public static String SUPER_REGISTER_DEALER = Constant.Base() + "users/create-dealer";
    public static String SUPER_REGISTER_SUPERDEALER = Constant.Base() + "users/create-superdealer";
    public static String DEALER_LIST = Constant.Base() + "dealers";
    public static String GET_WALLET_LIST = Constant.Base() + "/users/wallets";
    public static String AGENT_LIST = Constant.Base() + "agents";
    public static String VENDOR_LIST = Constant.Base() + "vendors";
    public static String GET_PROFILE = Constant.Base() + "users/profile";
    public static String GET_ALL_TRAANSCTIOON = Constant.Base() + "vending/logs";
    public static String GET_COMMISION_AHEAD_REPORT = Constant.Base() + "report/commission-ahead-profits";
    public static String WALLET_BALANCE = Constant.Base() + "wallets";
    public static String VENDING_AIRTIME = Constant.Base() + "Buy/buy-recharge";
    public static String VENDING_DATA = Constant.Base() + "Buy/buy-data";
    public static String VENDING_ELECTRICITY = Constant.Base() + "Buy/buy-electricity";
    public static String VENDING_CONFIRM = Constant.Base() + "Vending";
    public static String VENDING_COMPLETE = Constant.Base() + "Vending/Complete";
    public static String VENDING_SLIP = Constant.Base() + "vending/";
    public static String FUNDING_REQUEST = Constant.Base() + "fundingRequest";
    public static String FUNDING_REQUEST_HISTORY = Constant.Base() + "fundingRequest/history";
    public static String SUPER_LIST = Constant.Base() + "superdealers";
    public static String UPDATE_SUPER_ADMIN = Constant.Base() + "dealers";
    public static String ApproveUrl = Constant.Base() + "fundingRequest";
    public static String edit_user_profile = Constant.Base() + "users/profile";
    public static String GET_DASHBOARD = Constant.Base() + "users/dashboard";
    public static String GETBANKDETAILS = Constant.Base() + "wallet/getWalletBalance";
    public static String GETBANKDETAILS_GENERATE = Constant.Base() + "users/generate-reservedBankAccount";
    public static String FETCH_BANK_DETAILS = Constant.Base() + "banktransfer/fetchBank";
    public static String UserDeactivated = Constant.Base() + "users/isdeactivated";
    public static String MONEYTRANSFER = Constant.Base() + "banktransfer/transfer";
    public static String GET_MONEY_TRANSFER_REPORT = Constant.Base() + "banktransfer";

}
