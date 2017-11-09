package com.nxt.net.zhifutongqb.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.utils.StrictModeWrapper;
import com.nxt.net.zhifutongqb.base.BaseTitleActivity;
import com.nxt.net.zhifutongqb.utils.CommonUtils;
import com.nxt.net.zhifutongqb.utils.ZToastUtils;


/**
 * Update by iwon on 2016/6/19 20:25.
 */

public class WebActivity extends BaseTitleActivity {
    public WebView mWebView;
    private ImageView mImgback;
    private Intent intent;
    boolean isfirsarload = true;
    private ImageView addimg;
    private static final int REQUEST_CODE = 1;
    private String[] rechargeList = new String[3], rechargeurllist = new String[3];
    String title, url;
    private SwipeRefreshLayout refreshLayout;
    private Spinner chargesp;
    private ArrayAdapter<String> rechargeAdapter;
    private int tag;


    @Override
    protected void initView() {
        application.addActivity(this);
        intent = getIntent();
        MyApplication.getInstance().addActivity(this);
        StrictModeWrapper.init(getApplicationContext());
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                viewInfo();
            }
        });
        mWebView = (WebView) findViewById(R.id.webview);
        /**
         * 初始化webview设置
         */
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebView.setInitialScale(25);//
        mWebView.requestFocus();
        url = intent.getStringExtra("url");

        viewInfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    public void viewInfo() {
        if (CommonUtils.isNetWorkConnected(this)) {
            setWebViewConfig();
            return;
        }
        ZToastUtils.showShort(this, R.string.net_error);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewConfig() {

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isfirsarload) {
                    isfirsarload = false;
                    return false;
                } else {
                    startActivity(new Intent(WebActivity.this, WebActivity.class).putExtra("title", "详情").putExtra("url", url));
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //
            return true;
        } else {
            finish();
        }
        return false;
    }

    public void refresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                handler.sendEmptyMessageDelayed(0, 500);

            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewInfo();
            super.handleMessage(msg);
        }
    };
}