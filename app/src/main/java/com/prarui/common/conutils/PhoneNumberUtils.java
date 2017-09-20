package com.prarui.common.conutils;

import android.text.InputFilter;
import android.widget.EditText;

/**
 * Created by Prarui on 2017/7/10.
 */

public class PhoneNumberUtils {
    public static boolean isPhone(String phone) {
        if (phone.length() != 11)
            return false;
        else if (phone.matches("[0-9]*"))
            return true;
        else return false;
    }
    public static void setMaxNumberLenght(EditText editText, int maxNumberLenght){
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxNumberLenght)});
    }
}
