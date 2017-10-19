package com.example.dangm.appbanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangm.appbanhang.Adapter.LaptopAdapter;
import com.example.dangm.appbanhang.Model.SanPham;
import com.example.dangm.appbanhang.R;
import com.example.dangm.appbanhang.Util.CheckConnection;
import com.example.dangm.appbanhang.Util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lv;
    ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();
    LaptopAdapter adapter;
    int page = 1;
    int idlaptop = 0;
    View footerview;
    mHandler handler = new mHandler();
    boolean isLoading = false;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        init();
        actionbar();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdLoai();
            getData(page);
            loadmoredata();
        } else {
            Toast.makeText(this, "Vui lòng kiểm tra kết nối", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadmoredata() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LaptopActivity.this, ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", sanPhamArrayList.get(position));
                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount &&  totalItemCount!= 0 && isLoading == false == limitdata == false) {
                    isLoading = true;
                    ThreadData thread = new ThreadData();
                    thread.start();
                }
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
                Intent intent = new Intent(LaptopActivity.this, GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.laptoptool);
        lv = (ListView) findViewById(R.id.laptopList);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
    }
    private void getIdLoai() {
        idlaptop = getIntent().getIntExtra("idLoai", -1);
        Log.d("idLaptop", idlaptop + "");
    }

    private void actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.dienthoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        int idloai = 0;
                        String tenlaptop = "";
                        int gia = 0;
                        String mota = "";
                        String hinhlaptop = "";

                        if (response != null && response.length() != 2) {
                            lv.removeFooterView(footerview);
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.optJSONObject(i);

                                    id = jsonObject.optInt("idSP");
                                    idloai = jsonObject.optInt("idLoai");
                                    tenlaptop = jsonObject.optString("tenSP");
                                    gia = jsonObject.optInt("giaSP");
                                    mota = jsonObject.optString("hinhSP");
                                    hinhlaptop = jsonObject.optString("motaSP");

                                    sanPhamArrayList.add(new SanPham(id, idloai, tenlaptop, gia, hinhlaptop, mota));
                                }

                                adapter = new LaptopAdapter(getApplicationContext(), sanPhamArrayList);
                                lv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            limitdata = true;
                            lv.removeFooterView(footerview);
                            Toast.makeText(LaptopActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast(getApplicationContext(), "Lỗi");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idLoai", String.valueOf(idlaptop));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lv.addFooterView(footerview);
                    break;

                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            super.run();
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage(1);
            handler.sendMessage(message);
        }
    }
}
