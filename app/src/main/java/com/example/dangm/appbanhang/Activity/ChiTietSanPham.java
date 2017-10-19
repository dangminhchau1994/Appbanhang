package com.example.dangm.appbanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dangm.appbanhang.Model.GioHang;
import com.example.dangm.appbanhang.Model.SanPham;
import com.example.dangm.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import static com.example.dangm.appbanhang.Activity.MainActivity.manggiohang;

public class ChiTietSanPham extends AppCompatActivity {

    ImageView imgHinhSanPham;
    Toolbar toolbar;
    Spinner spn;
    TextView txtTenSP, txtGiaSP, txtMota;
    Button btnAdd;
    int id = 0;
    int idLoai = 0;
    String tensanpham = "";
    int giasp = 0;
    String mota = "";
    String hinhsanpham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        init();
        actionBar();
        getInfo();
        setSpinner();
        eventButton();
    }

    private void eventButton() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manggiohang.size() > 0) {
                    int sl = Integer.parseInt(spn.getSelectedItem().toString());
                    boolean exists = false;

                    for(int i = 0; i < manggiohang.size(); i++) {
                        if(manggiohang.get(i).getId() == id) {
                            manggiohang.get(i).setSoluong(manggiohang.get(i).getSoluong() + sl);

                            if(manggiohang.get(i).getSoluong() >= 10) {
                                manggiohang.get(i).setSoluong(10);
                            }

                            MainActivity.manggiohang.get(i).setGiapsp(MainActivity.manggiohang.get(i).getSoluong() * giasp);
                            exists = true;
                        }
                    }

                    if(exists == false) {
                        int soluong = Integer.parseInt(spn.getSelectedItem().toString());
                        long giamoi = giasp * soluong;
                        manggiohang.add(new GioHang(id, tensanpham, giamoi, soluong, hinhsanpham));
                    }

                } else {
                    int soluong = Integer.parseInt(spn.getSelectedItem().toString());
                    long giamoi = giasp * soluong;
                    manggiohang.add(new GioHang(id, tensanpham, giamoi, soluong, hinhsanpham));
                }

                Intent intent = new Intent(ChiTietSanPham.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item, soluong);
        spn.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.giohang:
                Intent intent = new Intent(ChiTietSanPham.this, GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getInfo() {
        SanPham sanpham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getIdSP();
        idLoai = sanpham.getIdLoaiSP();
        tensanpham = sanpham.getTenSP();
        giasp = sanpham.getGiaSP();
        mota = sanpham.getMota();
        hinhsanpham = sanpham.getHinhSP();
        txtTenSP.setText(tensanpham);
        DecimalFormat dinhdang = new DecimalFormat("###,###,###");
        txtGiaSP.setText(dinhdang.format(giasp) + " Đ");
        txtMota.setText(mota);
        Picasso.with(getApplicationContext()).load(hinhsanpham)
                .placeholder(R.drawable.load)
                .error(R.drawable.available)
                .into(imgHinhSanPham);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolchitiet);
        spn = (Spinner) findViewById(R.id.spnSoLuong);
        imgHinhSanPham = (ImageView) findViewById(R.id.hinhanhmota);
        txtTenSP = (TextView) findViewById(R.id.motatensanpham);
        txtGiaSP = (TextView) findViewById(R.id.motagiasanpham);
        txtMota = (TextView) findViewById(R.id.noidungmota);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }

}
