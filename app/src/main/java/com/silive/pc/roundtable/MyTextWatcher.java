package com.silive.pc.roundtable;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by PC on 2/7/2018.
 */

public class MyTextWatcher implements TextWatcher {

    private EditText mEditText;
    private TextInputLayout mTextInputLayout;

    public MyTextWatcher(EditText editText, TextInputLayout textInputLayout){
        this.mEditText = editText;
        this.mTextInputLayout = textInputLayout;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (mEditText.getId()) {
            case R.id.log_in_input_email:
                Validation.isEmailAddress(mEditText,mTextInputLayout,true);
                break;
            case R.id.log_in_input_password:
                Validation.hasText(mEditText, mTextInputLayout);
                break;
            case R.id.sign_up_input_email:
                Validation.isEmailAddress(mEditText,mTextInputLayout,true);
                break;
            case R.id.sign_up_input_name:
                Validation.hasText(mEditText, mTextInputLayout);
                break;
            case R.id.sign_up_input_password:
                Validation.hasText(mEditText, mTextInputLayout);
        }
    }
}
