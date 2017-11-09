package com.nxt.net.zhifutongqb.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.app.API;
import com.nxt.net.zhifutongqb.utils.ZPreferenceUtils;


/**
 * Created by Maven on 2016/10/01 0002.
 */
public class SplashActivity extends AppCompatActivity {

    private boolean isFirstIn = false;
    private boolean isLogin = false;
    private static final int TIME = 2000;
    private static final int GO_HOME = 998;
    private static final int GO_LOGIN = 999;
    private static final int GO_GUIDE = 1000;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case GO_LOGIN:
                    setGoLogin();
                    break;

                case GO_HOME:
                    setGoHome();
                    break;

                case GO_GUIDE:
                    setGoGuide();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags =
                    (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        init();
    }

    //判断是否加载引导界面
    private void init() {
        isFirstIn = ZPreferenceUtils.getPrefBoolean("isFirstIn", true);
        isLogin = ZPreferenceUtils.getPrefBoolean(API.IS_LOGIN, false);
        if (!isFirstIn && !isLogin) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
        } else if (!isFirstIn && isLogin) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            ZPreferenceUtils.setPrefBoolean("isFirstIn", false);
        }
    }

    private void setGoLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void setGoHome() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void setGoGuide() {
        Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
        startActivity(i);
        finish();
    }
}

