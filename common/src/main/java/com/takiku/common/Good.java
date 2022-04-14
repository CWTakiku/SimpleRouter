package com.takiku.common;

import androidx.annotation.NonNull;

/**
 * author:chengwl
 * Description: 商品实体类
 * Date:2022/4/14
 */
public class Good {
    private String goodName;
    private String buyUserName;
    private String buyTime;

    public Good(String buyUserName,String goodName,String buyTime){
        this.buyTime = buyTime;
        this.buyUserName = buyUserName;
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodName='" + goodName + '\'' +
                ", buyUserName='" + buyUserName + '\'' +
                ", buyTime='" + buyTime + '\'' +
                '}';
    }

    public String getGoodName() {
        return goodName == null ? "" : goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getBuyUserName() {
        return buyUserName == null ? "" : buyUserName;
    }

    public void setBuyUserName(String buyUserName) {
        this.buyUserName = buyUserName;
    }

    public String getBuyTime() {
        return buyTime == null ? "" : buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}
