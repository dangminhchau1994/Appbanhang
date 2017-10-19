package com.example.dangm.appbanhang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangm.appbanhang.Activity.ChiTietSanPham;
import com.example.dangm.appbanhang.Model.SanPham;
import com.example.dangm.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dangm on 9/27/2017.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();

    public SanPhamAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanpham_row, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = sanPhamArrayList.get(position);
        holder.txtTenSP.setText(sanPham.getTenSP());
        DecimalFormat dinhdang = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText(dinhdang.format(sanPham.getGiaSP()) + " Đ");
        Log.d("AAA", position + "\t" + sanPham.getHinhSP());
        Picasso.with(context).load(sanPham.getHinhSP())
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder  extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView txtTenSP, txtGiaSP;

        public ItemHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgHinhSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", sanPhamArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

}
