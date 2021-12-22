package id.vidya.pesankuy;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.vidya.pesankuy.Adapter.HistoryPesananAdapter;


public class HomeFragment extends Fragment {
    private DBHandler dbHandler;

    private ArrayList<String> id_pesanan, nomor_meja, nama_pemesan, paket_menu, jumlah, tambahan;

    public HistoryPesananAdapter historyPesananAdapter;
    private RecyclerView rvPesanan;
    private LinearLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_pesanan, container, false);

        // inisiasi objek DBHandler
        dbHandler = new DBHandler(getContext());
        id_pesanan = new ArrayList<>();
        nomor_meja = new ArrayList<>();
        nama_pemesan = new ArrayList<>();
        paket_menu = new ArrayList<>();
        jumlah = new ArrayList<>();
        tambahan = new ArrayList<>();

        getDataFromDatabase();

        // setelah methode dipanggil, seluruh variabel akan keisi dengan data yang ada di databae
        historyPesananAdapter = new HistoryPesananAdapter(view.getContext(), id_pesanan, nomor_meja, nama_pemesan, paket_menu, jumlah, tambahan);
        layoutManager = new LinearLayoutManager(view.getContext());
        // reverse layout agar data yang terbaru muncul di paling atas
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rvPesanan = view.findViewById(R.id.rvHistoryPesanan);
        rvPesanan.setLayoutManager(layoutManager);
        rvPesanan.setAdapter(historyPesananAdapter);

        return view;
    }

    private void getDataFromDatabase() {
        Cursor cursor = dbHandler.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Belum ada data!", Toast.LENGTH_SHORT).show();
        } else {
            //mengambil masing-masing baris
            while (cursor.moveToNext()) {
                id_pesanan.add(cursor.getString(0));
                nomor_meja.add(cursor.getString(1));
                nama_pemesan.add(cursor.getString(2));
                paket_menu.add(cursor.getString(3));
                jumlah.add(cursor.getString(4));
                tambahan.add(cursor.getString(5));
            }
        }
    }

    // memberi tahu ke adapter jika ada perubahan, buat refresh tampilan list
    @Override
    public void onResume() {
        super.onResume();
        historyPesananAdapter.notifyDataSetChanged();
    }
}