package com.roix.vklikes;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roix.vklikes.pojo.vk.User;
import com.squareup.picasso.Picasso;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MVP.RootView {
    private ActionBarDrawerToggle toggle;
    private MVP.RootPresenter presenter;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar=(ProgressBar)findViewById(R.id.toolbar_progress_bar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                navigationView.getMenu().getItem(1).setChecked(true);
                presenter.navTabLikesPushed();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.finish();
    }

    private void initPresenter() {
        String accessToken = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_ACCESS_TOKEN, "");
        String userId = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_USER_ID, "");
        String email = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_USER_EMAIL, "");
        presenter = new Presenter(this, accessToken, userId, email);
        presenter.init();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            presenter.backButtonPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.root, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            presenter.navTabProfilePushed();
        } else if (id == R.id.nav_like) {
            presenter.navTabLikesPushed();
        } else if (id == R.id.nav_photos) {
            presenter.navTabAlbumsPushed();
            /*
        } else if (id == R.id.nav_top) {
            presenter.navTabTopPushed();
        } else if (id == R.id.nav_shop) {
            presenter.navTabShopPushed();
            */
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void prepareDrawer(User user) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView name = (TextView) drawer.findViewById(R.id.name);
        TextView email = (TextView) drawer.findViewById(R.id.email);
        ImageView imageView = (ImageView) drawer.findViewById(R.id.imageView);
        name.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        Picasso.with(this).load(user.getProtoUrl()).into(imageView);
    }

    @Override
    public void prepareProfile() {
        getSupportActionBar().setTitle(R.string.title_profile);
        Fragment profileFragment = new ProfileFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, profileFragment).commit();
        presenter.updateContent((MVP.ContentView) profileFragment);
        handleFab(true);
    }

    @Override
    public void prepareLikes() {
        getSupportActionBar().setTitle(R.string.title_likes);
        Fragment fragment = new LikesFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        presenter.updateContent((MVP.ContentView) fragment);
        handleFab(false);
    }

    @Override
    public void prepareAlbums() {
        getSupportActionBar().setTitle(R.string.title_albums);
        Fragment fragment = new AlbumsFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        presenter.updateContent((MVP.ContentView) fragment);
        handleFab(true);

    }


    @Override
    public void prepareTop() {

    }

    @Override
    public void prepareShop() {

    }

    @Override
    public void showIsProgress(boolean isProgressing) {
        if(isProgressing) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void close() {
        supportFinishAfterTransition();
    }

    private boolean isFabShowing = true;

    private void handleFab(boolean isShowCommand) {
        if (isShowCommand) {
            showFab();
        } else {
            hideFab();
        }
    }

    private void showFab() {
        if (!isFabShowing) {
            isFabShowing = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                fab.animate().translationY(0).start();
            } else {
                Animation animation = AnimationUtils.makeInAnimation(this, false);
                animation.setFillAfter(true);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fab.startAnimation(animation);
            }
        }

    }

    private void hideFab() {
        if (isFabShowing) {
            isFabShowing = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                final Point point = new Point();
                this.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
                final float translation = fab.getY() - point.y;
                fab.animate().translationYBy(-translation).start();
            } else {
                Animation animation = AnimationUtils.makeOutAnimation(this, true);
                animation.setFillAfter(true);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        fab.setClickable(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fab.startAnimation(animation);
            }
        }
    }

}