package com.uti.cuanku;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ItemHolder> {

    LinkedList<ListItem> listItem;
    private Activity activity;

    public DataAdapter(Activity activity) {
        this.activity = activity;
    }

    public LinkedList<ListItem> getListItem() {
        return listItem;
    }

    public void setListItem(LinkedList<ListItem> listItem) {
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public DataAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item,parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ItemHolder holder, int position) {
        holder.nominal.setText(listItem.get(position).getNominal());
        holder.judul.setText(listItem.get(position).getJudul());
        holder.kategori.setText(listItem.get(position).getKategori());
        holder.cv_item.setOnClickListener(new CustomClick(position, (view, position1) -> {
            Intent intent = new Intent(activity, FormData.class);
            intent.putExtra(FormData.EXTRA_POSITION,position1);
            intent.putExtra(FormData.EXTRA_USER,getListItem().get(position1));
            activity.startActivityForResult(intent,FormData.REQUEST_UPDATE);
        }));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView nominal, judul, kategori;
        CardView cv_item;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.nominal);
            judul = itemView.findViewById(R.id.judul);
            kategori = itemView.findViewById(R.id.kategori);
            cv_item = itemView.findViewById(R.id.cv_item);
        }
    }
}
