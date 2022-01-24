package com.uti.cuanku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

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