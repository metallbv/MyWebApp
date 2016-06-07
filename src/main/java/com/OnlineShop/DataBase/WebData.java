package com.OnlineShop.DataBase;

/**
 * Created by v.babiak on 06.06.2016.
 */
public class WebData {
    private String name;
    private Integer count;
    private Float sum;

    public WebData() {
    }

    public WebData(String name, Integer count, Float sum) {
        this.name = name;
        this.count = count;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

}
