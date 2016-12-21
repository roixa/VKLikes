
package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPhotosResponse {

    @SerializedName("response")
    @Expose
    private AllPhotosInnerResponse response;

    public AllPhotosInnerResponse getResponse() {
        return response;
    }

    public void setResponse(AllPhotosInnerResponse allPhotosInnerResponse) {
        this.response = allPhotosInnerResponse;
    }

}
