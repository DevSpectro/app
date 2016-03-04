package Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.arturgoms.spectro.R;
import com.arturgoms.spectro.Webview1;
import com.arturgoms.spectro.project.ActivityProjectEdit;
import com.arturgoms.spectro.project.MainProject;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;

import static com.arturgoms.spectro.project.DataUtilsProject.NEW_PROJECT_REQUEST;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_REQUEST_CODE;


/**
 * Created by arturgoms on 22/02/16.
 */
public class WelcomePage extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    public WelcomePage() {
    }

    private Button btnEntendi;
    int id;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.welcome_page, container, false);
        btnEntendi = (Button) rootView.findViewById(R.id.btnEntendi);
        btnEntendi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainProject.class);
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

