package Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arturgoms.spectro.PopNew;
import com.arturgoms.spectro.PopOpen;
import com.arturgoms.spectro.R;

/**
 * Created by arturgoms on 22/02/16.
 */
public class ProjectPage extends Fragment implements View.OnClickListener{

    public ProjectPage() {
    }
    private Button btnNew;

    Button btnOpen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.project_page,container,false);
        btnNew = (Button) rootView.findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopNew.class);
                startActivity(intent);

            }
        });
        btnOpen = (Button) rootView.findViewById(R.id.btnOpen);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PopOpen.class);
                startActivity(intent);
            }
        });

        return rootView;

    }


    @Override
    public void onClick(View v) {

    }
}

