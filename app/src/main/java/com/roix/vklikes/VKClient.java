package com.roix.vklikes;

import android.util.Log;

import com.roix.vklikes.pojo.firebase.FirebaseLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseTasksSet;
import com.roix.vklikes.pojo.vk.AddLikeResponse;
import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.AllPhotosResponse;
import com.roix.vklikes.pojo.vk.GetPhotosByAlbumResponse;
import com.roix.vklikes.pojo.vk.Photo;
import com.roix.vklikes.pojo.vk.User;
import com.roix.vklikes.pojo.vk.UserInfoResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roix on 20.12.2016.
 */

public class VKClient implements MVP.VKClientModel {

    private MVP.RootPresenter presenter;
    private VKApi api;
    private String accessToken;
    private String TAG="VKClient";

    private User owner;
    private AllAlbumsResponse ownerAlbums;
    private List<Photo> choosedPhotos;


    public VKClient(MVP.RootPresenter presenter,String accessToken) {
        this.presenter = presenter;
        this.accessToken=accessToken;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(VKApi.class);

    }

    @Override
    public void loadOwnerById(String vkId) {
        api.geUserInfo(accessToken, vkId, Constants.USER_INFO_FIELDS, "5.8").enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if(response.isSuccessful()){
                    owner = response.body().getUser().get(0);
                    presenter.onLoadVkUser(owner);

                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadOwnerAlbums(String vkId) {
        api.getAllAlbums(accessToken, vkId, "5.6", true, true).enqueue(new Callback<AllAlbumsResponse>() {
            @Override
            public void onResponse(Call<AllAlbumsResponse> call, Response<AllAlbumsResponse> response) {
                ownerAlbums = response.body();
                presenter.onLoadAlbums(response.body());
            }

            @Override
            public void onFailure(Call<AllAlbumsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadPhotosByAlbum(Album album) {
        api.getPhotosByAlbum(accessToken,album.getOwnerId()+"","5.60",album.getId()+"",true,true,true,"0","50").enqueue(new Callback<GetPhotosByAlbumResponse>() {
            @Override
            public void onResponse(Call<GetPhotosByAlbumResponse> call, Response<GetPhotosByAlbumResponse> response) {
                if(response.isSuccessful()){
                    choosedPhotos=response.body().getResponse().getItems();
                    presenter.onLoadPhotosByAlbum(choosedPhotos);

                }
                else presenter.onError(0,"loadPhotosByAlbum !isSuccessful");
            }

            @Override
            public void onFailure(Call<GetPhotosByAlbumResponse> call, Throwable t) {
                presenter.onError(0,"loadPhotosByAlbum onFailure");
            }
        });
    }

    @Override
    public void loadAllPhotosById(String vkId) {
        api.getAllPhotos(accessToken,vkId,"5.6",true,true,0+"",50+"").enqueue(new Callback<AllPhotosResponse>() {
            @Override
            public void onResponse(Call<AllPhotosResponse> call, Response<AllPhotosResponse> response) {
                if(response.isSuccessful()){
                    choosedPhotos=response.body().getResponse().getItems();
                    presenter.onLoadAllPhotos(choosedPhotos);
                }
                else Log.d(TAG,"loadAllPhotosById !response.isSuccessful");
            }

            @Override
            public void onFailure(Call<AllPhotosResponse> call, Throwable t) {
                Log.d(TAG,"loadAllPhotosById onFailure");
            }
        });
    }

    @Override
    public void addLike(final FirebaseTasksSet tasksSet, final int likedPos) {
        FirebaseLikeTask task=tasksSet.getTasks().get(likedPos);
        api.addLike(accessToken,task.getOwnerID(),"5.6",task.getPhotoID(),"photo").enqueue(new Callback<AddLikeResponse>() {
            @Override
            public void onResponse(Call<AddLikeResponse> call, Response<AddLikeResponse> response) {
                Log.d(TAG,"addLike "+response.isSuccessful());

                if(response.isSuccessful())
                    presenter.onAddLikeResponse(tasksSet,likedPos);
            }

            @Override
            public void onFailure(Call<AddLikeResponse> call, Throwable t) {
                Log.d(TAG,"addLike onFailure ");
            }
        });
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public AllAlbumsResponse getOwnerAlbums() {
        return ownerAlbums;
    }

    public List<Photo> getChoosedPhotos() {
        return choosedPhotos;
    }
}
