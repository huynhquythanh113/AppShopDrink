package com.example.appshopdrink.Adapter;

import com.example.appshopdrink.Utils.Common;

public class Category {
    public String image;
    public String name;
    public String id;
    public Category(String image, String name , String price) {
        this.image = image;
        this.name = name;
        this.id = price;
    }

    public String getImage() {
        String a = Common.url +"/AppShopTraSua/admincp/modules/quanlysanpham/hinhanh/" + image;
        return a;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
