package com.arturgoms.spectro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arturgoms.spectro.R;
import com.arturgoms.spectro.TerminalPage;
import com.arturgoms.spectro.bluetoothchat.BluetoothChat;
import com.arturgoms.spectro.bluetoothchat.BluetoothChatBar;
import com.arturgoms.spectro.bluetoothchat.DeviceListActivity;

/**
 * Created by arturgoms on 28/02/16.
 */
public class Terminal extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    public Terminal() {
    }

    private Button btnTerminal;
    private Button btn1;
    private Button btn2;
    int id;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.terminal, container, false);
        btnTerminal = (Button) rootView.findViewById(R.id.btnTerminal);
        btnTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BluetoothChatBar.class);
                startActivity(intent);


            }
        });
        return rootView;

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        return false;
    }
}
