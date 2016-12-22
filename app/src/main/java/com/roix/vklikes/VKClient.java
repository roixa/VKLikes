package com.roix.vklikes;

import com.roix.vklikes.pojo.vk.User;
import com.roix.vklikes.pojo.vk.UserInfoResponse;

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
    public void loadUserById(String vkId) {
        api.geUserInfo(accessToken,vkId,Constants.USER_INFO_FIELDS,"5.8").enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                final User user=response.body().getUser().get(0);
                presenter.onLoadVkUser(user);
            }
            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });

    }
}
