package com.teesofttech.mkremit.Utils;

import android.content.Context;

import com.teesofttech.mkremit.models.PINModel;
import com.teesofttech.mkremit.models.UserModel;


/**
 * Created by BABATUNDE on 8/8/2016.
 */
public class PrefUtils {

    public static void setCurrentUser(UserModel currentDealers, Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentDealers);
        complexPreferences.commit();
    }

    public static UserModel getCurrentUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        UserModel currentUser = complexPreferences.getObject("current_user_value", UserModel.class);
        return currentUser;
    }

    public static void clearUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    //app pin
    public static void setPinUser(PINModel currentDealers, Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "pin_prefs", 0);
        complexPreferences.putObject("pin_user_value", currentDealers);
        complexPreferences.commit();
    }

    public static PINModel getPinUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "pin_prefs", 0);
        PINModel currentUser = complexPreferences.getObject("pin_user_value", PINModel.class);
        return currentUser;
    }

    public static void clearUserPin(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "pin_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

}
