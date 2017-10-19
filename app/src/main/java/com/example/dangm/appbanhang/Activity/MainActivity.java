package com.example.dangm.appbanhang.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangm.appbanhang.Adapter.SanPhamAdapter;
import com.example.dangm.appbanhang.Adapter.loaispadapter;
import com.example.dangm.appbanhang.Model.GioHang;
import com.example.dangm.appbanhang.Model.SanPham;
import com.example.dangm.appbanhang.Model.loaiSP;
import com.example.dangm.appbanhang.R;
import com.example.dangm.appbanhang.Util.CheckConnection;
import com.example.dangm.appbanhang.Util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ViewFlipper viewFlipper;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ArrayList<SanPham> arraySanPham = new ArrayList<>();
    private ListView lv;
    private RequestQueue requestQueue;
    private ArrayList<loaiSP> loaiSPArrayList = new ArrayList<>();
    private loaispadapter adapter;
    boolean isLoading = false;
    private SanPhamAdapter sanphamadapter;
    public static ArrayList<GioHang> manggiohang;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //Check github commit
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFliper();
            getLoaiSP();
            getSP();
            catchListLoaiSP();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối");
        }
    }



    private void getSP() {
        JsonArrayRequest jsonArray = new JsonArrayRequest(Server.sanpham,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null) {
                            int id = 0;
                            int idLoai = 0;
                            String tensp = "";
                            Integer giasp = 0;
                            String hinhsp = "";
                            String mota = "";

                            for(int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.optJSONObject(i);
                                id = jsonObject.optInt("idSP");
                                idLoai = jsonObject.optInt("idloaiSP");
                                tensp = jsonObject.optString("tenSP");
                                giasp = jsonObject.optInt("giaSP");
                                hinhsp = jsonObject.optString("hinhSP");
                                mota = jsonObject.optString("mota");
                                arraySanPham.add(new SanPham(id, idLoai, tensp, giasp, mota, hinhsp));
                            }

                            sanphamadapter = new SanPhamAdapter(getApplicationContext(), arraySanPham);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            recyclerView.setAdapter(sanphamadapter);
                            sanphamadapter.notifyDataSetChanged();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối");
                    }
                });

        requestQueue.add(jsonArray);
    }

    private void getLoaiSP() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.loaisp, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Respone", response.toString());
                        if (response != null) {

                            int idLoai = 0;
                            String tenloai = "";
                            String hinhloai = "";

                            for ( int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.optJSONObject(i);
                                idLoai = jsonObject.optInt("idLoai");
                                Log.d("IdLoai", String.valueOf(idLoai));
                                tenloai = jsonObject.optString("tenLoai");
                                Log.d("Tenloai", tenloai);
                                hinhloai = jsonObject.optString("hinhloai");
                                Log.d("hinhloai", hinhloai);
                                loaiSPArrayList.add(new loaiSP(idLoai, tenloai, hinhloai));
                            }

                            loaiSPArrayList.add(0, new loaiSP(0, "Trang chủ", "http://pngimages.net/sites/default/files/home-home-png-image-94707.png"));
                            loaiSPArrayList.add(3, new loaiSP(0, "Thông tin", "http://pngimages.net/sites/default/files/info-png-image-30062.png"));
                            loaiSPArrayList.add(4, new loaiSP(0, "Liên hệ", "https://cdn.iconscout.com/public/images/icon/premium/png-512/call-contact-dialer-phone-receiver-telephone-32dba9a3f015b045-512x512.png"));
                            adapter = new loaispadapter(getApplicationContext(), loaiSPArrayList);
                            lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void catchListLoaiSP() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    switch (position) {
                        case 0:
                            Intent trangchu = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(trangchu);
                            break;

                        case 1:
                            Intent dienthoai = new Intent(MainActivity.this, DienThoaiActivity.class);
                            dienthoai.putExtra("idLoai", loaiSPArrayList.get(position).getIdLoai());
                            startActivity(dienthoai);
                            break;

                        case 2:
                            Intent laptop = new Intent(MainActivity.this, LaptopActivity.class);
                            laptop.putExtra("idLoai", loaiSPArrayList.get(position).getIdLoai());
                            startActivity(laptop);
                            break;

                        case 3:
                            Intent thongtin = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(thongtin);
                            break;

                        case 4:
                            Intent lienhe = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(lienhe);
                            break;
                    }
                }
            }
        });
    }

    private void actionViewFliper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn1.tgdd.vn/qcao/05_09_2017_16_30_38_galaxy-note8-800-300.png");
        mangQuangCao.add("https://cdn2.tgdd.vn/qcao/05_09_2017_14_23_08_Sony-XZ1-800-300.png");
        mangQuangCao.add("https://cdn4.tgdd.vn/qcao/31_08_2017_14_28_35_Big-800-300.png");
        mangQuangCao.add("https://cdn4.tgdd.vn/qcao/11_09_2017_16_13_24_Iphone-8-Livestream-800-300.png");
        mangQuangCao.add("https://cdn1.tgdd.vn/qcao/31_08_2017_13_47_06_Big-Samsung-800-300.png");

        for( int i = 0; i < mangQuangCao.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getResources().getString(R.string.trangchu));
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
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
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navi);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlip);
        lv = (ListView) findViewById(R.id.listNavi);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawer);
        if(manggiohang != null) {

        } else {
            manggiohang = new ArrayList<>();
        }
    }
}
