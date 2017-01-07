package com.roix.vklikes;

import com.roix.vklikes.pojo.firebase.FirebaseLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.firebase.FirebaseTasksSet;
import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.Photo;
import com.roix.vklikes.pojo.vk.User;

import java.util.List;
import java.util.Map;

/**
 * Created by roix on 17.12.2016.
 */

public class MVP {

    public interface RootView{
        void prepareDrawer(User user);
        void prepareProfile();
        void prepareLikes();
        void prepareAlbums();
        void prepareTop();
        void prepareShop();

        void showIsProgress(boolean isProgressing);
        void close();
    }
    public interface ContentView{
        void loadContent(RootPresenter presenter,Object o);
    }


    public enum  State {PROFILE,TASKS,TOP,LIKES,SHOP};

    public interface RootPresenter{
        //base
        void init();
        void finish();
        void backButtonPressed();
        //drawer
        void navTabProfilePushed();
        void navTabLikesPushed();
        void navTabAlbumsPushed();
        void navTabTopPushed();
        void navTabShopPushed();

        //content
        void updateContent(ContentView view);
        void choosedAlbum(Album album);//null == all photos
        void imageLikeClicked(FirebaseTasksSet tasksSet,int likedPos);

        void onError(int code,String err);
        //vk api callback
        void onLoadVkUser(User user);
        void onLoadAlbums(AllAlbumsResponse response);
        void onLoadPhotosByAlbum(List<Photo> response);
        void onLoadAllPhotos(List<Photo> response);
        void onAddLikeResponse(FirebaseTasksSet tasksSet,int likedPos);//this params need to firebase
        //firebase api callbacks
        void onFirebaseAuth();
        void onUpgradeFirebaseProfile(FirebaseProfile profile);
        void onLoadLikeTasks(FirebaseTasksSet tasksSet);
    }

    public interface VKClientModel{
        void loadOwnerById(String vkId);
        void loadOwnerAlbums(String vkId);
        void loadPhotosByAlbum(Album album);
        void loadAllPhotosById(String vkId);
        void addLike(FirebaseTasksSet tasksSet,int likedPos);
    }

    public interface FirebaseClientModel{
        void singIn();
        void listenOwner(String ownerId);
        void addPhotoLikeTasks(List<FirebaseLikeTask> tasks);
        void loadPhotoLikeTasksSet();
        void registerLikeEvent(FirebaseLikeTask task);
        void registerShownEvent(FirebaseLikeTask task);//exp;ain: not like event
        void finish();

    }


}
