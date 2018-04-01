package com.silive.pc.roundtable.activities;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silive.pc.roundtable.AlertDialogueBox;
import com.silive.pc.roundtable.AlertDialogueBoxInterface;
import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.fragments.ChannelFragment;
import com.silive.pc.roundtable.fragments.Profile;

import java.util.ArrayList;

import static com.silive.pc.roundtable.activities.LogInActivity.LOG_IN_PREFS_NAME;

public class HomeActivity extends AppCompatActivity implements AlertDialogueBoxInterface {

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
    private static final String TAG_PROFILE = "profile";
    public static String CURRENT_TAG = TAG_CHANNEL;

    private String userName, userEmail, userAvatarColor;
    private int userAvatarName;
    private String channelName, channelDescription;

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

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CHANNEL;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * userName, userEmail
     */
    private void loadNavHeader() {
        SharedPreferences prefs = getSharedPreferences(LOG_IN_PREFS_NAME, MODE_PRIVATE);
        userName = prefs.getString("userName", "No token generated");
        userEmail = prefs.getString("userEmail", "No token generated");
        userAvatarName = prefs.getInt("userAvatar", 0);

        // name, email, profileImage
        navHeaderUserName.setText(userName);
        navHeaderUserEmail.setText(userEmail);
        imgNavHeaderProfile.setImageResource(ProfileImageAssets.getProfileImages().get(userAvatarName));

    }
    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
    public Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // channel
                ChannelFragment channelFragment = new ChannelFragment();
                return channelFragment;
            case 1:
                // profile
                Profile profileFragment = new Profile();
                return profileFragment;
            default:
                return new ChannelFragment();
        }
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_channels:
                        toolbar.setTitle("ChannelName");
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_CHANNEL;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        toolbar.setTitle("Profile");
                        CURRENT_TAG = TAG_PROFILE;
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
                loadHomeFragment();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
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
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadChannelFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_CHANNEL;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.add_channel, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_channel) {
            Toast.makeText(getApplicationContext(), "ADD CHANNEL!", Toast.LENGTH_LONG).show();
            AlertDialogueBox dialogueBox = new AlertDialogueBox(this);
            dialogueBox.getAlertDialogueBox();
            //Toast.makeText(this, channels.get(0).toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendChannel(ArrayList<String> channelList) {
        Toast.makeText(this, channelList.toString(), Toast.LENGTH_SHORT).show();
    }
}
