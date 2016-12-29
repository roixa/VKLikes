package com.roix.vklikes;

import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
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

    private User owner=null;
    private AllAlbumsResponse ownerAlbums=null;


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
        api.geUserInfo(accessToken,vkId,Constants.USER_INFO_FIELDS,"5.8").enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                owner=response.body().getUser().get(0);
                presenter.onLoadVkUser(owner);
            }
            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadOwnerAlbums(String vkId) {
        api.getAllAlbums(accessToken,vkId,"5.6",true,true).enqueue(new Callback<AllAlbumsResponse>() {
            @Override
            public void onResponse(Call<AllAlbumsResponse> call, Response<AllAlbumsResponse> response) {
                ownerAlbums=response.body();
                presenter.onLoadAlbums(response.body());
            }

            @Override
            public void onFailure(Call<AllAlbumsResponse> call, Throwable t) {

            }
        });
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }




}
