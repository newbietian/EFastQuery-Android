package bravest.ptt.efastquery.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bravest.ptt.androidlib.utils.PLog;
import bravest.ptt.efastquery.R;

/**
 * Created by root on 12/27/16.
 */

public class Utils {

    public static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfoList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceInfoList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceInfoList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceInfoList.get(i);
            ComponentName serviceName = serviceInfo.service;
            if (TextUtils.equals(serviceName.getClassName(), className)) {
                return true;
            }
        }
        return false;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return (int) context.getResources().getDimension(resourceId);
    }

    public static void popSoftInput(final Context context, final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        //Delay , otherwise the edit text not ready and the keyboard will not pop up;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, 0);
            }
        }, 300);
    }

    public static void hideSoftInput(Context context, EditText editText) {
        if (!isKeyboardShowing(context)) {
            return;
        }
        editText.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static boolean isKeyboardShowing(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static boolean requestPermissions(Activity activity, int requestCode, String[] permissions) {
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions.size() > 0) {
            activity.requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            return false;
        }
        return true;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permissions) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permissions) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    private static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    //StatusBar
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dp2px(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    public static void getHexagonPoints(float topX, float topY, float length) {
        float x = topX;
        float y = topY;
        //根号3
        final float gen3 = 1.732f;
        PLog.log("左肩: x =  " + (x - (length / 2f) * gen3) + ", y = " + (y + length / 2f));
        PLog.log("右肩：x = " + (x + (length / 2f) * gen3) + ", y = " + (y + length / 2f));
        PLog.log("左跨：x = " + (x - (length / 2f) * gen3) + ", y = " + (y + (3f / 2f) * length));
        PLog.log("右跨：x = " + (x + (length / 2f) * gen3) + ", y = " + (y + (3f / 2f) * length));
        PLog.log("底点：x = " + (x) + ", y = " + (y + 2f * length));
        PLog.log("边长：= " + length);
    }

    public static Bitmap getScreenBitmap(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        int statusBarHeight = getStatusBarHeight(activity);

        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bmp;
    }

    public static Bitmap getScreenBitmapWithoutToolbar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        int statusBarHeight = getStatusBarHeight(activity);
        int actionBarHeight;
        if (activity.getActionBar() != null) {
            actionBarHeight = activity.getActionBar().getHeight();
        } else if (activity instanceof AppCompatActivity) {
            if (((AppCompatActivity) activity).getSupportActionBar() != null) {
                actionBarHeight = ((AppCompatActivity) activity).getSupportActionBar().getHeight();
            } else {
                actionBarHeight = 0;
            }
        } else {
            actionBarHeight = 0;
        }

        PLog.log(actionBarHeight);

        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight + actionBarHeight, width, height - statusBarHeight - actionBarHeight);
        view.destroyDrawingCache();
        return bmp;
    }

    public static final Type getType(Class<?> subclass) {
        Type superClass = subclass.getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }
        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public static void showOverlayConfirmDialog(final Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getString(R.string.overlay_confirm_title))
                        .setMessage(context.getString(R.string.overlay_confirm_content))
                        .setNegativeButton(R.string.overlay_confirm_negative, null)
                        .setPositiveButton(R.string.overlay_confirm_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                intent.setData(Uri.parse("package:bravest.ptt.efastquery"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                builder.setCancelable(false);
                builder.show();
            }
        }
    }
}
