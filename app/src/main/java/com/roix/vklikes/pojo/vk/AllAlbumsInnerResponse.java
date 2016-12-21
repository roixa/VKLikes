
package com.roix.vklikes.pojo.vk;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAlbumsInnerResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<Album> alba = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Album> getAlba() {
        return alba;
    }

    public void setAlba(List<Album> alba) {
        this.alba = alba;
    }

}
