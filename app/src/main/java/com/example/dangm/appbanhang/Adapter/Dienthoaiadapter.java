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
 * Created by dangm on 10/13/2017.
 */

public class Dienthoaiadapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();

    public Dienthoaiadapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_dienthoai, null);
            holder = new ViewHolder();

            holder.txtTendienthoai = row.findViewById(R.id.rowtendienthoai);
            holder.txtgiadienthoai = row.findViewById(R.id.rowgiadienthoai);
            holder.imgHinhDienThoai = row.findViewById(R.id.rowhinhdienthoai);
            holder.txtmota = row.findViewById(R.id.rowmotadienthoai);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        SanPham sanPham = sanPhamArrayList.get(position);

        holder.txtTendienthoai.setText(sanPham.getTenSP());
        DecimalFormat dinhdangtien = new DecimalFormat("###,###,###");
        holder.txtgiadienthoai.setText(dinhdangtien.format(sanPham.getGiaSP()) + " Đ");
        holder.txtmota.setMaxLines(2);
        holder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtmota.setText(sanPham.getMota());
        Picasso.with(context).load(sanPham.getHinhSP())
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(holder.imgHinhDienThoai);

        return row;
    }

    public class ViewHolder {
        TextView txtTendienthoai, txtgiadienthoai, txtmota;
        ImageView imgHinhDienThoai;
    }
}
