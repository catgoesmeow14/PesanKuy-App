package id.vidya.pesankuy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    private String dataNoMeja, dataNamaPemesan, dataPaketMenu, dataJumlah, dataTambahan, id_pesanan;
    private EditText edtNoMeja, edtNamaPemesan;
    private TextView tvlblNoMeja, tvlblNamaPemesan, tvlblPilihMenu, tvlblJumlahPesanan, tvJumlahPesanan, tvlblSaus;
    private CheckBox cbSausTomat, cbSausPedas, cbKecapManis;
    private SeekBar sbJumlahPesanan;
    private RadioGroup rgPaketMenu;
    private RadioButton rbPaket1, rbPaket2, rbPaket3, rbPaketTerpilih;
    private Button btnPesan;
    private StringBuilder rekapDataPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_formorder);

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
        btnPesan = findViewById(R.id.btnPesan);

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

        // Cetak progress SeekBar
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

        // On Click Listener tombol pesan
        btnPesan.setOnClickListener(view -> validasiData());

    }

    private void validasiData() {

        // Mengambil semua input user
        dataNoMeja = edtNoMeja.getText().toString();
        dataNamaPemesan = edtNamaPemesan.getText().toString();

        //  Mengambil input Paket Menu
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

        //  Mengambil input seekbar jumlah
        Integer jumlahPesanan = sbJumlahPesanan.getProgress() + 1;
        dataJumlah = jumlahPesanan.toString();

        //  Mengambil input checkbox tambahan
        //  jika tidak diberi value, hasilnya null ketika pengecekan validasi check empty
        //  Jika null, ketika dice empty jadinya error sehingga diakalin, biar pas cek empty tidak dianggap null
        dataTambahan = "";
        //  Jika Saus Tomat Terpilih
        if (cbSausTomat.isChecked()) {
            dataTambahan = dataTambahan + cbSausTomat.getText().toString() + ", ";
        }

        //  Jika Saus Pedas Terpilih
        if (cbSausPedas.isChecked()) {
            dataTambahan = dataTambahan + cbSausPedas.getText().toString() + ", ";
        }

        //  Jika Kecap Maniss Terpilih
        if (cbKecapManis.isChecked()) {
            dataTambahan = dataTambahan + cbKecapManis.getText().toString() + ", ";
        }

        //  menghapus koma paling belakang
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
            simpanDataPesanan();
        }
    }

    private void simpanDataPesanan() {
        // Rekap data input user dengan StringBuilder
        rekapDataPesanan = new StringBuilder();
        // make stringbuilder karena seluruh inputan akan ditampilkan di alertdialog
        // sedangkan alertdialog cuma bisa 1 string, jadi harus digabung
        rekapDataPesanan.append("Nomer Meja : " + dataNoMeja + "\n\n");
        rekapDataPesanan.append("Nama Pemesan : " + dataNamaPemesan + "\n\n");
        rekapDataPesanan.append("Paket Menu : " + dataPaketMenu + "\n\n");
        rekapDataPesanan.append("Jumlah : " + dataJumlah + "\n\n");
        rekapDataPesanan.append("Tambahan : " + dataTambahan + "\n\n");

        // create objek alert dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Apakah data sudah benar?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(rekapDataPesanan.toString())
                //  jika diback, alertdialognya tidak hilang
                .setCancelable(false)
                .setPositiveButton("PESAN", (dialog, id) -> {
                    resetFormPesanan();
                    saveDataPesanan();
                    getLastRowID();
                    toHasilPesananActivity();
                    Toast.makeText(getApplicationContext(), "Data telah disimpan!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("KEMBALI", (dialog, id) -> dialog.cancel());

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void resetFormPesanan() {
        // me-Reset semua input
        edtNoMeja.getText().clear();
        edtNamaPemesan.getText().clear();
        rbPaket1.setChecked(true);
        sbJumlahPesanan.setProgress(0);
        cbSausTomat.setChecked(false);
        cbSausPedas.setChecked(false);
        cbKecapManis.setChecked(false);
    }

    private void saveDataPesanan() {
        // Save ke SQLite
        DBHandler myDatabaseHelper = new DBHandler(this);
        myDatabaseHelper.tambahPesanan(dataNoMeja, dataNamaPemesan, dataPaketMenu, dataJumlah, dataTambahan);

    }

    private void getLastRowID() {
        DBHandler db = new DBHandler(this);
        Cursor cursor = db.readLastRowID();

        while (cursor.moveToNext()) {
            id_pesanan = cursor.getString(0);
        }
    }

    private void toHasilPesananActivity() {
        // Intent untuk pindah ke activity hasil
        Intent intentToHasil = new Intent(this, HasilPesananActivity.class);
        intentToHasil.putExtra("id_pesanan", id_pesanan);
        startActivity(intentToHasil);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentToHome = new Intent(FormActivity.this, MainActivity.class);
        startActivity(intentToHome);
    }
}