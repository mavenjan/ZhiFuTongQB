package com.nxt.net.zhifutongqb.utils;

import android.content.Context;

/**
 * Created by zhangyonglu on 2017/2/11.
 * dec:px ,dip ,sp 的转换
 */

public class DensityUtil {
        public DensityUtil() {
        }

        public static int dip2px(Context var0, float var1) {
            float var2 = var0.getResources().getDisplayMetrics().density;
            return (int)(var1 * var2 + 0.5F);
        }

        public static int px2dip(Context var0, float var1) {
            float var2 = var0.getResources().getDisplayMetrics().density;
            return (int)(var1 / var2 + 0.5F);
        }

        public static int sp2px(Context var0, float var1) {
            float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
            return (int)(var1 * var2 + 0.5F);
        }

        public static int px2sp(Context var0, float var1) {
            float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
            return (int)(var1 / var2 + 0.5F);
        }
    }

