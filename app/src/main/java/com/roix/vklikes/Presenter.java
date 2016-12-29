package com.roix.vklikes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.User;
import com.roix.vklikes.pojo.vk.UserInfoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roix on 10.12.16.
 */

public class Presenter implements MVP.RootPresenter {
    private MVP.RootView rootView;
    private MVP.ContentView contentView;
    private MVP.State state;
    private VKClient vkClient;
    private FirebaseClient firebaseClient;
    private String TAG="Presenter";
    private String accessToken,userId,email;


    public Presenter(MVP.RootView rootView,String accessToken,String userId,String email){
        Log.d(TAG,"Presenter()");
        this.rootView=rootView;
        this.accessToken=accessToken;
        this.userId=userId;
        this.email=email;
        vkClient=new VKClient(this,accessToken);
        firebaseClient=new FirebaseClient(this);

    }

    @Override
    public void init(){
        Log.d(TAG,"init()");

        state= MVP.State.PROFILE;
        rootView.prepareProfile();
        firebaseClient.singIn();
    }

    @Override
    public void finish() {
        Log.d(TAG,"finish()");
    }

    @Override
    public void navTabProfilePushed() {
        state= MVP.State.PROFILE;
        rootView.prepareProfile();
        vkClient.loadOwnerById(userId);
    }

    @Override
    public void navTabLikesPushed() {
        state= MVP.State.LIKES;
        rootView.prepareLikes();
    }

    @Override
    public void navTabAlbumsPushed() {
        state= MVP.State.TASKS;
        rootView.prepareAlbums();
        vkClient.loadOwnerAlbums(vkClient.getOwner().getId());
    }

    @Override
    public void navTabTopPushed() {
        state= MVP.State.TOP;
        rootView.prepareTop();
    }

    @Override
    public void navTabShopPushed() {
        state= MVP.State.SHOP;
        rootView.prepareShop();
    }

    @Override
    public void updateContent(MVP.ContentView contentView){
        Log.d(TAG,"updateContent()");
        this.contentView=contentView;
    }

    @Override
    public void onLoadVkUser(User user) {
        Log.d(TAG,"onLoadVkUser(User user)");
        user.setEmail(email);
        contentView.loadContent(this,user);
        rootView.prepareDrawer(user);
        firebaseClient.listenUser(user);
    }

    @Override
    public void onLoadAlbums(AllAlbumsResponse response) {
        Log.d(TAG,"onLoadAlbums() "+response.getAllAlbumsInnerResponse().getAlba().size());
        contentView.loadContent(this,response);

    }

    @Override
    public void onFirebaseAuth() {
        Log.d(TAG,"onFirebaseAuth()");
        vkClient.loadOwnerById(userId);
    }

    @Override
    public void onUpgradeFirebaseProfile(FirebaseProfile profile) {
        Log.d(TAG,"onUpgradeFirebaseProfile(profile)"+profile.getEmail());

    }


}
