package com.roix.vklikes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roix.vklikes.pojo.firebase.FirebaseProfile;
import com.roix.vklikes.pojo.vk.User;

/**
 * Created by roix on 18.12.2016.
 */

public class FirebaseClient implements MVP.FirebaseClientModel {
    private MVP.RootPresenter presenter;
    private String TAG="FirebaseClient";
    private FirebaseAuth mFirebaseAuth;

    public FirebaseClient(MVP.RootPresenter presenter){
        this.presenter=presenter;
    }

    @Override
    public void singIn() {
        mFirebaseAuth= FirebaseAuth.getInstance();

        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG,"onAuthStateChanged   onComplete " +firebaseAuth.toString());

            }
        });

        mFirebaseAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful()+mFirebaseAuth.getCurrentUser().getDisplayName());
                        if (task.isSuccessful()) {
                            presenter.onFirebaseAuth();
                        }
                    }
                });
    }

    @Override
    public void listenUser(final User user) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
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
        });
    }
}
