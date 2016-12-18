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
import com.roix.vklikes.pojo.vk.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roix on 10.12.16.
 */

public class Presenter {
    private MVP.RootView rootView;
    private MVP.ContentView contentView;
    private enum  State {PROFILE,TASKS,TOP,LIKES};
    private State state;
    private VKApi vkApi;
    private String TAG="Presenter";
    private String accessToken,userId,email;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    public Presenter(MVP.RootView rootView,String accessToken,String userId,String email){
        this.rootView=rootView;
        this.accessToken=accessToken;
        this.userId=userId;
        this.email=email;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        vkApi=retrofit.create(VKApi.class);

    }
    public void init(){
        state=State.PROFILE;
        rootView.prepareProfile();
        singInFirebase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        vkApi.geUserInfo(accessToken,userId,Constants.USER_INFO_FIELDS,"5.8").enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                final User user=response.body().getUser().get(0);
                if(contentView!=null)
                    contentView.loadContent(user);
                Log.d(TAG,"onResponse "+user.getFirstName()+" "+user.getId()+" common "+user.getCommon()+" url "+user.getProtoUrl());
                user.setEmail(email);
                database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChild(""+email.hashCode())){
                            database.getReference().child("users").child(""+email.hashCode()).setValue(new FirebaseProfile(email,user.getId(),0,0,null)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //contentView.loadContent();
                                }
                            });
                        }
                        else ;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //database.getReference().child("users").child(""+email.hashCode()).child("vk").setValue(user);
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });



    }


    public void updateContent(MVP.ContentView contentView){
        this.contentView=contentView;
    }

    private void singInFirebase(){
        mFirebaseAuth = FirebaseAuth.getInstance();

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
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously:failed", task.getException());
                        }
                    }
                });

    }

}
