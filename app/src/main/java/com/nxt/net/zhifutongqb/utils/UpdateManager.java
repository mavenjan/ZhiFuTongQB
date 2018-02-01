package com.nxt.net.zhifutongqb.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.app.API;
import com.nxt.net.zhifutongqb.bean.CheckUpdateInfo;
import com.qiangxi.checkupdatelibrary.dialog.ForceUpdateDialog;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Maven on 2017/4/10.
 * Email: cyjiang_11@163.com
 * Description:版本更新工具类
 */

public class UpdateManager {
    private static final String TAG = "UpdateManager";
    private Context mcontext;
    private boolean isnet;
    AlertDialog updatedialog;
    private String updatecontent;
    private int opreate = 0;//0监测 1更新
    private int versioncode;
    private boolean isauto;

    private ForceUpdateDialog mForceUpdateDialog;
    private CheckUpdateInfo mCheckUpdateInfo;


    public UpdateManager(Context context, boolean auto) {
        this.mcontext = context;
        this.isauto = auto;
    }

    public void checkUpdate() {
        isnet = CommonUtils.isNetWorkConnected(mcontext);
        if (isnet) {
            opreate = 0;

            final int code = new PackageUtil(mcontext).getVersionCode();

            OkGo.get(API.APP_VERSION_URL)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.e(TAG, "onSuccess: result----------->" + s);
                            mCheckUpdateInfo = new Gson().fromJson(s, new TypeToken<CheckUpdateInfo>() {
                            }.getType());
                            if (mCheckUpdateInfo.getVersionCode() > code) {
                                showForceUpdateDialog(mCheckUpdateInfo);
                            } else {
                                if (!isauto) {
                                    ZToastUtils.showShort(mcontext, R.string.is_newest_version);
                                }
                            }
                        }
                    });
        } else {
            ZToastUtils.showShort(mcontext, R.string.net_error);
        }
    }

    /**
     * 强制更新,checkupdatelibrary中提供的默认强制更新Dialog,您完全可以自定义自己的Dialog,
     */
    public void showForceUpdateDialog(CheckUpdateInfo mCheckUpdateInfo) {
        mForceUpdateDialog = new ForceUpdateDialog(mcontext);
        mForceUpdateDialog.setAppSize(mCheckUpdateInfo.getAppSize())
                .setDownloadUrl(API.APP_DOWNLOAD_URL)
                .setTitle(mCheckUpdateInfo.getAppName() + "有更新啦")
                .setReleaseTime(mCheckUpdateInfo.getAppReleaseTime())
                .setVersionName(mCheckUpdateInfo.getAppVersionName())
                .setUpdateDesc(mCheckUpdateInfo.getAppUpdateDesc())
                .setFileName("淇滨区扶贫.apk")
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib").show();
    }
}
