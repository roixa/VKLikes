package com.roix.vklikes.pojo.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by roix on 17.12.2016.
 */
@IgnoreExtraProperties
public class FirebaseProfile {
    private String email;
    private String userId;
    private Integer likeCountIn;
    private Integer likeCountOut;
    private Integer likeCountBuy;
    private Double rating;

    public FirebaseProfile(String email, String userId, Integer likeCountIn, Integer likeCountOut,Integer likeCountBuy,Double rating) {
        this.email = email;
        this.userId = userId;
        this.likeCountIn = likeCountIn;
        this.likeCountOut = likeCountOut;
        this.likeCountBuy = likeCountBuy;
        this.rating = rating;

    }
    public FirebaseProfile(){

    }


    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("userId", userId);
        result.put("likeCountIn", likeCountIn);
        result.put("likeCountOut", likeCountOut);
        result.put("likeCountBuy", likeCountBuy);
        result.put("rating", rating);
        return result;
    }

    public Integer getLikeCountBuy() {
        return likeCountBuy;
    }

    public void setLikeCountBuy(Integer likeCountBuy) {
        this.likeCountBuy = likeCountBuy;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getLikeCountOut() {
        return likeCountOut;
    }

    public void setLikeCountOut(Integer likeCountOut) {
        this.likeCountOut = likeCountOut;
    }

    public Integer getLikeCountIn() {
        return likeCountIn;
    }

    public void setLikeCountIn(Integer likeCountIn) {
        this.likeCountIn = likeCountIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
