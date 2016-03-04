package com.arturgoms.spectro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by arturgoms on 28/02/16.
 */
public class TutorialPage extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    public TutorialPage() {
    }

    private Button btnTutorial;
    int id;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tutorial_project, container, false);
        btnTutorial = (Button) rootView.findViewById(R.id.btnTutorial);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), YourProjectsPage.class);
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