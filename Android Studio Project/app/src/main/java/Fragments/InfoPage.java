package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arturgoms.spectro.R;


/**
 * Created by arturgoms on 24/02/16.
 */
public class InfoPage extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_page, container, false);
        return rootView;

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }
}
