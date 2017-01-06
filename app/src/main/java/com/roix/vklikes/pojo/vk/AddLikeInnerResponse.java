
package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddLikeInnerResponse {

    @SerializedName("likes")
    @Expose
    private Integer likes;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}
