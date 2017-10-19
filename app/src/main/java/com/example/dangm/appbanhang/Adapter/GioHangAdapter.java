package com.example.dangm.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangm.appbanhang.Activity.GioHangActivity;
import com.example.dangm.appbanhang.Activity.MainActivity;
import com.example.dangm.appbanhang.Model.GioHang;
import com.example.dangm.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dangm on 10/16/2017.
 */

public class GioHangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GioHang> gioHangArrayList = new ArrayList<>();

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View row = convertView;

        if(row == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_giohang, null);

            holder.txtTenSP = row.findViewById(R.id.rowtensanphamgiohang);
            holder.txtGiaSP = row.findViewById(R.id.rowgiasanphamgiohang);
            holder.imgHinhSP = row.findViewById(R.id.imghinhgiohang);
            holder.btnMinus = row.findViewById(R.id.btnMinus);
            holder.btnValues = row.findViewById(R.id.btnValues);
            holder.btnPlus = row.findViewById(R.id.btnPlus);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GioHang gioHang = gioHangArrayList.get(position);
        holder.txtTenSP.setText(gioHang.getTensp());
        DecimalFormat dinhdang = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText(dinhdang.format(gioHang.getGiapsp()));
        holder.btnValues.setText(gioHang.getSoluong() + "");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(holder.imgHinhSP);

        final int soluong = Integer.parseInt(holder.btnValues.getText().toString());

        if(soluong >= 10) {
            holder.btnPlus.setVisibility(View.INVISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        } else if(soluong <= 1) {
            holder.btnMinus.setVisibility(View.INVISIBLE);
        } else if(soluong >= 1) {
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.btnPlus.setVisibility(View.VISIBLE);
        }

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(holder.btnValues.getText().toString()) + 1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiapsp();
                MainActivity.manggiohang.get(position).setSoluong(soluongmoi);
                long giamoi = (giaht * soluongmoi) / slht;
                MainActivity.manggiohang.get(position).setGiapsp(giamoi);


                if(soluongmoi > 9) {
                    holder.btnPlus.setVisibility(View.INVISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(soluongmoi));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(soluongmoi));
                }

                DecimalFormat dinhdang = new DecimalFormat("###,###,###");
                holder.txtGiaSP.setText(dinhdang.format(giamoi) + " Đ");
                GioHangActivity.CheckData();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(holder.btnValues.getText().toString()) - 1;
                int slht = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiapsp();
                MainActivity.manggiohang.get(position).setSoluong(soluongmoi);
                long giamoi = (giaht * soluongmoi) / slht;
                MainActivity.manggiohang.get(position).setGiapsp(giamoi);


                if(soluongmoi < 2) {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.INVISIBLE);
                    holder.btnValues.setText(String.valueOf(soluongmoi));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(soluongmoi));
                }

                DecimalFormat dinhdang = new DecimalFormat("###,###,###");
                holder.txtGiaSP.setText(dinhdang.format(giamoi) + " Đ");
                GioHangActivity.CheckData();
            }
        });
        return row;
    }

    public class ViewHolder {
        TextView txtTenSP, txtGiaSP;
        Button btnMinus, btnValues, btnPlus;
        ImageView imgHinhSP;
    }

}
