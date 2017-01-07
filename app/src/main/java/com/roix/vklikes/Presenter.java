package com.roix.vklikes;

import android.util.Log;

import com.roix.vklikes.pojo.firebase.FirebaseLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.firebase.FirebaseTasksSet;
import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.Photo;
import com.roix.vklikes.pojo.vk.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private int savedAlbumPos;

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
        vkClient.loadAllPhotosById(userId);

    }

    @Override
    public void finish() {
        Log.d(TAG,"finish()");
        firebaseClient.finish();
    }

    @Override
    public void backButtonPressed() {
        if(state== MVP.State.PROFILE) rootView.close();
        else {
            state=MVP.State.PROFILE;
            rootView.prepareProfile();
            vkClient.loadOwnerById(userId);

        }
    }

    @Override
    public void navTabProfilePushed() {
        state= MVP.State.PROFILE;
        rootView.prepareProfile();
        vkClient.loadOwnerById(userId);
        rootView.showIsProgress(true);
    }

    @Override
    public void navTabLikesPushed() {
        state= MVP.State.LIKES;
        rootView.prepareLikes();
        firebaseClient.loadPhotoLikeTasksSet();
        rootView.showIsProgress(true);
    }

    @Override
    public void navTabAlbumsPushed() {
        state= MVP.State.TASKS;
        rootView.prepareAlbums();
        vkClient.loadOwnerAlbums(vkClient.getOwner().getId());
        rootView.showIsProgress(true);
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
    public void choosedAlbum(Album album) {
        if(album!=null){
            Log.d(TAG,"album() "+album.getTitle()+" "+album.getOwnerId()+" " +album.getId());
            vkClient.loadPhotosByAlbum(album);
        }
        else vkClient.loadAllPhotosById(userId);
        addLikeTasks(3);
        rootView.showIsProgress(true);
    }

    @Override
    public void imageLikeClicked(FirebaseTasksSet tasksSet,int likedPos) {
        Log.d(TAG,"imageLikeClicked");
        firebaseClient.loadPhotoLikeTasksSet();
        vkClient.addLike(tasksSet,likedPos);
        rootView.showIsProgress(true);
    }

    //@TODO add errors handling
    @Override
    public void onError(int code, String err) {

    }

    @Override
    public void onLoadVkUser(User user) {
        Log.d(TAG,"onLoadVkUser(User user)");

        if(state== MVP.State.PROFILE){
            user.setEmail(email);
            contentView.loadContent(this,user);
            rootView.prepareDrawer(user);
            rootView.showIsProgress(false);

        }
    }

    @Override
    public void onLoadAlbums(AllAlbumsResponse response) {
        Log.d(TAG,"onLoadAlbums() "+response.getAllAlbumsInnerResponse().getAlba().size());
        if(state== MVP.State.TASKS){
            contentView.loadContent(this,response);
            rootView.showIsProgress(false);
        }
    }

    @Override
    public void onLoadPhotosByAlbum(List<Photo> response) {
        Log.d(TAG,"onLoadPhotosByAlbum() " +response.size()+" "+vkClient.getChoosedPhotos().size());
        rootView.showIsProgress(false);
    }

    @Override
    public void onLoadAllPhotos(List<Photo> response) {
        Log.d(TAG,"onLoadAllPhotos " +response.size());
        rootView.showIsProgress(false);
    }

    @Override
    public void onAddLikeResponse(FirebaseTasksSet tasksSet,int likedPos) {
        Log.d(TAG,"onAddLikeResponse"+likedPos);
        addLikeTasks(3);
        for (int i=0;i<tasksSet.getTasks().size();i++){
            FirebaseLikeTask task=tasksSet.getTasks().get(i);
            if(i==likedPos) firebaseClient.registerLikeEvent(task);
            else firebaseClient.registerShownEvent(task);
        }
    }

    @Override
    public void onFirebaseAuth() {
        Log.d(TAG,"onFirebaseAuth()");
        firebaseClient.listenOwner(userId);
        vkClient.loadOwnerById(userId);

    }

    @Override
    public void onUpgradeFirebaseProfile(FirebaseProfile profile) {
        Log.d(TAG,"onUpgradeFirebaseProfile(profile)"+profile.getUserId());
        if(state== MVP.State.PROFILE||state== MVP.State.LIKES){
            contentView.loadContent(this,profile);
        }
    }

    @Override
    public void onLoadLikeTasks(FirebaseTasksSet tasks) {
        Log.d(TAG,"onLoadLikeTasks() "+tasks.getTasks().size());
        contentView.loadContent(this,tasks);
        rootView.showIsProgress(false);
    }

    private void addLikeTasks(int count){
        if(vkClient.getChoosedPhotos()==null) return;
        List<Photo> photos=vkClient.getChoosedPhotos();
        Random random=new Random(System.currentTimeMillis());
        List<FirebaseLikeTask> tasks=new ArrayList<>();
        for(int i=0;i<count;i++){
            int pos=random.nextInt(photos.size());
            Photo  choosed= photos.get(pos);
            FirebaseLikeTask task=new FirebaseLikeTask(true,choosed.getId()+"",choosed.getOwnerId()+"",choosed.getSizes().get(choosed.getSizes().size()-1).getSrc());
            tasks.add(task);
        }
        firebaseClient.addPhotoLikeTasks(tasks);
    }


}
