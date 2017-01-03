package com.roix.vklikes;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

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
        fab1=(FloatingActionButton) v.findViewById(R.id.fab1);
        fab2=(FloatingActionButton) v.findViewById(R.id.fab2);
        fab3=(FloatingActionButton) v.findViewById(R.id.fab3);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
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
        else first.setImageBitmap(null);
        if(sz>1) Picasso.with(getActivity()).load(list.get(1).getPhotoUrl()).into(second);
        else second.setImageBitmap(null);
        if(sz>2) Picasso.with(getActivity()).load(list.get(2).getPhotoUrl()).into(third);
        else third.setImageBitmap(null);

    }

    @Override
    public void onClick(View v) {

        List<FirebasePhotoLikeTask> list = new ArrayList<>(taskMap.values());

        FullImageDialogFragment dialogFragment = null;

        switch (v.getId()){
            case R.id.imageFirst:
                dialogFragment=FullImageDialogFragment.newInstance(list.get(0).getPhotoUrl(),"");
                dialogFragment.show(getFragmentManager(),"diag1");
                break;
            case R.id.imageSecond:
                dialogFragment=FullImageDialogFragment.newInstance(list.get(1).getPhotoUrl(),"");
                dialogFragment.show(getFragmentManager(),"diag1");
                break;
            case R.id.imageThird:
                dialogFragment=FullImageDialogFragment.newInstance(list.get(2).getPhotoUrl(),"");
                dialogFragment.show(getFragmentManager(),"diag1");
                break;
            case R.id.fab1:
                presenter.imageLikeClicked(taskMap,0);
                break;
            case R.id.fab2:
                presenter.imageLikeClicked(taskMap,1);
                break;
            case R.id.fab3:
                presenter.imageLikeClicked(taskMap,2);
                break;

        }

    }
}
