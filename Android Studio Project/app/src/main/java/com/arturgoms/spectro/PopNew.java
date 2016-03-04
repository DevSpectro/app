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
public class PopNew extends Activity{

    private EditText edtNameProj;
    private Spinner spnTipos;
    private Spinner spnPlacas;
    private Button btnCriar;
    private Button btnCancel;

    private ArrayAdapter<String> adpTipoPlacas;
    private ArrayAdapter<String> adpTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.pop_new);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCriar = (Button) findViewById(R.id.btnCriar);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopNew.this, ProjetoMain.class);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopNew.this, PopNew.class);
                finish();
            }
        });

        edtNameProj = (EditText) findViewById(R.id.edtNomeProj);

        spnPlacas = (Spinner) findViewById(R.id.spnPlacas);
        spnTipos = (Spinner) findViewById(R.id.spnTipos);

        adpTipoPlacas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adpTipoPlacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adpTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnPlacas.setAdapter(adpTipoPlacas);
        spnTipos.setAdapter(adpTipo);

        adpTipoPlacas.addAll("Arduino");
        adpTipoPlacas.addAll("ESP");
        adpTipoPlacas.addAll("RaspBerry Pi (soon)");

        adpTipo.addAll("Bluetooht HC-05");
        adpTipo.addAll("Wifi (ESP)");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int largura = dm.widthPixels;
        int altura = dm.heightPixels;

        getWindow().setLayout((int)(largura*.87),(int)(altura*.45));
    }
}
