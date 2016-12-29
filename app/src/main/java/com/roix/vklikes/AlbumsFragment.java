package com.roix.vklikes;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roix.vklikes.pojo.vk.Album;
import com.roix.vklikes.pojo.vk.AllAlbumsResponse;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment implements MVP.ContentView{

    private RecyclerView recyclerView;

    public AlbumsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void loadContent(MVP.RootPresenter presenter,Object o) {
        if(o instanceof AllAlbumsResponse){
            AllAlbumsResponse response=(AllAlbumsResponse) o;
            AlbumsAdapter adapter=new AlbumsAdapter(response.getAllAlbumsInnerResponse().getAlba(),getActivity(),presenter);
            recyclerView.setAdapter(adapter);
        }
    }
}
