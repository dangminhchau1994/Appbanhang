package com.example.dangm.appbanhang.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dangm.appbanhang.Adapter.GioHangAdapter;
import com.example.dangm.appbanhang.R;
import com.example.dangm.appbanhang.Util.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    private ListView lv;
    private TextView txtStatus;
    private static TextView txtTotal;
    private Button btnPay, btnBuyFurther;
    private GioHangAdapter giohangadapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        init();
        CheckStatus();
        CheckData();
        actionBar();
        catchOnList();
        eventButton();
    }

    private void eventButton() {
        btnBuyFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() < 1) {
                    CheckConnection.showToast(GioHangActivity.this, "Bạn chưa có sản phẩm");
                } else {
                    Intent intent = new Intent(GioHangActivity.this, ThongTinActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CheckStatus() {
        if (MainActivity.manggiohang.size() <= 0) {
            giohangadapter.notifyDataSetChanged();
            txtStatus.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        } else {
            giohangadapter.notifyDataSetChanged();
            txtStatus.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
        }
    }

    private void catchOnList() {

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm này ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size() <= 0) {
                            txtStatus.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.manggiohang.remove(position);
                            giohangadapter.notifyDataSetChanged();
                            CheckData();
                            if (MainActivity.manggiohang.size() <= 0) {
                                txtStatus.setVisibility(View.VISIBLE);
                            } else {
                                giohangadapter.notifyDataSetChanged();
                                txtStatus.setVisibility(View.GONE);
                                CheckData();
                            }
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangadapter.notifyDataSetChanged();
                        CheckData();
                    }
                });

                builder.show();
                return true;
            }
        });
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

    public static void CheckData() {
        long tongtien = 0;
        for(int i = 0; i < MainActivity.manggiohang.size(); i++) {
            tongtien += MainActivity.manggiohang.get(i).getGiapsp();
        }
        DecimalFormat dinhdang = new DecimalFormat("###,###,###");
        txtTotal.setText(dinhdang.format(tongtien) + " Đ");
    }

    private void init() {
        lv = (ListView) findViewById(R.id.listgiohang);
        toolbar = (Toolbar) findViewById(R.id.toolgiohang);
        txtStatus = (TextView) findViewById(R.id.textviewgiohang);
        txtTotal = (TextView) findViewById(R.id.giatrigiohang);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnBuyFurther = (Button) findViewById(R.id.btnBuy);
        giohangadapter = new GioHangAdapter(GioHangActivity.this, MainActivity.manggiohang);
        lv.setAdapter(giohangadapter);
    }
}
