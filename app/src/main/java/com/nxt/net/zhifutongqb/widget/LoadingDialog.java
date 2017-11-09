package com.nxt.net.zhifutongqb.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nxt.net.zhifutongqb.R;


/**
 * Created by zhangyonglu on 2016/11/23 002316:34
 */

public class LoadingDialog extends Dialog {
    private Context mcontext;
    private String mtitle;
    private int mlayoutid = 0;

    public LoadingDialog(Context context, String title) {
        super(context, R.style.customDialog);
        this.mcontext = context;
        this.mtitle = title;
    }

    public LoadingDialog(Context context, int layoutid, String title) {
        super(context, R.style.customDialog);
        this.mcontext = context;
        this.mtitle = title;
        this.mlayoutid = layoutid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view;
        if (mlayoutid == 0) {
            view = LayoutInflater.from(mcontext).inflate(R.layout.layout_loading_dialog, null);
        } else {
            view = LayoutInflater.from(mcontext).inflate(mlayoutid, null);

        }
        ((TextView) view.findViewById(R.id.tv_loading)).setText(mtitle);
        setContentView(view);
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (d.heightPixels * 0.12); // 高度设置为屏幕的0.6
        lp.width = (int) (d.widthPixels * 0.5); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

    }
}
