package com.nxt.net.zhifutongqb.bean;

/**
 * 当前类注释:模拟Gallery提供数据的实体类
 * 项目名：FastDev4Android
 * 包名：com.chinaztt.fda.entity
 * 作者：江清清 on 15/11/19 20:55
 * 邮箱：jiangqqlmj@163.com
 * QQ： 781931404
 * 公司：江苏中天科技软件技术有限公司
 */
public class GalleryModel {
    private String imgurl;
    private String title;
    private String address;

    public GalleryModel() {
    }

    public GalleryModel(String imgurl, String title, String address) {
        this.imgurl = imgurl;
        this.title = title;
        this.address = address;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GalleryModel{" +
                "imgurl=" + imgurl +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
