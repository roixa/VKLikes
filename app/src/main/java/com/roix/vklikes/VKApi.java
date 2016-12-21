package com.roix.vklikes;

import com.roix.vklikes.pojo.vk.UserInfoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
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
                                  @Query("owner_id") String ownerID, @Query("v") String version,@Query("extended") boolean extended,@Query("photo_sizes") boolean photoSizes);//extended=true photo_sizes=true

    @GET("/method/photos.getAlbums")
    Call<ResponseBody> getAllAlbums(@Query("access_token") String accessToken,
                                    @Query("owner_id") String ownerID, @Query("v") String version, @Query("need_system") boolean needSystem, @Query("need_covers") boolean needCovers);//need_system=true need_covers=true



}
