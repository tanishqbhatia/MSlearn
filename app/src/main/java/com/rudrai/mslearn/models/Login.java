package com.rudrai.mslearn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tanishq Bhatia on 1/17/2018 at 5:46 PM.
 * Contact at tanishqbhatia1995@gmail.com or +919780702709
 */

public class Login {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("user")
    @Expose
    private User user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}