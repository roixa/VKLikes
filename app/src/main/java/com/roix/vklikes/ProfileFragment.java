package com.roix.vklikes;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.User;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements MVP.ContentView {
    private TextView userName;
    private TextView userInfo;
    private ImageView userPhoto;
    private TextView userStats;
    private TextView userRating;
    private Object latestContent;
    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        userName=(TextView)v.findViewById(R.id.userName);
        userPhoto=(ImageView)v.findViewById(R.id.userPhoto);
        userInfo=(TextView)v.findViewById(R.id.userInfo);
        userStats=(TextView)v.findViewById(R.id.usersStats);
        userRating=(TextView)v.findViewById(R.id.userRating);
        if(latestContent!=null)loadContent(null,latestContent);
        return v;
    }

    @Override
    public void loadContent(MVP.RootPresenter presenter,Object o) {
        latestContent=o;
        //spinner.dismiss();
        if (o instanceof User) {
            User u=((User) o);
            if(userName!=null)
                userName.setText(u.getFirstName()+" "+u.getLastName());
            if(userInfo!=null)
                userInfo.setText(u.getCommon()+" "+u.getFollowers());

            Picasso.with(getActivity()).load(u.getProtoUrl()).into(userPhoto);
        }
        else if(o instanceof FirebaseProfile){
            FirebaseProfile profile=(FirebaseProfile) o;
            if(userStats!=null)
                userStats.setText("likes in: "+profile.getLikeCountIn()+" likes out: " +profile.getLikeCountOut()+" liked bought: "+profile.getShowCountBuy());
            if(userRating!=null)
                userRating.setText("Rating: "+profile.getRating()*100+" %");
        }


    }
}
