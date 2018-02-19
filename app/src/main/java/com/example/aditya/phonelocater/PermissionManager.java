package com.example.aditya.phonelocater;

/**
 * Created by aditya on 15/2/18.
 */
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager {

    private static final int PERMISSION_CODE = 0;

    public static boolean checkPermission(Context context,String permissionName) {
        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    public static void grantPermission(Activity context, String permissionName) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissionName)) {
            return;
        }
        ActivityCompat.requestPermissions(context, new String[]{permissionName},
                PERMISSION_CODE);
    }

    public static void grantAllPermissions(Activity context, String[] permissionList){
        ActivityCompat.requestPermissions(context, permissionList ,
                PERMISSION_CODE);
    }

}
