package com.example.appshopdrink.Adapter;

public class DonHang {
    public String id;
    public String date;
    public int Tongtien;
    public String diachi;
    public String phone;
    public String name;
    public String detailorder;
    public String cmt;
    public String status;
    public DonHang(String id, String date, int tongtien, String diachi, String phone, String name, String detailorder ,String cmt , String status) {
        this.id = id;
        this.date = date;
        Tongtien = tongtien;
        this.diachi = diachi;
        this.phone = phone;
        this.name = name;
        this.detailorder = detailorder;
        this.cmt = cmt;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DonHang() {

    }

    public void setCmt(String cmt){
        this.cmt = cmt;
    }
    public String getCmt(){
        return cmt;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTongtien() {
        return Tongtien;
    }

    public void setTongtien(int tongtien) {
        Tongtien = tongtien;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailorder() {
        return detailorder;
    }

    public void setDetailorder(String detailorder) {
        this.detailorder = detailorder;
    }
}
