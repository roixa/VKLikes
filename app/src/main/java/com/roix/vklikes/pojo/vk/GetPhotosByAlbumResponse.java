package com.roix.vklikes.pojo.vk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by roix on 29.12.2016.
 */

public class GetPhotosByAlbumResponse {
    @SerializedName("response")
    @Expose
    private GetPhotosByAlbumInnerResponse response;

    public GetPhotosByAlbumInnerResponse getResponse() {
        return response;
    }

    public void setResponse(GetPhotosByAlbumInnerResponse response) {
        this.response = response;
    }

}
