package com.arturgoms.spectro;

import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.*;

import com.arturgoms.spectro.R;

/**
 * Created by arturgoms on 22/02/16.
 */
public class ConfigPage extends ActionBarActivity {

    private Spinner spnMainpag;

    private EditText edtName;
    private EditText edtEmail;

    Button btnName;
    Button btnEmail;



    private ArrayAdapter<String> userName;
    private ArrayAdapter<String> userEmail;
    private ArrayAdapter<String> adpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_page);

        spnMainpag = (Spinner) findViewById(R.id.spnMainpag);

        adpMain = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adpMain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpMain.addAll("Welcome");
        adpMain.addAll("Projeto 1#");
        adpMain.addAll("Projeto 2#");


        btnName = (Button) findViewById(R.id.btnName);
        btnEmail = (Button) findViewById(R.id.btnEmail);

        ArrayAdapter<String> user_Name =
                new ArrayAdapter<String>(this, R.layout.user_name);
        userName = new ArrayAdapter<String>(this, R.layout.user_name);
        ArrayAdapter<String> user_Email =
                new ArrayAdapter<String>(this, R.layout.user_email);
        userEmail = new ArrayAdapter<String>(this, R.layout.user_email);
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtnameUser = (EditText) findViewById(R.id.edtNome);
                edtnameUser.setText(getResources().getString(R.string.user_name));

            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send a message using content of the edit text widget

                EditText edtnameUser = (EditText) findViewById(R.id.edtEmail);
                edtnameUser.setText(getResources().getString(R.string.user_email));



            }
        });
    }




        //final Spinner spinner = (Spinner) rootView.findViewById(R.id.spnMainpag);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        //adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //@Override
        //public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //check = check + 1;
        //int selState = spinner.getSelectedItemPosition();
        //switch (selState) {
        //case 0:

        //break;
        //case 1:
        //FragmentManager fn = getFragmentManager();
        //fn.beginTransaction().replace(R.id.content_page, new WelcomePage()).commit();
        //break;
        //case 2:
        //    FragmentManager fm = getFragmentManager();
        // fm.beginTransaction().replace(R.id.content_page, new ConfigPage()).commit();
        //break;
        //  case 3:
        // FragmentManager fo = getFragmentManager();

        //fo.beginTransaction().replace(R.id.welcome_layout, new WelcomePage()).commit();
        // break;
        // }

        // }


        //@Override
        //public void onNothingSelected(AdapterView<?> parent) {

        //}
        // });


}




