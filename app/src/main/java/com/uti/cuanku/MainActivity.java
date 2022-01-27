package com.uti.cuanku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView recyclerView;
//    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton add_cuan = findViewById(R.id.add_cuan);

        add_cuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:ini intent yang sifatnya temporary alias sementara, buat uji coba aja. note by akbar
                startActivity(new Intent(MainActivity.this, FormData.class));
            }
        });

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