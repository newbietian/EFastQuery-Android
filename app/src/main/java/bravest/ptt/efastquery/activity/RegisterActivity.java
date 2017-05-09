package bravest.ptt.efastquery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bravest.ptt.androidlib.activity.BaseActivity;
import bravest.ptt.efastquery.R;
import bravest.ptt.androidlib.utils.PLog;
import bravest.ptt.androidlib.utils.UserUtil;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity{

    private static final String TAG = "RegisterActivity";

    private EditText mPhoneNumberEditor;

    private EditText mVerificationEditor;

    private Button mVerificationSender;

    private EditText mPasswordEditor;

    private Button mRegister;

    private TextView mRegisterAlready;

    private RegisterSaveListener mSaveListener;

    @Override
    protected void initVariables() {
        mSaveListener = new RegisterSaveListener();
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);

        mPhoneNumberEditor = (EditText) findViewById(R.id.phoneNumber);
        mVerificationEditor = (EditText) findViewById(R.id.verification_code);
        mVerificationSender = (Button) findViewById(R.id.verification_code_send);
        mPasswordEditor = (EditText) findViewById(R.id.passWord);
        mRegister = (Button) findViewById(R.id.register);
        mRegisterAlready = (TextView) findViewById(R.id.register_already);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterClick();
            }
        });

        mRegisterAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterAlreadyClick();
            }
        });
    }

    @Override
    protected void initData() {
    }

    private void handleRegisterClick() {
        BmobUser user = new BmobUser();
        String phoneNumber =  mPhoneNumberEditor.getText().toString();
        String password =  mPasswordEditor.getText().toString();
        PLog.d(TAG, "onClick: phonenumber = " + phoneNumber);
        PLog.d(TAG, "onClick: password = " + password );
        user.setMobilePhoneNumber(phoneNumber);
        user.setUsername(UserUtil.generateUserName(UserUtil.USER));
        user.setPassword(password);
        user.signUp(mSaveListener);
    }

    private void handleRegisterAlreadyClick() {

    }

    private class RegisterSaveListener extends SaveListener<BmobUser> {
        @Override
        public void done(BmobUser o, BmobException e) {
            if (e == null) {
                Log.d(TAG, "done: success");
                finish();
            } else {
                Log.d(TAG, "done: e = " + e);
                Log.d(TAG, "done: failure");
            }
        }
    }
}
