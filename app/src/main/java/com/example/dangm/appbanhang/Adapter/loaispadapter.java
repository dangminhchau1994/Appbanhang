package com.example.dangm.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangm.appbanhang.Model.loaiSP;
import com.example.dangm.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dangm on 9/14/2017.
 */

public class loaispadapter extends BaseAdapter {

    private Context context;
    private ArrayList<loaiSP> loaiSPArrayList = new ArrayList<>();

    public loaispadapter(Context context, ArrayList<loaiSP> loaiSPArrayList) {
        this.context = context;
        this.loaiSPArrayList = loaiSPArrayList;
    }

    @Override
    public int getCount() {

        return loaiSPArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSPArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        View row = view;

        if (row == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.loaisp_row, null);

            holder.txtTenLoai = row.findViewById(R.id.rowTenLoai);
            holder.imgHinhLoai = row.findViewById(R.id.rowHinhLoai);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        loaiSP loaiSP = loaiSPArrayList.get(i);

        holder.txtTenLoai.setText(loaiSP.getTenLoai());
        Picasso.with(context).load(loaiSP.getHinhloai())
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(holder.imgHinhLoai);

        return row;
    }

    private class ViewHolder {
        TextView txtTenLoai;
        ImageView imgHinhLoai;
    }

}
