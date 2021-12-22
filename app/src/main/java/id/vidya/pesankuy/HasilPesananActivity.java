package id.vidya.pesankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HasilPesananActivity extends AppCompatActivity {
    private String dataNoMeja, dataNamaPemesan, dataPaketMenu, dataJumlah, dataTambahan, id_pesanan;
    private Boolean updated;
    private TextView tvNoMeja, tvNamaPemesan, tvNamaMenu, tvJumlahPesanan, tvTambahan, tvStatus;
    private Button btnCekRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pesanan);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_leaf);

        //  Ambil data dari Activity sebelumnya
        id_pesanan = getIntent().getStringExtra("id_pesanan");

        // Ambil status update apabila dari UpdatePesananActivity
        updated = getIntent().getBooleanExtra("updated", false);

        //  Find view by ID
        tvNoMeja = findViewById(R.id.tvNoMeja);
        tvNamaPemesan = findViewById(R.id.tvNamaPemesan);
        tvNamaMenu = findViewById(R.id.tvNamaMenu);
        tvJumlahPesanan = findViewById(R.id.tvJumlahPesanan);
        tvTambahan = findViewById(R.id.tvSaus);
        btnCekRiwayat = findViewById(R.id.btnCekRiwayat);
        tvStatus = findViewById(R.id.tvStatus);

        // Ambil data pesanan sesuai ID
        getDataPesananByID(id_pesanan);

        //  Set text
        tvNoMeja.setText("Meja " + dataNoMeja);
        tvNamaPemesan.setText(dataNamaPemesan);
        tvNamaMenu.setText(dataPaketMenu);
        tvJumlahPesanan.setText(dataJumlah);
        tvTambahan.setText(dataTambahan);

        // Set text status
        if (updated) {
            tvStatus.setText("Pemesanan Diperbarui");
        }

        btnCekRiwayat.setOnClickListener(v -> {
            Toast.makeText(HasilPesananActivity.this, "Anda akan melihat riwayat pesanan", Toast.LENGTH_SHORT).show();
            toMainHistoryPesananAcitivy();
        });
    }

    private void toMainHistoryPesananAcitivy() {
        Intent toMainHistoryPesananAcitivy = new Intent(HasilPesananActivity.this, MainActivity.class);
        startActivity(toMainHistoryPesananAcitivy);
        finish();
    }

    private void getDataPesananByID(String id_pesanan) {
        DBHandler db = new DBHandler(this);
        Cursor cursor = db.readByID(id_pesanan);

        while (cursor.moveToNext()) {
            dataNoMeja = cursor.getString(1);
            dataNamaPemesan = cursor.getString(2);
            dataPaketMenu = cursor.getString(3);
            dataJumlah = cursor.getString(4);
            dataTambahan = cursor.getString(5);
        }
    }

    @Override
    protected void onStart() {
        Toast.makeText(this, "Activity : On Start! || Activity di mulai", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "Activity : On Resume! || Anda melanjutkan aplikasi", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "Activity : On Pause! || Anda keluar sementara", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "Activity : On Stop! || Activity di berhentikan", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intentToHome = new Intent(HasilPesananActivity.this, MainActivity.class);
        startActivity(intentToHome);
    }
}