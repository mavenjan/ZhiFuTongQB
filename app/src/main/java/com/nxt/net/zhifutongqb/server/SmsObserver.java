package com.nxt.net.zhifutongqb.server;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.nxt.net.zhifutongqb.ui.activity.RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @aouther Maven
 * 观察者模式监听收件箱短信变化，自动填写验证码方法
 */
public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    /**
     * 重写父类的方法。
     * 当被观察者发生变化时自动调用该函数
     * @param selfChange 被观察者本身是否发生变化
     * @param uri 对应的uri
     */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        String code = "";

        Log.e("DEBUG", "Sms has changed");
        Log.e("DEBUG",uri.toString());

        //没有消息变化的时候不读取uri
        if (uri.toString().equals("content://sms/raw")){
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");    //指向收件箱的uri

        Cursor c = mContext.getContentResolver().query(inboxUri,null,null,null,"date desc");  //date desc 日期倒序排列
        if (c != null){
            if (c.moveToFirst()){

                String address = c.getString(c.getColumnIndex("address"));      //获得发件人
                String body = c.getString(c.getColumnIndex("body"));            //获得短信主题

                Log.e("DEBUG","发件人:" + address + " " + "短信内容:" + body);


//                //验证验证码的来源(如果唯一的话)
//                if (!address.equals("15555215554")){
//                    return;
//                }

                Pattern pattern = Pattern.compile("(\\d{6})");      //创建正则表达式获取连续的6个数字
                Matcher matcher = pattern.matcher(body);            //生成matcher对象来捕获body中的内容

                if (matcher.find()){

                    code = matcher.group(0);        //0对应正则表达式中第一个括号中的内容
                    Log.e("DEBUG", "code is" + " " + code);

                    //mHandler获取msg(what，obj).发送到目标对象
                    mHandler.obtainMessage(RegisterActivity.MSG_RECIVED_CODE,code).sendToTarget();
                }
            }
            c.close();
        }
    }
}