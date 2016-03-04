package com.arturgoms.spectro;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arturgoms.spectro.bluetoothchat.BluetoothChatBar;
import com.arturgoms.spectro.project.ActivityProjectEdit;
import com.arturgoms.spectro.project.MainProject;

import java.util.UUID;

import Fragments.DadosPage;
import Fragments.InfoPage;
import Fragments.ProjectPage;
import Fragments.WelcomePage;

import static com.arturgoms.spectro.project.DataUtilsProject.NEW_PROJECT_REQUEST;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_REQUEST_CODE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;
    private Button btnEntendi;
    private TextView txtMyname;
    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    private int mMaxChars = 50000;//Default
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;

    private boolean mIsUserInitiatedDisconnect = false;

    // All controls here
    private TextView mTxtReceive;
    private EditText mEditSend;
    private Button mBtnDisconnect;
    private Button mBtnSend;
    private Button mBtnClear;
    private Button mBtnClearInput;
    private ScrollView scrollView;
    private CheckBox chkScroll;
    private CheckBox chkReceiveText;

    private boolean mIsBluetoothConnected = false;

    private BluetoothDevice mDevice;

    private ProgressDialog progressDialog;
    //Bluetooth code



    private static final String SAVED_PENDING_REQUEST_ENABLE_BT = "PENDING_REQUEST_ENABLE_BT";
    // do not resend request to enable Bluetooth
    // if there is a request already in progress
    // See: https://code.google.com/p/android/issues/detail?id=24931#c1
    boolean pendingRequestEnableBt = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnEntendi = (Button) findViewById(R.id.btnEntendi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityProjectEdit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(PROJECT_REQUEST_CODE, NEW_PROJECT_REQUEST);

                startActivityForResult(intent, NEW_PROJECT_REQUEST);


            }
        });

        if (Build.VERSION.SDK_INT >= 18)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

            // Android version < 18 -> set orientation sensorPortrait
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        // BLUETOOTH SERIA CODE



        // BLUETOOTH CODE END


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_page, new WelcomePage()).commit();

        getSupportActionBar().setHomeButtonEnabled(false);

        if (savedInstanceState != null) {
            pendingRequestEnableBt = savedInstanceState.getBoolean(SAVED_PENDING_REQUEST_ENABLE_BT);
        }




    }

// ======================================== BLUETOOTH TERMINAL CODE =========================


//=============================================== END OF BLUETOOTH CODE ======================

    //=============================================== MENU ======================
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
//=============================================== MENU ======================


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_welcome) {
            fm.beginTransaction().replace(R.id.content_page, new WelcomePage()).commit();

        } else if (id == R.id.nav_projetos) {
            Intent intent = new Intent(MainActivity.this, MainProject.class);
            startActivity(intent);

        } else if (id == R.id.nav_dados) {
            fm.beginTransaction().replace(R.id.content_page, new DadosPage()).commit();

        } else if (id == R.id.nav_config) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_terminal) {

            Intent intent = new Intent(MainActivity.this, BluetoothChatBar.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_note) {

            Intent intent = new Intent(MainActivity.this, NoteMain.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_tutorial) {
            fm.beginTransaction().replace(R.id.content_page, new TutorialPage()).commit();
        }

        else if (id == R.id.nav_face){

            Intent intent = new Intent(MainActivity.this, Webview3.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_tt){

            Intent intent = new Intent(MainActivity.this, Webview2.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_git){
            Intent intent = new Intent(MainActivity.this, Webview1.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_info){
            fm.beginTransaction().replace(R.id.content_page, new InfoPage()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // BLUETOOTH SERIAL CODE



}
