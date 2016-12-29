package com.roix.vklikes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roix.vklikes.pojo.vk.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by roix on 25.12.2016.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>{

    private List<Album> alba;
    private Context context;
    private MVP.RootPresenter presenter;

    public AlbumsAdapter(List<Album> alba, Context context,MVP.RootPresenter presenter) {
        this.alba = alba;
        this.context=context;
        this.presenter=presenter;
    }

    @Override
    public AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        AlbumsViewHolder holder=new AlbumsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumsViewHolder holder, int pos) {
        Album album=alba.get(pos);
        holder.name.setText(album.getTitle());
        holder.info.setText(album.getSize()+"");
        Picasso.with(context).load(album.getThumbSrc()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(alba!=null)return alba.size();
        return 0;
    }

    public class AlbumsViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView name;
        public TextView info;
        public AlbumsViewHolder(View v) {
            super(v);
            imageView=(ImageView) v.findViewById(R.id.photo);
            name=(TextView) v.findViewById(R.id.name);
            info=(TextView) v.findViewById(R.id.info);
        }
    }
}
