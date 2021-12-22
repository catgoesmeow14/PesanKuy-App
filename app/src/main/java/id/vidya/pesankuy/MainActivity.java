package id.vidya.pesankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton fabtnTambahPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.BottomNavView);
        // ini menunjukkan bahwa method sudah tidak dilanjutkan pengembangannya
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // mengganti bagian fragment dengan activity HomeFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        fabtnTambahPesanan = findViewById(R.id.fabtnTambahPesanan);
        fabtnTambahPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForm = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intentForm);
                finish();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // inisiasi awal biar gak null (kalo line 47 diapus maka error)
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.homeSection:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.aboutSection:
                    selectedFragment = new AboutFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}