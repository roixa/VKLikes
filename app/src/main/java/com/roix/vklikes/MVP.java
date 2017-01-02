package com.roix.vklikes;

import com.roix.vklikes.pojo.firebase.FirebasePhotoLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;
import com.roix.vklikes.pojo.vk.GetPhotosByAlbumResponse;
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

    }
    public interface ContentView{
        void loadContent(RootPresenter presenter,Object o);
    }


    public enum  State {PROFILE,TASKS,TOP,LIKES,SHOP};

    public interface RootPresenter{
        void init();
        void finish();

        void navTabProfilePushed();
        void navTabLikesPushed();
        void navTabAlbumsPushed();
        void navTabTopPushed();
        void navTabShopPushed();

        void updateContent(ContentView view);
        void choosedAlbum(Album album);
        void imageLikeClicked(Map<String,FirebasePhotoLikeTask> likeTaskMap,int pos);

        void onError(int code,String err);
        //vk api callback
        void onLoadVkUser(User user);
        void onLoadAlbums(AllAlbumsResponse response);
        void onLoadPhotosByAlbum(GetPhotosByAlbumResponse response);
        //firebase api callbacks
        void onFirebaseAuth();
        void onUpgradeFirebaseProfile(FirebaseProfile profile);
        void onLoadLikeTasks(Map<String,FirebasePhotoLikeTask> tasks);
    }

    public interface VKClientModel{
        void loadOwnerById(String vkId);
        void loadOwnerAlbums(String vkId);
        void loadPhotosByAlbum(Album album);
    }

    public interface FirebaseClientModel{
        void singIn();
        void listenUser(User user);
        void addPhotoLikeTask(FirebasePhotoLikeTask task);
        void getAndUsePhotoLikeTasks();
        void lockOrRemoveLikeTasks(boolean isLock,Map<String,FirebasePhotoLikeTask> tasks,int removePos);//lock mean isUsing flag = true, update server, or this remove liked task and unlock other
        void finish();

    }


}
