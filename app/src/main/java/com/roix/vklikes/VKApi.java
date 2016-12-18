package com.roix.vklikes;

import com.roix.vklikes.pojo.UserInfoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roix on 08.12.16.
 */

public interface VKApi {
    @GET("/method/users.get")
    Call<UserInfoResponse> geUserInfo(@Query("access_token") String accessToken,
                                      @Query("user_ids") String userID, @Query("fields") String fields, @Query("v") String version);
    @GET("/method/photos.getAll")
    Call<ResponseBody> getAllPhotos(@Query("access_token") String accessToken,
                                  @Query("user_id") String userID, @Query("v") String version);


}
