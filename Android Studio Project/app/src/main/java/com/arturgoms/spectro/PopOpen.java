package com.arturgoms.spectro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by arturgoms on 24/02/16.
 */
public class PopOpen extends Activity{

    private Spinner spnProjetos;
    private Button btnAbrir;
    private Button btnCancel;

    private ArrayAdapter<String> adpProjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.pop_open);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopOpen.this, PopOpen.class);
                finish();
            }
        });


        spnProjetos = (Spinner) findViewById(R.id.spnProjetos);


        adpProjetos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adpProjetos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spnProjetos.setAdapter(adpProjetos);

        adpProjetos.addAll("-");
        adpProjetos.addAll("Projeto #1");
        adpProjetos.addAll("Projeto #2");
        adpProjetos.addAll("Projeto #3");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int largura = dm.widthPixels;
        int altura = dm.heightPixels;

        getWindow().setLayout((int)(largura*1),(int)(altura*.30));
    }
}
