package id.vidya.pesankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class UpdatePesananActivity extends AppCompatActivity {

    private String dataNoMeja, dataNamaPemesan, dataPaketMenu, dataJumlah, dataTambahan, id_pesanan;
    private EditText edtNoMeja, edtNamaPemesan;
    private TextView tvlblNoMeja, tvlblNamaPemesan, tvlblPilihMenu, tvlblJumlahPesanan, tvJumlahPesanan, tvlblSaus;
    private CheckBox cbSausTomat, cbSausPedas, cbKecapManis;
    private SeekBar sbJumlahPesanan;
    private RadioGroup rgPaketMenu;
    private RadioButton rbPaket1, rbPaket2, rbPaket3, rbPaketTerpilih;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pesanan);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_formorder);

        //  Ambil data dari Activity sebelumnya
        id_pesanan = getIntent().getStringExtra("id_pesanan");

        getDataByID();

        edtNoMeja = findViewById(R.id.edtNoMeja);
        edtNamaPemesan = findViewById(R.id.edtNamaPemesan);
        rgPaketMenu = findViewById(R.id.rgPaketMenu);
        rbPaket1 = findViewById(R.id.rbPaket1);
        rbPaket2 = findViewById(R.id.rbPaket2);
        rbPaket3 = findViewById(R.id.rbPaket3);
        sbJumlahPesanan = findViewById(R.id.sbJumlahPesanan);
        cbSausTomat = findViewById(R.id.cbSausTomat);
        cbSausPedas = findViewById(R.id.cbSausPedas);
        cbKecapManis = findViewById(R.id.cbKecapManis);
        tvlblNoMeja = findViewById(R.id.tvlblNoMeja);
        tvlblNamaPemesan = findViewById(R.id.tvlblNamaPemesan);
        tvlblPilihMenu = findViewById(R.id.tvlblPilihMenu);
        tvlblJumlahPesanan = findViewById(R.id.tvlblJumlahPesanan);
        tvJumlahPesanan = findViewById(R.id.tvJumlahPesanan);
        tvlblSaus = findViewById(R.id.tvlblSaus);
        btnUpdate = findViewById(R.id.btnUpdate);

        setFormData();

        rbPaket1.setOnClickListener(v -> {
            rbPaket1.setChecked(true);
            rbPaket2.setChecked(false);
            rbPaket3.setChecked(false);
        });

        rbPaket2.setOnClickListener(v -> {
            rbPaket1.setChecked(false);
            rbPaket2.setChecked(true);
            rbPaket3.setChecked(false);
        });

        rbPaket3.setOnClickListener(v -> {
            rbPaket1.setChecked(false);
            rbPaket2.setChecked(false);
            rbPaket3.setChecked(true);
        });

        sbJumlahPesanan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Integer progress = i + 1;
                tvJumlahPesanan.setText(progress.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });

    }

    private void setFormData() {
        // Isi input nomer meja
        edtNoMeja.setText(dataNoMeja);

        // Isi input nama pemesan
        edtNamaPemesan.setText(dataNamaPemesan);

        // Isi input paket menu
        if (dataPaketMenu.equals("Paket 1 (Bakso Ayam + Es Teh)")) {
            rbPaket1.setChecked(true);
        } else if (dataPaketMenu.equals("Paket 2 (Ayam Goreng + Lemon Tea)")) {
            rbPaket2.setChecked(true);
        } else {
            rbPaket3.setChecked(true);
        }

        // Isi input jumlah
        sbJumlahPesanan.setProgress(Integer.valueOf(dataJumlah) - 1);
        tvJumlahPesanan.setText(dataJumlah);

        // Isi input tambahan
        if (dataTambahan.contains("Saus Tomat")) {
            cbSausTomat.setChecked(true);
        }
        if (dataTambahan.contains("Saus Pedas")) {
            cbSausPedas.setChecked(true);
        }
        if (dataTambahan.contains("Kecap Manis")) {
            cbKecapManis.setChecked(true);
        }
    }

    private void getDataByID() {
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

    private void validasiData() {

        // Mengambil semua input user
        dataNoMeja = edtNoMeja.getText().toString();
        dataNamaPemesan = edtNamaPemesan.getText().toString();

        // Mengambil input Paket Menu
        dataPaketMenu = "";
        int idPaketTerpilih = rgPaketMenu.getCheckedRadioButtonId();
        rbPaketTerpilih = findViewById(idPaketTerpilih);
        //  dataPaketMenu = rbPaketTerpilih.getText().toString();
        if (rbPaket1.isChecked()) {
            dataPaketMenu = "Paket 1 (Bakso Ayam + Es Teh)";
        }
        if (rbPaket2.isChecked()) {
            dataPaketMenu = "Paket 2 (Ayam Goreng + Lemon Tea)";
        }
        if (rbPaket3.isChecked()) {
            dataPaketMenu = "Paket 3 (Nasi Goreng + Teh Tarik)";
        }

        // Mengambil input seekbar jumlah
        Integer jumlahPesanan = sbJumlahPesanan.getProgress() + 1;
        dataJumlah = jumlahPesanan.toString();

        //  Mengambil input checkbox tambahan
        //  jika tidak diberi value, hasilnya null ketika pengecekan validasi check empty
        //  Jika null, ketika dice empty jadinya error sehingga diakalin, biar pas cek empty tidak dianggap null
        dataTambahan = "";
        // Jika Saus Tomat Terpilih
        if (cbSausTomat.isChecked()) {
            dataTambahan = dataTambahan + cbSausTomat.getText().toString() + ", ";
        }

        // Jika Saus Pedas Terpilih
        if (cbSausPedas.isChecked()) {
            dataTambahan = dataTambahan + cbSausPedas.getText().toString() + ", ";
        }

        // Jika Kecap Maniss Terpilih
        if (cbKecapManis.isChecked()) {
            dataTambahan = dataTambahan + cbKecapManis.getText().toString() + ", ";
        }

        //menghapus koma paling belakang
        if (!dataTambahan.isEmpty()) {
            dataTambahan = dataTambahan.substring(0, dataTambahan.length() - 2);
        }
        if (dataTambahan.isEmpty()) {
            dataTambahan = "Tidak Ada Tambahan";
        }

        // Validasi Input User
        if (dataNoMeja.isEmpty()) {
            //biar keyboard langsung muncul di edit text yang kosong
            edtNoMeja.requestFocus();
            edtNoMeja.setError("Silahkan isi Nomer Meja!");
        } else if (dataNamaPemesan.isEmpty()) {
            edtNamaPemesan.requestFocus();
            edtNamaPemesan.setError("Silahkan isi Nama Pemesan!");
        } else if (dataPaketMenu.isEmpty()) {
            rgPaketMenu.requestFocus();
        } else if (dataJumlah.isEmpty()) {
            sbJumlahPesanan.requestFocus();
        } else {
            perbaruiDataPesanan();
        }
    }

    private void perbaruiDataPesanan() {
        DBHandler db = new DBHandler(getApplicationContext());
        db.updateData(id_pesanan, dataNoMeja, dataNamaPemesan, dataPaketMenu, dataJumlah, dataTambahan);

        Intent toHasilPemesananActivity = new Intent(UpdatePesananActivity.this, HasilPesananActivity.class);
        toHasilPemesananActivity.putExtra("id_pesanan", id_pesanan);
        toHasilPemesananActivity.putExtra("updated", true);

        startActivity(toHasilPemesananActivity);
        finish();
    }

}