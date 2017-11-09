package com.nxt.net.zhifutongqb.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.nxt.net.zhifutongqb.R;
import com.nxt.net.zhifutongqb.utils.CommonUtils;
import com.nxt.net.zhifutongqb.widget.LoadingDialog;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Fragment基类,实现onCreateView方法
 * 使用时须在onAttach()中设置layoutResId,并在initView()中初始化控件
 *
 * @author koneloong koneloong@gmail.com
 *         Created on 2015/8/6 10:53.
 * @version 1.0
 * @since 1.0
 */
public abstract class ZBaseFragment extends Fragment implements View.OnClickListener, Callback {
    private View mView;
    protected LoadingDialog loadingDialog;
    protected Unbinder unbinder;
    protected boolean isnetconnected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        } else {
            mView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mView);
        isnetconnected = CommonUtils.isNetWorkConnected(getActivity());
        initView(mView);
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void showDialog() {
        loadingDialog = new LoadingDialog(getActivity(), getString(R.string.is_loading));
        loadingDialog.show();
    }

    protected void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
    }


    /**
     * 初始化界面
     *
     * @since 1.0
     */
    protected abstract void initView(View view);

    /**
     * @return 布局文件资源ID
     * @since 1.0
     */
    protected abstract int getLayoutId();

    protected void setStatusBar() {
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */

    public String getName() {
        return BaseFragment.class.getName();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFailure(Call call, IOException e) {
        onError(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            try {
                handler.sendMessage(handler.obtainMessage(0, response.body().string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            handler.sendMessage(handler.obtainMessage(1, response.code() + ""));
        }

    }

    public void onResult(String string) {

    }

    public void onError(String error) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            switch (msg.what) {
                case 0:
                    onResult(res);
                    break;
                case 1:
                    onError(res);
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
