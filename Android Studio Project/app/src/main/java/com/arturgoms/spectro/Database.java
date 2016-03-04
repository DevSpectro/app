package com.arturgoms.spectro;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.*;

/**
 * Created by arturgoms on 24/02/16.
 */
public class Database extends SQLiteOpenHelper{

    public Database(Context context){

        super(context, "Projetos", null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
