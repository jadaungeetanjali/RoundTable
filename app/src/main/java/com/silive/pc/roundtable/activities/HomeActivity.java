package com.silive.pc.roundtable.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.silive.pc.roundtable.R;

public class HomeActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgNavHeaderProfile;
    private TextView navHeaderUserName, navHeaderUserEmail;
    private android.support.v7.widget.Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_CHANNEL = "channel";
    private static final String TAG_PERSONAL = "personal";
    public static String CURRENT_TAG = TAG_CHANNEL;

    // flag to load channel fragment when user presses back key
    private boolean shouldLoadChannelFragOnBackPress = true;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.nav_drawer_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        navHeaderUserName = (TextView) navHeader.findViewById(R.id.nav_user_name);
        navHeaderUserEmail = (TextView) navHeader.findViewById(R.id.nav_user_email);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.nav_img_header_bg);
        imgNavHeaderProfile = (ImageView) navHeader.findViewById(R.id.nav_img_profile);

        setUpNavigationView();

        // load nav menu header data
        loadNavHeader();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CHANNEL;
            //loadChannelFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * userName, userEmail
     */
    private void loadNavHeader() {
        // name, website
        navHeaderUserName.setText("Geetanjali Jadaun");
        navHeaderUserEmail.setText("silive.in");

        // TODO load profile imageView

    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_channels:
                        navItemIndex = 0;
                        break;
                    case R.id.nav_user1:
                        navItemIndex = 1;
                        break;
                    case R.id.nav_user2:
                        navItemIndex = 2;
                        break;
                    default:
                        navItemIndex = 0;
                }
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


}
