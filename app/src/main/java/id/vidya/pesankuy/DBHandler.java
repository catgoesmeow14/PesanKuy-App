package id.vidya.pesankuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private Context context;

    // Deklarasi nama database dan versinya
    private static final String DATABASE_NAME = "pesankuy.db";

    //	value default versi database adalah 1
    private static final int DATABASE_VERSION = 1;

    // Deklarasi variabel untuk nama tabel dan kolom
    private static final String TABLE_NAME = "tb_pesanan";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOMOR_MEJA = "nomor_meja";
    private static final String COLUMN_NAMA_PEMESAN = "nama_pemesan";
    private static final String COLUMN_PAKET_MENU = "paket_menu";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_TAMBAHAN = "tambahan";

    //	Constructor untuk ambil context dari activity yang menggunakan handler ini
    public DBHandler(@Nullable Context context) {
        //	Factory null artinya adalah Cursor SQLite yang digunakan adalah versi standar
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    //	Method untuk membuat tabel pesanan
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMOR_MEJA + " TEXT, " + COLUMN_NAMA_PEMESAN + " TEXT, "
                + COLUMN_PAKET_MENU + " TEXT, " + COLUMN_JUMLAH + " TEXT, "
                + COLUMN_TAMBAHAN + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    // Method yang dipanggil ketika ada pembaruan versi database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //	Method untuk menambah data pesanan ke database
    void tambahPesanan(String nomorMeja, String namaPemesan, String paketMenu, String jumlah, String tambahan) {
        SQLiteDatabase db = this.getWritableDatabase();
        //	Content Values untuk menggabungkan sekumpulan value yang nantinya akan dipakai untuk query ke database
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOMOR_MEJA, nomorMeja);
        cv.put(COLUMN_NAMA_PEMESAN, namaPemesan);
        cv.put(COLUMN_PAKET_MENU, paketMenu);
        cv.put(COLUMN_JUMLAH, jumlah);
        cv.put(COLUMN_TAMBAHAN, tambahan);

        //	Panggil method insert untuk tambah data
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Gagal menambah data!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil menambah data ke Database!", Toast.LENGTH_SHORT).show();
        }
    }

    //	Method untuk select semua data dari tabel pesanan
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        //	Jika database tidak kosong maka cursor diisi dengan hasil eksekusi perintah select all
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //    Untuk read baris dengan ID tertentu
    Cursor readByID(String id_pesanan) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id_pesanan;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //   Untuk read ID dari baris paling baru (baris terakhir)
    Cursor readLastRowID() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //	Method untuk perbarui data sesuai dengan ID yang dipilih
    void updateData(String row_id, String nomorMeja, String namaPemesan, String paketMenu, String jumlah, String tambahan) {
        SQLiteDatabase db = this.getWritableDatabase();

        //	Content Values digunakan untuk mengabunggkan sekumpulan value yang nantinya akan dipakai untuk query ke database
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOMOR_MEJA, nomorMeja);
        cv.put(COLUMN_NAMA_PEMESAN, namaPemesan);
        cv.put(COLUMN_PAKET_MENU, paketMenu);
        cv.put(COLUMN_JUMLAH, jumlah);
        cv.put(COLUMN_TAMBAHAN, tambahan);

        //	Panggil method update untuk perbarui data, dengan ID sebagai where clause
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Gagal memperbarui data!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil memperbarui data!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDataByID(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Gagal menghapus data!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil menghapus data!", Toast.LENGTH_SHORT).show();
        }
    }
}
