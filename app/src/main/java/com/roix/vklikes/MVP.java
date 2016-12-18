package com.roix.vklikes;

import com.roix.vklikes.pojo.FirebaseProfile;
import com.roix.vklikes.pojo.User;

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

    //@TODO impliment and split presenter vk and file models
    public interface RootPresenter{
        void init();
        void onLoadVkSelf(User user);
        void onLoadFireProfile(FirebaseProfile profile);
    }
}
