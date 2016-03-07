package com.arturgoms.spectro;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.arturgoms.spectro.login.helper.SQLiteHandler;
import com.arturgoms.spectro.login.helper.SessionManager;

/**
 * Created by arturgoms on 07/03/16.
 */
public class NavHeader extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private TextView txtMyname;
    private TextView txtUserEmail;


    private SessionManager session;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
