package com.uti.cuanku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.uti.cuanku.KeuanganHelper;

import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    EditText edt_nominal,edt_judul,edt_keterangan,edt_kategori,edt_tanggal;
    Button btn_simpan,btn_hapus;
    DatePickerDialog datePicker;
    String date;

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
        setContentView(R.layout.form);

        //Membuat definisi tab layout dengan fragment
//        tabLayout = findViewById(R.id.tabLayout);
//        viewPager = findViewById(R.id.viewPager);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        vpAdapter.addFragment(new TambahPemasukanFragment(), "Pemasukan");
//        vpAdapter.addFragment(new TambahPengeluaranFragment(), "Pengeluaran");
//        viewPager.setAdapter(vpAdapter);

        //Membuat Definisi untuk list item

        edt_nominal = findViewById(R.id.edt_nominal);
        edt_judul = findViewById(R.id.edt_judul);
        edt_keterangan = findViewById(R.id.edt_keterangan);
        edt_kategori = findViewById(R.id.edt_kategori);
        edt_tanggal = findViewById(R.id.edt_tanggal);
        date ="";
        btn_hapus = findViewById(R.id.btn_hapus);
        btn_simpan = findViewById(R.id.btn_simpan);
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
            btn_hapus.setVisibility(Button.VISIBLE);
            edt_nominal.setText(item.getNominal());
            edt_judul.setText(item.getJudul());
            edt_keterangan.setText(item.getKeterangan());
            edt_kategori.setText(item.getKategori());
            edt_tanggal.setText(item.getTanggal());

        }else{
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_simpan.setText(btnTitle);

        edt_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(FormData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month_of_year, int day_of_month) {
                        // set day of month , month and year value in the edit text
                        NumberFormat numberformat = new DecimalFormat("00");
                        date = year + "-" + numberformat.format(( month_of_year +1 )) + "-" + numberformat.format(day_of_month);
                        edt_tanggal.setText( numberformat.format(day_of_month) + "/" + numberformat.format(( month_of_year +1 )) + "/" + year );
                    }
                }, CurrentDateHelper.year, CurrentDateHelper.month, CurrentDateHelper.day);
                datePicker.show();
            }
        });

        btn_hapus.setOnClickListener(v -> {
            keuanganHelper.delete(item.getId());
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, pos);
            setResult(RESULT_DELETE, intent);
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keuanganHelper != null){
            keuanganHelper.close();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_simpan){
            String nominal = edt_nominal.getText().toString().trim();
            String judul = edt_judul.getText().toString().trim();
            String keterangan = edt_keterangan.getText().toString().trim();
            String kategori = edt_kategori.getText().toString().trim();
            String tanggal = edt_tanggal.getText().toString().trim();

            boolean isEmpty = false;

            if (nominal.isEmpty() || judul.isEmpty() || keterangan.isEmpty()){
                isEmpty = true;
                Toast.makeText(FormData.this, "Data Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
            }

            if (!isEmpty){
                ListItem newItem = new ListItem();
                newItem.setNominal(nominal);
                newItem.setJudul(judul);
                newItem.setKeterangan(keterangan);
                newItem.setKategori(kategori);
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
                    newItem.setKategori(kategori);
                    newItem.setTanggal(tanggal);
                    keuanganHelper.insert(newItem);

                    setResult(RESULT_ADD);
                    finish();
                }
            }
        }
    }
}