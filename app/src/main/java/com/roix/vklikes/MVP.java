package com.roix.vklikes;

import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.User;

/**
 * Created by roix on 17.12.2016.
 */

public class MVP {

    public interface RootView{
        void prepareProfile();
    }
    public interface ContentView{
        void loadContent(Object o);
    }


    public enum  State {PROFILE,TASKS,TOP,LIKES};
    //@TODO impliment and split presenter vk and file models
    public interface RootPresenter{
        void init();
        void finish();


        void onLoadVkUser(User user);
        void onFirebaseAuth();
        void onUpgradeFirebaseProfile(FirebaseProfile profile);
    }

    public interface VKClientModel{
        void loadUserById(String vkId);
    }

    public interface FirebaseClientModel{
        void singIn();
        void listenUser(User user);
    }


}
