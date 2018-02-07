package com.silive.pc.roundtable;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PC on 2/7/2018.
 */

public class Validation {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";

    public static boolean isEmailAddress(EditText editText,TextInputLayout textInputLayout, boolean required) {
        return isValid(editText,textInputLayout, EMAIL_REGEX, EMAIL_MSG, required);
    }


    public static boolean isValid(EditText editText, TextInputLayout textInputLayout, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        textInputLayout.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText, textInputLayout) ) return false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // pattern doesn't match so returning false
        if (required && !matcher.matches()) {
            textInputLayout.setError(errMsg);
            Log.i("output", text);
            return false;
        };
        //Log.i("output", text);
        return true;
    }

    public static boolean hasText(EditText editText, TextInputLayout textInputLayout) {

        String text = editText.getText().toString().trim();
        textInputLayout.setError(null);

        //Log.i("text", text);
        // length 0 means there is no text
        if (text.length() == 0) {
            textInputLayout.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}
