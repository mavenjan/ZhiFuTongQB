package com.nxt.net.zhifutongqb.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxt.net.zhifutongqb.R;


/**
 * 标题栏
 *
 */
public class ZTitleBar extends RelativeLayout {

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView titleView;
    protected RelativeLayout titleLayout;

    public ZTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public ZTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZTitleBar(Context context) {
        super(context);
        init(context, null);
    }
    
    private void init(final Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
//        leftLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity)context).finish();
//            }
//        });
        
        parseStyle(context, attrs);
    }
    
    private void parseStyle(Context context, AttributeSet attrs){

        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZTitleBar);
            String title = ta.getString(R.styleable.ZTitleBar_ztitleBarTitle);
            titleView.setText(title);
            
            Drawable leftDrawable = ta.getDrawable(R.styleable.ZTitleBar_ztitleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.ZTitleBar_ztitleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }
        
            Drawable background = ta.getDrawable(R.styleable.ZTitleBar_ztitleBarBackground);
            if(null != background) {
                titleLayout.setBackgroundDrawable(background);
            }
            
            ta.recycle();
        }
    }
    
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }
    
    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }
    
    public void setLeftLayoutClickListener(OnClickListener listener){
        leftLayout.setOnClickListener(listener);
    }
    
    public void setRightLayoutClickListener(OnClickListener listener){
        rightLayout.setOnClickListener(listener);
    }
    
    public void setLeftLayoutVisibility(int visibility){
        leftLayout.setVisibility(visibility);
    }
    
    public void setRightLayoutVisibility(int visibility){
        rightLayout.setVisibility(visibility);
    }
    
    public void setTitle(String title){
        //LogUtils.i("ssss",title+"---------------------");

        titleView.setText(title);
    }
    
    public void setBackgroundColor(int color){
        titleLayout.setBackgroundColor(color);
    }
    
    public RelativeLayout getLeftLayout(){
        return leftLayout;
    }
    
    public RelativeLayout getRightLayout(){
        return rightLayout;
    }
}
