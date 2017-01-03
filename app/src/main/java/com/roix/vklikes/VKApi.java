package com.roix.vklikes;

import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.AllPhotosResponse;
import com.roix.vklikes.pojo.vk.GetPhotosByAlbumResponse;
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
                                      @Query("user_ids") String userID, @Query("fields") String fields, @Query("v") String version);//v="5.8"
    @GET("/method/photos.getAll")
    Call<AllPhotosResponse> getAllPhotos(@Query("access_token") String accessToken,
                                         @Query("owner_id") String ownerID, @Query("v") String version,
                                         @Query("extended") boolean extended, @Query("photo_sizes") boolean photoSizes,
                                         @Query("offset") String offset, @Query("count") String count);//extended=true photo_sizes=true

    @GET("/method/photos.getAlbums")
    Call<AllAlbumsResponse> getAllAlbums(@Query("access_token") String accessToken,
                                         @Query("owner_id") String ownerID, @Query("v") String version,
                                         @Query("need_system") boolean needSystem, @Query("need_covers") boolean needCovers);//need_system=true need_covers=true v="5.60"

    @GET("/method/photos.get")
    Call<GetPhotosByAlbumResponse> getPhotosByAlbum(@Query("access_token") String accessToken, @Query("owner_id") String ownerID,
                                                    @Query("v") String version, @Query("album_id") String albumId, @Query("rev") boolean rev,
                                                    @Query("extended") boolean extended, @Query("photo_sizes") boolean photoSizes,
                                                    @Query("offset") String offset, @Query("count") String count);//rev=true extended=true photo_sizes=true v="5.60"


}
