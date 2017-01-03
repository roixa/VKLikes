package com.roix.vklikes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roix.vklikes.pojo.firebase.FirebasePhotoLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by roix on 18.12.2016.
 */

public class FirebaseClient implements MVP.FirebaseClientModel {
    private MVP.RootPresenter presenter;
    private String TAG="FirebaseClient";
    private FirebaseAuth mFirebaseAuth;
    ValueEventListener ownerListener;

    OnCompleteListener authListener;


    public FirebaseClient(MVP.RootPresenter presenter){
        this.presenter=presenter;
        FirebaseDatabase database = FirebaseDatabase.getInstance();

    }

    @Override
    public void singIn() {
        mFirebaseAuth= FirebaseAuth.getInstance();

        /*
        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG,"onAuthStateChanged   onComplete " +firebaseAuth.toString());

            }
        });
        */

        authListener=new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful()+mFirebaseAuth.getCurrentUser().getDisplayName());
                if (task.isSuccessful()) {
                    presenter.onFirebaseAuth();
                }

            }
        };
        mFirebaseAuth.signInAnonymously()
                .addOnCompleteListener(authListener);
    }

    @Override
    public void listenUser(final User user) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        ownerListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(user.getId())){
                    database.getReference().child("users").child(user.getId()).updateChildren(new FirebaseProfile(user.getEmail(),user.getId(),0,0,null).toMap());
                }
                else {
                    FirebaseProfile profile=  dataSnapshot.child(user.getId()).getValue(FirebaseProfile.class);
                    presenter.onUpgradeFirebaseProfile(profile);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database.getReference().child("users").addValueEventListener(ownerListener);
    }

    @Override
    public void addPhotoLikeTask(FirebasePhotoLikeTask task) {
        FirebaseDatabase.getInstance().getReference().child("likesTask").push().setValue(task);
    }

    @Override
    public void getAndUsePhotoLikeTasks() {
        Log.d(TAG,"getAndUsePhotoLikeTasks start ");
        Query recentPostsQuery = FirebaseDatabase.getInstance().getReference().child("likesTask");//.limitToLast(3).orderByChild("isUsing").equalTo(false);


        //recentPostsQuery.keepSynced(true);

        ValueEventListener likesTaskListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,FirebasePhotoLikeTask> ret=new HashMap<>();

                Log.d( TAG,"Count "+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    FirebasePhotoLikeTask post = postSnapshot.getValue(FirebasePhotoLikeTask.class);
                    Log.d( TAG,"post "+post.getPhotoID());
                    postSnapshot.getRef().setValue(post);
                    ret.put(postSnapshot.getKey(),post);
                }
                ret=filterData(ret);
                Log.d( TAG,"last "+ret.size());
                lockOrRemoveLikeTasks(true,ret,0);
                dataSnapshot.getRef().removeEventListener(this);
                presenter.onLoadLikeTasks(ret);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        recentPostsQuery.addValueEventListener(likesTaskListener);

    }

    @Override
    public void lockOrRemoveLikeTasks(boolean isLock, Map<String, FirebasePhotoLikeTask> tasks, int removePos) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("likesTask");

        for (Map.Entry<String, FirebasePhotoLikeTask> entry : tasks.entrySet()) {
            FirebasePhotoLikeTask task=entry.getValue();
            if(isLock){
                task.setUsing(true);
                reference.child(entry.getKey()).setValue(task);
            }
            else {
                reference.child(entry.getKey()).removeValue();
            }
        }
    }

    @Override
    public void finish() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("users").removeEventListener(ownerListener);

    }
    private Map<String,FirebasePhotoLikeTask> filterData(Map<String,FirebasePhotoLikeTask> pre){
        Map<String,FirebasePhotoLikeTask> post=new HashMap<>();
        for (Map.Entry<String, FirebasePhotoLikeTask> entry : pre.entrySet()){
            //if(!entry.getValue().isUsing()&&post.size()<3){
            if(post.size()<3){
                post.put(entry.getKey(),entry.getValue());
            }
        }
        Log.d( TAG,"filterData "+post.values().size());

        return post;
    }
}
