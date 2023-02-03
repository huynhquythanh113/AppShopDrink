package com.example.appshopdrink.Adapter;

import com.example.appshopdrink.Utils.Common;

public class Drink {
    public String name;
    public String image;
    public int price;
    public int countsell;
    public int id;

    public Drink(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
    public Drink(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountsell() {
        return countsell;
    }

    public void setCountsell(int countsell) {
        this.countsell = countsell;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        String a = Common.url +"/AppShopTraSua/admincp/modules/quanlysanpham/hinhanh/" + image;
        return a;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
