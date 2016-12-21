
package com.roix.vklikes.pojo.vk;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPhotosInnerResponse {

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

    public void setItems(List<Photo> photos) {
        this.items = photos;
    }

}
