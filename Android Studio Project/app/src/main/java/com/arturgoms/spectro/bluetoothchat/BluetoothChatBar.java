package com.arturgoms.spectro.bluetoothchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arturgoms.spectro.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.arturgoms.spectro.common.activities.SampleActivityBase;
import com.arturgoms.spectro.common.logger.Log;
import com.arturgoms.spectro.common.logger.LogWrapper;
import com.arturgoms.spectro.common.logger.MessageOnlyLogFilter;
import com.arturgoms.spectro.R;

import com.arturgoms.spectro.ConfigPage;

public class BluetoothChatBar extends AppCompatActivity {


    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_page);


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }


    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_chat, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:

                Intent intent = new Intent(BluetoothChatBar.this, ConfigPage.class);
                startActivity(intent);
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        //LogFragment logFragment = (LogFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.log_fragment);
        //msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }



}
