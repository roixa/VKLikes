
package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddLikeResponse {

    @SerializedName("response")
    @Expose
    private AddLikeInnerResponse addLikeInnerResponse;

    public AddLikeInnerResponse getAddLikeInnerResponse() {
        return addLikeInnerResponse;
    }

    public void setAddLikeInnerResponse(AddLikeInnerResponse addLikeInnerResponse) {
        this.addLikeInnerResponse = addLikeInnerResponse;
    }

}
