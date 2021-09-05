package com.teesofttech.mkremit.Utils;

import android.os.Environment;

/**
 * Created by phest on 12/24/2017.
 */

public class CheckForSDCard {

    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
