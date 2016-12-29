package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roix on 29.12.2016.
 */

public class GetPhotosByAlbumInnerResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<Photo> items = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Photo> getItems() {
        return items;
    }

    public void setItems(List<Photo> items) {
        this.items = items;
    }


}
