package com.roix.vklikes;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roix.vklikes.pojo.firebase.FirebasePhotoLikeTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LikesFragment extends Fragment implements MVP.ContentView, View.OnClickListener{

    private ImageView first;
    private ImageView second;
    private ImageView third;
    private MVP.RootPresenter presenter;
    private Map<String,FirebasePhotoLikeTask> taskMap;

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
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        return v;
    }

    //@TODO make safety
    @Override
    public void loadContent(MVP.RootPresenter presenter, Object o) {
        this.presenter=presenter;
        taskMap=(Map<String,FirebasePhotoLikeTask> ) o;
        List<FirebasePhotoLikeTask> list = new ArrayList<>(taskMap.values());
        int sz=list.size();
        if(sz>0) Picasso.with(getActivity()).load(list.get(0).getPhotoUrl()).into(first);
        if(sz>1) Picasso.with(getActivity()).load(list.get(1).getPhotoUrl()).into(second);
        if(sz>2) Picasso.with(getActivity()).load(list.get(2).getPhotoUrl()).into(third);
    }

    @Override
    public void onClick(View v) {
        int pos=-1;
        if(v.getId()==R.id.imageFirst) pos=0;
        else if(v.getId()==R.id.imageSecond) pos=1;
        else if(v.getId()==R.id.imageThird) pos=2;
        presenter.imageLikeClicked(taskMap,pos);

    }
}
