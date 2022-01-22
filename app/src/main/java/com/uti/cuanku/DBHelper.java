package com.uti.cuanku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
    public static final String database_name = "DB_UAS_Mobile";
    public static final String table_name = "pengeluaran";
    public static final String clm_id = "_id";
    public static final String clm_nim = "Nim";
    public static final String clm_namalkp = "Nama";
    public static final String clm_nohp = "Nohp";
    public static final String clm_alamat = "Alamat";
    public static final String clm_jk = "JK";
    public static final String clm_status = "status";
    public static final String clm_jurusan = "jurusan";
    public static final String clm_angkatan = "angkatan";

}
