package id.vidya.pesankuy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.vidya.pesankuy.DBHandler;
import id.vidya.pesankuy.HasilPesananActivity;
import id.vidya.pesankuy.MainActivity;
import id.vidya.pesankuy.R;
import id.vidya.pesankuy.UpdatePesananActivity;

public class HistoryPesananAdapter extends RecyclerView.Adapter<HistoryPesananAdapter.AppViewHolder> {

    Context context;
    ArrayList id_pesanan, nomor_meja, nama_pemesan, paket_menu, jumlah, tambahan;

    public HistoryPesananAdapter(Context context,
                                 ArrayList id_pesanan,
                                 ArrayList nomor_meja,
                                 ArrayList nama_pemesan,
                                 ArrayList paket_menu,
                                 ArrayList jumlah,
                                 ArrayList tambahan) {
        this.context = context;
        this.id_pesanan = id_pesanan;
        this.nomor_meja = nomor_meja;
        this.nama_pemesan = nama_pemesan;
        this.paket_menu = paket_menu;
        this.jumlah = jumlah;
        this.tambahan = tambahan;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNoMeja.setText(String.valueOf(nomor_meja.get(position)));
        holder.tvNamaPemesan.setText(String.valueOf(nama_pemesan.get(position)));
        holder.tvNamaMenu.setText(String.valueOf(paket_menu.get(position)));
        holder.cvItemPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHasil = new Intent(view.getContext(), HasilPesananActivity.class);
                intentHasil.putExtra("id_pesanan", String.valueOf(id_pesanan.get(position)));
                intentHasil.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intentHasil);
            }
        });
        holder.ivIconUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHasil = new Intent(view.getContext(), UpdatePesananActivity.class);
                intentHasil.putExtra("id_pesanan", String.valueOf(id_pesanan.get(position)));
                intentHasil.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intentHasil);
            }
        });
        holder.ivIconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        view.getContext());

                // set title dialog
                alertDialogBuilder.setTitle("Konfirmasi");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Apakah anda yakin ingin menghapus pesanan ini?")
                        //  jika back dengan bawaan android, alertdialog-nya tidak hilang
                        .setCancelable(false)
                        .setPositiveButton("HAPUS", (dialog, id) -> {
                            DBHandler dbHandler = new DBHandler(view.getContext());
                            dbHandler.deleteDataByID(String.valueOf(id_pesanan.get(position)));
                            Toast.makeText(view.getContext(), "Data telah dihapus!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            view.getContext().startActivity(intent);
                        })
                        .setNegativeButton("KEMBALI", (dialog, id) -> dialog.cancel());

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return id_pesanan.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoMeja, tvNamaPemesan, tvNamaMenu;
        CardView cvItemPesanan;
        ImageView ivIconUpdate, ivIconDelete;

        public AppViewHolder(View view) {
            super(view);
            cvItemPesanan = view.findViewById(R.id.cvItemPesanan);
            tvNoMeja = view.findViewById(R.id.tvNoMeja);
            tvNamaPemesan = view.findViewById(R.id.tvNamaPemesan);
            tvNamaMenu = view.findViewById(R.id.tvNamaMenu);
            ivIconDelete = view.findViewById(R.id.ivIconDelete);
            ivIconUpdate = view.findViewById(R.id.ivIconUpdate);
        }
    }
}
