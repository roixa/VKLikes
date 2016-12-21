
package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAlbumsResponse {

    @SerializedName("response")
    @Expose
    private AllAlbumsInnerResponse allAlbumsInnerResponse;

    public AllAlbumsInnerResponse getAllAlbumsInnerResponse() {
        return allAlbumsInnerResponse;
    }

    public void setAllAlbumsInnerResponse(AllAlbumsInnerResponse allAlbumsInnerResponse) {
        this.allAlbumsInnerResponse = allAlbumsInnerResponse;
    }

}
