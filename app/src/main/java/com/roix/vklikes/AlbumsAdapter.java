package com.roix.vklikes;

import android.content.Context;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.roix.vklikes.pojo.vk.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by roix on 25.12.2016.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>{
    private String TAG="AlbumsAdapter";
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

        int choosedAlbumPos= PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.PREF_CHOOSED_ALBUM_ID,-1);
        Log.d(TAG,"onBindViewHolder choosedAlbumPos="+choosedAlbumPos);
        onBind = true;


        if(choosedAlbumPos==-1){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(pos==choosedAlbumPos);
        }
        onBind = false;

    }

    @Override
    public int getItemCount() {
        if(alba!=null)return alba.size();
        return 0;
    }

    boolean onBind=false;
    public void onCheckBoxClicked(int pos,boolean isChecked){
        Log.d(TAG,"onCheckBoxClicked pos="+pos);
        if(onBind) return;
        if(isChecked&&pos==-1) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(Constants.PREF_CHOOSED_ALBUM_ID,-1).commit();
            presenter.choosedAlbum(null);
        }
        else if(!isChecked&&pos==-1){
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(Constants.PREF_CHOOSED_ALBUM_ID,0).commit();
            presenter.choosedAlbum(alba.get(0));
        }
        else{
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(Constants.PREF_CHOOSED_ALBUM_ID,pos).commit();
            presenter.choosedAlbum(alba.get(pos));
        }
        notifyDataSetChanged();
    }







    public class AlbumsViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        public CircleImageView imageView;
        public TextView name;
        public TextView info;
        public CheckBox checkBox;
        public AlbumsViewHolder(View v) {
            super(v);
            imageView=(CircleImageView) v.findViewById(R.id.photo);
            name=(TextView) v.findViewById(R.id.name);
            info=(TextView) v.findViewById(R.id.info);
            checkBox=(CheckBox)v.findViewById(R.id.useCheckBox);
            checkBox.setOnCheckedChangeListener(this);
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            onCheckBoxClicked(getAdapterPosition(),isChecked);

        }
    }
}
