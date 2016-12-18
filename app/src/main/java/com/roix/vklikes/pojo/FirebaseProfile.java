package com.roix.vklikes.pojo;

import java.util.List;

/**
 * Created by roix on 17.12.2016.
 */

public class FirebaseProfile {
    private String email;
    private String userId;
    private Integer likeCountIn;
    private Integer likeCountOut;
    private List<String> likePhotoUrls;

    public FirebaseProfile(String email, String userId, Integer likeCountIn, Integer likeCountOut, List<String> likePhotoUrls) {
        this.email = email;
        this.userId = userId;
        this.likeCountIn = likeCountIn;
        this.likeCountOut = likeCountOut;
        this.likePhotoUrls = likePhotoUrls;
    }

    public List<String> getLikePhotoUrls() {
        return likePhotoUrls;
    }

    public void setLikePhotoUrls(List<String> likePhotoUrls) {
        this.likePhotoUrls = likePhotoUrls;
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
