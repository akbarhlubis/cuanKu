package com.uti.cuanku;

import android.provider.BaseColumns;

public class DBContract {
    static String TABLE_KEUANGAN = "keuangan";

    static final class KeuanganColumns implements BaseColumns {
        static String NOMINAL = "nominal";
        static String JUDUL = "judul";
        static String KETERANGAN = "keterangan";
        static String KATEGORI = "kategori";
    }
}
