package com.uti.cuanku;

import static android.provider.BaseColumns._ID;
import static com.uti.cuanku.DBContract.TABLE_KEUANGAN;
import static com.uti.cuanku.DBContract.KeuanganColumns.NOMINAL;
import static com.uti.cuanku.DBContract.KeuanganColumns.JUDUL;
import static com.uti.cuanku.DBContract.KeuanganColumns.KETERANGAN;
import static com.uti.cuanku.DBContract.KeuanganColumns.TANGGAL;
import static com.uti.cuanku.DBContract.KeuanganColumns.KATEGORI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uti.cuanku.ListItem;

import java.util.ArrayList;

public class KeuanganHelper {
    private static String DB_TABLE = TABLE_KEUANGAN;
    private Context context;
    private DBConfig dbHelper;
    private SQLiteDatabase database;

    public KeuanganHelper(Context context) {
        this.context = context;
    }

    public KeuanganHelper open() throws SQLException{
        dbHelper = new DBConfig(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<ListItem> query(){
        ArrayList<ListItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DB_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        ListItem item;
        if (cursor.getCount() > 0){
            do {
                item = new ListItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                item.setNominal(cursor.getString(cursor.getColumnIndexOrThrow(NOMINAL)));
                item.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                item.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(KETERANGAN)));
                item.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));
                item.setKategori(cursor.getString(cursor.getColumnIndexOrThrow(KATEGORI)));

                arrayList.add(item);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insert(ListItem item){
        ContentValues inValues = new ContentValues();
        inValues.put(NOMINAL, item.getNominal());
        inValues.put(JUDUL, item.getJudul());
        inValues.put(KETERANGAN, item.getKeterangan());
        inValues.put(TANGGAL, item.getTanggal());
        inValues.put(KATEGORI, item.getKategori());
        return database.insert(DB_TABLE,null,inValues);
    }

    public int update(ListItem item){
        ContentValues args = new ContentValues();
        args.put(NOMINAL, item.getNominal());
        args.put(JUDUL, item.getJudul());
        args.put(KETERANGAN, item.getKeterangan());
        args.put(TANGGAL, item.getTanggal());
        args.put(KATEGORI, item.getKategori());
        return database.update(DB_TABLE, args, _ID + "= '"+item.getId() + "'",null);
    }

    public int delete(int id){
        return database.delete(TABLE_KEUANGAN, _ID + "= '"+id+"'",null);
    }
}
