package com.roix.vklikes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.test.espresso.core.deps.guava.base.Splitter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

public class OAuthActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog spinner;
    private static final String TAG="OauthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        webView=(WebView)findViewById(R.id.webView);
        spinner = new ProgressDialog(this);
        spinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        spinner.setMessage("Loading...");
        String url=Constants.VK_OAUTH_URL+"?"+"client_id="+Keys.VK_CLIENT_ID+"&"+"redirect_uri="+Constants.VK_OAUTH_REDIRECT_URL+"&scope=wall,email,offline,photos&response_type=token";

        Log.d(TAG, "m url: " + url);

        webView.loadUrl(url);
        webView.setWebViewClient(new OAuthWebViewClient(this));
    }

    private class OAuthWebViewClient extends WebViewClient {
        private Context context;
        public OAuthWebViewClient (Context context){
            this.context=context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {

            return false;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading URL: " + url);

            super.onPageStarted(view, url, favicon);
            spinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            spinner.dismiss();
            if (url.startsWith(Constants.VK_OAUTH_REDIRECT_URL)) {
                Log.d(TAG, "onPageFinished all: "+url);

                String query = url.split("\\#")[1];
                Map<String, String> map = Splitter.on('&').trimResults().withKeyValueSeparator("=").split(query);

                String code = map.get(Constants.PREF_ACCESS_TOKEN);
                String userid=map.get(Constants.PREF_USER_ID);
                String email=map.get(Constants.PREF_USER_EMAIL);
                SharedPreferences preference=PreferenceManager.getDefaultSharedPreferences(context);
                preference.edit().putString(Constants.PREF_ACCESS_TOKEN,code).commit();
                preference.edit().putString(Constants.PREF_USER_ID,userid).commit();
                preference.edit().putString(Constants.PREF_USER_EMAIL,email  ).commit();
                Log.d(TAG, "onPageFinished code: " + code+" user id: "+userid+" email: "+email);
                Intent intent = new Intent(OAuthActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }



}
