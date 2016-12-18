package com.roix.vklikes;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.roix.vklikes.pojo.User;
import com.roix.vklikes.pojo.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.VK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String accessToken= PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_ACCESS_TOKEN,"");
        String userId= PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_USER_ID,"");
        Log.d(TAG,"getDefaultSharedPreferences"+accessToken+" "+userId);

        VKApi api=retrofit.create(VKApi.class);
        api.geUserInfo(accessToken,userId,Constants.USER_INFO_FIELDS,"5.8").enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                User user=response.body().getUser().get(0);
                Log.d(TAG,"onResponse "+user.getFirstName()+" "+user.getId()+" common "+user.getCommon());

            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
