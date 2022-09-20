package com.bnadeau.fractalengine.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public final class Utils {


    public static String colorToHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }


    public static EditText getEditText (View v, int id) {
        return v.findViewById(id);
    }
    public static String getEditTextValue(View v, int id) {
        return getEditText(v, id).getText().toString();
    }
    public static void setEditTextValue(View v, int id, String value) {
        getEditText(v, id).setText(value);
    }

    public static void setEditTextValue(View v, int id, int value) {
        setEditTextValue(v, id, String.valueOf(value));
    }


    public static EditText getEditText (Activity activity, int id) {
        return activity.findViewById(id);
    }
    public static String getEditTextValue(Activity activity, int id) {
        return getEditText(activity, id).getText().toString();
    }

    public static void setEditTextValue(Activity activity, int id, String value) {
        getEditText(activity, id).setText(value);
    }

    public static void setEditTextValue(Activity activity, int id, int value) {
       setEditTextValue(activity, id, String.valueOf(value));
    }


    public static Button getButton(Activity activity, int id) {
        return activity.findViewById(id);
    }

    public static void changeButtonColor(Activity activity, int id, int color) {
        getButton(activity, id).setBackgroundColor(color);
    }

}
