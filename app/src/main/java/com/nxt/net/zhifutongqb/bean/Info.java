package com.nxt.net.zhifutongqb.bean;


import com.nxt.net.zhifutongqb.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable {
    private static final long serialVersionUID = -1010711775392052966L;
    private double latitude;
    private double longitude;
    private int imgId;
    private String name;
    private String distance;
    private int zan;

    public static List<Info> infos = new ArrayList<Info>();

    static {
        infos.add(new Info(114.314315, 35.706667, R.drawable.a01, "道庄村",
                "距离209米", 1456));
        infos.add(new Info(114.286059, 35.733398, R.drawable.a02, "桑庄村",
                "距离897米", 456));
        infos.add(new Info(114.286023, 35.706612, R.drawable.a03, "榆孙村",
                "距离249米", 1456));
        infos.add(new Info(114.286057, 35.706666, R.drawable.a04, "小郑村",
                "距离679米", 1456));

    }

    public Info(double longitude, double latitude, int imgId, String name,
                String distance, int zan) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.zan = zan;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

}
