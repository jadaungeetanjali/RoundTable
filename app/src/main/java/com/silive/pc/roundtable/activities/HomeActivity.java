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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.silive.pc.roundtable.AlertDialogueBox;
import com.silive.pc.roundtable.AlertDialogueBoxInterface;
import com.silive.pc.roundtable.ProfileImageAssets;
import com.silive.pc.roundtable.R;
import com.silive.pc.roundtable.RoundTableApplication;
import com.silive.pc.roundtable.fragments.ChannelFragment;
import com.silive.pc.roundtable.fragments.ChannelListFragment;
import com.silive.pc.roundtable.fragments.Profile;
import com.silive.pc.roundtable.models.AddChannelModel;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.silive.pc.roundtable.activities.LogInActivity.LOG_IN_PREFS_NAME;

public class HomeActivity extends AppCompatActivity implements AlertDialogueBoxInterface {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgNavHeaderProfile;
    private TextView navHeaderUserName, navHeaderUserEmail;
    private android.support.v7.widget.Toolbar toolbar;
    private Socket socket;
    private Boolean isConnected = true;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    private String token;

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


        try {
            socket = IO.socket("https://chattymac.herokuapp.com/v1/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on(Manager.EVENT_TRANSPORT, onTransport);
        socket.on(Socket.EVENT_CONNECT,onConnect);
        socket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        Socket socket1 = socket.connect();
        Log.i("socket info", socket1.toString());

        SharedPreferences prefs = getSharedPreferences(LOG_IN_PREFS_NAME, MODE_PRIVATE);
        token = prefs.getString("token", "No token generated");//"No token generated" is the default value.

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CHANNEL;
            loadHomeFragment();
        }
    }

    private Emitter.Listener onTransport = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Transport transport = (Transport)args[0];

            transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    @SuppressWarnings("unchecked")
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer "+token);
                }
            });
        }
    };
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=channelName)
                            socket.emit("newChannel", channelName, channelDescription);
                        Toast.makeText(getApplicationContext(),
                                "Connected", Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            "Disconnected. Please check your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(),
                            "Failed to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onChannelCreated = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Log.e(TAG, "Error connecting");
                    Toast.makeText(getApplicationContext(),
                            "Channel", Toast.LENGTH_LONG).show();
                }
            });
        }
    };



    /***
     * Load navigation menu header information
     * like background image, profile image
     * userName, email
     */
    private void loadNavHeader() {
        SharedPreferences prefs = getSharedPreferences(LOG_IN_PREFS_NAME, MODE_PRIVATE);
        userName = prefs.getString("userName", "No token generated");
        userEmail = prefs.getString("email", "No token generated");
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

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendChannel(ArrayList<String> channelList) {
        channelName = channelList.get(0);
        channelDescription = channelList.get(1);
        if (channelName != null && channelDescription != null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CHANNEL;
            Bundle bundle = new Bundle();
            bundle.putString("channelName", channelName);
            bundle.putString("channelDescription", channelDescription);

            AddChannelModel addChannelModel = new AddChannelModel(channelName, channelDescription);
            Log.i("sender", addChannelModel.getDescription());
            Log.i("sender", addChannelModel.getName());
            Gson gson = new Gson();
            String json = gson.toJson(addChannelModel);
            if(socket.connected() == true) {
                Log.i("json", json);
                socket.emit("newChannel", json, new Ack() {
                    @Override
                    public void call(Object... args) {
                        Log.i("response socket", args.toString());
                        Toast.makeText(getApplicationContext(), "new channel", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            /*
            // channel
            ChannelListFragment channelListFragment = new ChannelListFragment();
            channelListFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, channelListFragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();
            */
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        socket.disconnect();

        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    }
}
