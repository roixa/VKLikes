package com.roix.vklikes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roix.vklikes.pojo.firebase.FirebaseLikeTask;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.firebase.FirebaseTasksSet;
import com.roix.vklikes.pojo.vk.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roix on 18.12.2016.
 */

public class FirebaseClient implements MVP.FirebaseClientModel {
    private MVP.RootPresenter presenter;
    private String TAG="FirebaseClient";
    private DatabaseReference dbRef;
    private FirebaseAuth mFirebaseAuth;
    private ValueEventListener ownerListener;
    private OnCompleteListener authListener;
    private FirebaseProfile owner;
    private List<FirebaseLikeTask> lastTasks;


    public FirebaseClient(MVP.RootPresenter presenter){
        this.presenter=presenter;
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void singIn() {
        mFirebaseAuth= FirebaseAuth.getInstance();
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
    public void listenOwner(final String ownerId) {
        ownerListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(ownerId)){
                    Log.d( TAG,"listenOwner create user");
                    dataSnapshot.getRef().child(ownerId).updateChildren(new FirebaseProfile(ownerId,0,0,0,0.d).toMap());
                }
                else {
                    Log.d( TAG,"listenOwner get user");
                    owner=  dataSnapshot.child(ownerId).getValue(FirebaseProfile.class);
                    presenter.onUpgradeFirebaseProfile(owner);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        if(owner==null){
            dbRef.child("users").addValueEventListener(ownerListener);
        }
        else {
            presenter.onUpgradeFirebaseProfile(owner);
        }
    }

    @Override
    public void addPhotoLikeTasks(List<FirebaseLikeTask> tasks) {
        owner.addTasks(tasks);
        owner.setLikeCountOut(owner.getLikeCountOut()+tasks.size());
        owner.refreshData();
        dbRef.child("users").child(owner.getUserId()).updateChildren(owner.toMap());
    }

    @Override
    public void loadPhotoLikeTasksSet() {
        Query activeUsersQuery = dbRef.child("users").orderByChild("isActive").equalTo(true).limitToLast(4);
        ValueEventListener likesTaskListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FirebaseLikeTask> choosedTasks=new ArrayList<>();
                Log.d( TAG,"Count "+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FirebaseProfile profile = postSnapshot.getValue(FirebaseProfile.class);
                    Log.d( TAG,"profile tasks size "+profile.getTasks().size());
                    FirebaseLikeTask task=profile.removeTask();
                    profile.setLikeCountIn(profile.getLikeCountIn()+1);
                    profile.refreshData();
                    postSnapshot.getRef().updateChildren(profile.toMap());
                    choosedTasks.add(task);
                }
                presenter.onLoadLikeTasks(new FirebaseTasksSet(choosedTasks));
                dataSnapshot.getRef().removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        activeUsersQuery.addValueEventListener(likesTaskListener);
    }

    private synchronized FirebaseProfile getOwner() {
        return owner;
    }

    private synchronized void setOwner(FirebaseProfile owner) {
        this.owner = owner;
    }


    @Override
    public void finish() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("users").removeEventListener(ownerListener);

    }
    private Map<String,FirebaseLikeTask> filterData(Map<String,FirebaseLikeTask> pre){
        Map<String,FirebaseLikeTask> post=new HashMap<>();
        for (Map.Entry<String, FirebaseLikeTask> entry : pre.entrySet()){
            //if(!entry.getValue().isUsing()&&post.size()<3){
            if(post.size()<3){
                post.put(entry.getKey(),entry.getValue());
            }
        }
        Log.d( TAG,"filterData "+post.values().size());

        return post;
    }
}
