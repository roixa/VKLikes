package com.roix.vklikes;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class FullImageDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String argImageUrl = "imageUrl";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String imageUrl;
    private String mParam2;


    public FullImageDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */

    public static FullImageDialogFragment newInstance(String param1, String param2) {
        FullImageDialogFragment fragment = new FullImageDialogFragment();
        Bundle args = new Bundle();
        args.putString(argImageUrl, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(argImageUrl);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_full_image_dialog, container, false);
        ImageView imageView=(ImageView)v.findViewById(R.id.dialog_image_view);
        Picasso.with(getActivity()).load(imageUrl).into(imageView);
        return v;
    }

}
