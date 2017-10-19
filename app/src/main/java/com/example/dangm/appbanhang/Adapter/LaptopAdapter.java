package com.example.dangm.appbanhang.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangm.appbanhang.Model.SanPham;
import com.example.dangm.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dangm on 10/14/2017.
 */

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();

    public LaptopAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @Override
    public int getCount() {
        return sanPhamArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhamArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View row = convertView;

        if(row == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.laptop_row, null);

            holder.txtTenLaptop = row.findViewById(R.id.rowtenlaptop);
            holder.txtGiaLaptop = row.findViewById(R.id.rowgialaptop);
            holder.txtMotaLaptop = row.findViewById(R.id.rowmotalaptop);
            holder.imgHinhLaptop = row.findViewById(R.id.rowhinhlaptop);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        SanPham sanPham = sanPhamArrayList.get(position);

        holder.txtTenLaptop.setText(sanPham.getTenSP());
        DecimalFormat dinhdang = new DecimalFormat("###,###,###");
        holder.txtGiaLaptop.setText(dinhdang.format(sanPham.getGiaSP())+ " Đ");
        holder.txtMotaLaptop.setMaxLines(2);
        holder.txtMotaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtMotaLaptop.setText(sanPham.getMota());

        Picasso.with(context).load(sanPham.getHinhSP())
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(holder.imgHinhLaptop);

        return row;
    }

    public class ViewHolder {
        TextView txtTenLaptop, txtGiaLaptop, txtMotaLaptop;
        ImageView imgHinhLaptop;
    }
}
