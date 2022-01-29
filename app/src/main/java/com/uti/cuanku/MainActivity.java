package com.uti.cuanku;

import static com.uti.cuanku.FormData.REQUEST_UPDATE;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private RecyclerView recyclerView;
//    private TextView emptyView;

    RecyclerView recyclerView;
    FloatingActionButton fab;

    private LinkedList<ListItem> list;
    private DataAdapter adapter;
    private KeuanganHelper keuanganHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FloatingActionButton add_cuan = findViewById(R.id.add_cuan);
//
//        add_cuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO:ini intent yang sifatnya temporary alias sementara, buat uji coba aja. note by akbar
//                startActivity(new Intent(MainActivity.this, FormData.class));
//            }
//        });

        //        //Percobaan Empty View
//        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recycler_view);
//        emptyView = (TextView) emptyView.findViewById(R.id.empty_view);
//
//        if (dataset.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//        }
//        else {
//            recyclerView.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        fab = findViewById(R.id.add_cuan);
        fab.setOnClickListener(this);

        keuanganHelper = new KeuanganHelper(this);
        keuanganHelper.open();

        list = new LinkedList<>();
        adapter = new DataAdapter(this);
        adapter.setListItem(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_cuan){
            startActivityForResult(new Intent(MainActivity.this,FormData.class),FormData.REQUEST_ADD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FormData.REQUEST_ADD){
            if (resultCode == FormData.RESULT_ADD){
                showSnackbarMessage("Satu data berhasil ditambahkan");
                Objects.requireNonNull(recyclerView.getLayoutManager()).smoothScrollToPosition(recyclerView,new RecyclerView.State(),0);
            }
        }

        else if (requestCode == REQUEST_UPDATE) {
            /*
            Akan dipanggil jika result codenya UPDATE
            Semua data di load kembali dari awal
            */
            if (resultCode == FormData.RESULT_UPDATE) {
                showSnackbarMessage("Satu item berhasil diubah");
                int position = data.getIntExtra(FormData.EXTRA_POSITION, 0);
                Objects.requireNonNull(recyclerView.getLayoutManager()).smoothScrollToPosition(recyclerView, new RecyclerView.State(), position);
            }
            /*
            Akan dipanggil jika result codenya DELETE
            Delete akan menghapus data dari list berdasarkan dari position
            */
            else if (resultCode == FormData.RESULT_DELETE) {
                int position = data.getIntExtra(FormData.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListItem(list);
                adapter.notifyDataSetChanged();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keuanganHelper != null){
            keuanganHelper.close();
        }
    }

    private void showSnackbarMessage(String mes) {
        Snackbar.make(recyclerView,mes,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    //TODO: bermasalah di item menu "Keluar Aplikasi" yaitu pada line #61 sampai #65 @akbarkbarhlubis
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.about){
            startActivity(new Intent(this, AboutActivity.class));
        } else if (item.getItemId() == R.id.exit) {
            finish();
            System.exit(0);
        }
        return true;
    }
}