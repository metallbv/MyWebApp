package com.OnlineShop.DataBase;

/**
 * Created by v.babiak on 06.06.2016.
 */
public class WebData {
    private String name;
    private Integer cont;
    private Float sum;

    public WebData() {
    }

    public WebData(String name, Integer cont, Float sum) {
        this.name = name;
        this.cont = cont;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCont() {
        return cont;
    }

    public void setCont(Integer cont) {
        this.cont = cont;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

}
