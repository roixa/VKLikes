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

import com.roix.vklikes.pojo.vk.User;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements MVP.ContentView {
    private ProgressDialog spinner;
    private TextView userName;
    private TextView userInfo;
    private ImageView userPhoto;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner = new ProgressDialog(getActivity());
        spinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spinner.setMessage("Loading...");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        userName=(TextView)v.findViewById(R.id.userName);
        userPhoto=(ImageView)v.findViewById(R.id.userPhoto);
        userInfo=(TextView)v.findViewById(R.id.userInfo);

        return v;
    }

    @Override
    public void loadContent(Object o) {
        //spinner.dismiss();
        if (o instanceof User) {
            User u=((User) o);
            userName.setText(u.getFirstName()+" "+u.getLastName());
            userInfo.setText(u.getCommon()+" "+u.getFollowers());

            Picasso.with(getActivity()).load(u.getProtoUrl()).into(userPhoto);
        }


    }
}
