package com.example.appshopdrink.Model;

public class CheckUserReponse {
    private boolean exists;

    private String error_msg;

    public CheckUserReponse(){
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
