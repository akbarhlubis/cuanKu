package com.uti.cuanku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class FormData extends AppCompatActivity implements View.OnClickListener {

    //deklarasi dari variabel layout dan viewpager
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //    deklarasi variabel class
    DBConfig config;
    //    deklarasi variabel SQLite
    SQLiteDatabase db;
    Cursor cursor;
    //    deklarasi variabel komponen
    EditText edt_nominal,edt_judul,edt_keterangan,edt_tanggal;
    Button btn_simpan,btn_reset;

    public static String EXTRA_USER = "extra_user";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private ListItem item;
    private int pos;
    private KeuanganHelper keuanganHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);

        //Membuat definisi tab layout dengan fragment
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new TambahPemasukanFragment(), "Pemasukan");
        vpAdapter.addFragment(new TambahPengeluaranFragment(), "Pengeluaran");
        viewPager.setAdapter(vpAdapter);

        edt_nominal = findViewById(R.id.edt_nominal);
        edt_judul = findViewById(R.id.edt_judul);
        edt_keterangan = findViewById(R.id.edt_keterangan);
        edt_tanggal = findViewById(R.id.edt_tanggal);
        btn_reset = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(this);

        keuanganHelper = new KeuanganHelper(this);
        keuanganHelper.open();

        item = getIntent().getParcelableExtra(EXTRA_USER);
        if (item != null){
            pos = getIntent().getIntExtra(EXTRA_POSITION,0);
            isEdit = true;
        }

        String actionBarTitle = null;
        String btnTitle = null;

        if (isEdit){
            actionBarTitle = "Ubah";
            btnTitle = "Update";
            edt_nominal.setText(item.getNominal());
            edt_judul.setText(item.getJudul());
            edt_keterangan.setText(item.getKeterangan());
            edt_tanggal.setText(item.getTanggal());
        }else{
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_simpan.setText(btnTitle);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_simpan){
            String nominal = edt_nominal.getText().toString().trim();
            String judul = edt_judul.getText().toString().trim();
            String keterangan = edt_keterangan.getText().toString().trim();
            String tanggal = edt_tanggal.getText().toString().trim();
            String kategori = edt_tanggal.getText().toString().trim();

            boolean isEmpty = false;

            if (nominal.isEmpty() || judul.isEmpty() || keterangan.isEmpty() || tanggal.isEmpty()){
                isEmpty = true;
                Toast.makeText(FormData.this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
            }

            if (!isEmpty){
                ListItem newItem = new ListItem();
                newItem.setNominal(nominal);
                newItem.setJudul(judul);
                newItem.setKeterangan(keterangan);
                newItem.setTanggal(tanggal);

                Intent intent = new Intent();

                /*
                Jika merupakan edit setresultnya UPDATE, dan jika bukan maka setresultnya ADD
                 */
                if (isEdit){
                    newItem.setId(item.getId());
                    keuanganHelper.update(newItem);

                    intent.putExtra(EXTRA_POSITION, pos);
                    setResult(RESULT_UPDATE, intent);
                    finish();
                }else{
                    newItem.setNominal(nominal);
                    newItem.setJudul(judul);
                    newItem.setKeterangan(keterangan);
                    newItem.setTanggal(tanggal);
                    keuanganHelper.insert(newItem);

                    setResult(RESULT_ADD);
                    finish();
                }
            }
        }

    }
}