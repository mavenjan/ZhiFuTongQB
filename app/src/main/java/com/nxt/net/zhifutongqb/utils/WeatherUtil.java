package com.nxt.net.zhifutongqb.utils;

import android.text.TextUtils;


import com.nxt.net.zhifutongqb.R;

import java.lang.reflect.Field;

/**
 * Created by zhangyonglu on 2016/2/26 0026.
 */
public class WeatherUtil {
    private static CharacterParser characterParser;

    //指数数据
    public static String getImage(String weather) {
        characterParser = CharacterParser.getInstance();
        if (!TextUtils.isEmpty(weather)) {
            if (weather.contains("转")) {
                return characterParser.getSelling(weather.split("转")[1]);
            } else {
                return characterParser.getSelling(weather);
            }
        }
        return null;
    }

    public static int getImageresource(String weather) {
        String pic = getImage(weather);

        if (pic == null || pic.trim().equals("")) {
            return R.drawable.biz_plugin_weather_qing;
        }
        pic = "biz_plugin_weather_" + pic;
        Class draw = R.drawable.class;
        try {
            Field field = draw.getDeclaredField(pic);
            return field.getInt(pic);
        } catch (SecurityException e) {
            return R.drawable.biz_plugin_weather_qing;
        } catch (NoSuchFieldException e) {
            return R.drawable.biz_plugin_weather_qing;
        } catch (IllegalArgumentException e) {
            return R.drawable.biz_plugin_weather_qing;
        } catch (IllegalAccessException e) {
            return R.drawable.biz_plugin_weather_qing;
        }
    }
}
