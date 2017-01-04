package com.roix.vklikes;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roix.vklikes.pojo.firebase.FirebaseLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.firebase.FirebaseTasksSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LikesFragment extends Fragment implements MVP.ContentView, View.OnClickListener{

    private ImageView first;
    private ImageView second;
    private ImageView third;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private TextView userStats;
    private TextView userRating;

    private MVP.RootPresenter presenter;
    private List<FirebaseLikeTask> taskList;

    public LikesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_likes, container, false);
        first=(ImageView) v.findViewById(R.id.imageFirst);
        second=(ImageView) v.findViewById(R.id.imageSecond);
        third=(ImageView) v.findViewById(R.id.imageThird);
        fab1=(FloatingActionButton) v.findViewById(R.id.fab1);
        fab2=(FloatingActionButton) v.findViewById(R.id.fab2);
        fab3=(FloatingActionButton) v.findViewById(R.id.fab3);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        userStats=(TextView)v.findViewById(R.id.usersStats);
        userRating=(TextView)v.findViewById(R.id.userRating);

        return v;
    }

    //@TODO make safety
    @Override
    public void loadContent(MVP.RootPresenter presenter, Object o) {
        this.presenter=presenter;
        if(o instanceof FirebaseTasksSet){
            taskList= ((FirebaseTasksSet) o).getTasks();
            int sz=taskList.size();
            if(sz>0) Picasso.with(getActivity()).load(taskList.get(0).getPhotoUrl()).into(first);
            else first.setImageBitmap(null);
            if(sz>1) Picasso.with(getActivity()).load(taskList.get(1).getPhotoUrl()).into(second);
            else second.setImageBitmap(null);
            if(sz>2) Picasso.with(getActivity()).load(taskList.get(2).getPhotoUrl()).into(third);
            else third.setImageBitmap(null);

        }
        else if(o instanceof FirebaseProfile){
            FirebaseProfile profile=(FirebaseProfile) o;
            userStats.setText("likes in: "+profile.getLikeCountIn()+" likes out: " +profile.getLikeCountOut()+" liked bought: "+profile.getLikeCountBuy());
            userRating.setText("Rating: "+profile.getRating()*100+" %");
        }
    }

    @Override
    public void onClick(View v) {


        FullImageDialogFragment dialogFragment = null;

        switch (v.getId()){
            case R.id.imageFirst:
                if(taskList.size()>0){
                    dialogFragment=FullImageDialogFragment.newInstance(taskList.get(0).getPhotoUrl(),"");
                    dialogFragment.show(getFragmentManager(),"diag1");
                }
                break;
            case R.id.imageSecond:
                if(taskList.size()>1) {
                    dialogFragment=FullImageDialogFragment.newInstance(taskList.get(1).getPhotoUrl(),"");
                    dialogFragment.show(getFragmentManager(),"diag1");

                }
                break;
            case R.id.imageThird:
                if(taskList.size()>2) {
                    dialogFragment=FullImageDialogFragment.newInstance(taskList.get(2).getPhotoUrl(),"");
                    dialogFragment.show(getFragmentManager(),"diag1");
                }
                break;
            case R.id.fab1:
                presenter.imageLikeClicked(taskList,0);
                break;
            case R.id.fab2:
                presenter.imageLikeClicked(taskList,1);
                break;
            case R.id.fab3:
                presenter.imageLikeClicked(taskList,2);
                break;

        }

    }
}
