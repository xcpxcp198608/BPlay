package com.wiatec.bplay.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by patrick on 2017/2/10.
 */

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BPLAY";
    public static final String TABLE_NAME = "FAVORITE";
    private static final String CREATE_TABLE_CHANNEL = "create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement" +
            ",channelId integer ,sequence integer ,tag text , name text ,url text ,icon text,type text,country text,style text,visible integer)";
    private static final String DROP_TABLE_CHANNEL = "drop table if exists "+TABLE_NAME;
    private static final int VERSION = 7;

    public SQLHelper(Context context ) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHANNEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CHANNEL);
        onCreate(db);
    }
}
