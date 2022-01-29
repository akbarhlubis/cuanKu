package com.uti.cuanku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static String DB_NAME = "data_keuangan";
    private static final int DB_VERSION = 1;

    public static final String SQL_CREATE_TABLE_KEUANGAN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DBContract.TABLE_KEUANGAN,
            DBContract.KeuanganColumns._ID,
            DBContract.KeuanganColumns.NOMINAL,
            DBContract.KeuanganColumns.JUDUL,
            DBContract.KeuanganColumns.KETERANGAN,
            DBContract.KeuanganColumns.KATEGORI);

    public DBConfig(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_KEUANGAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_KEUANGAN);
        onCreate(db);
    }
}
