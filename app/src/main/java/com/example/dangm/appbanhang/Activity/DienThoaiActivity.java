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
import com.example.dangm.appbanhang.Adapter.Dienthoaiadapter;
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

public class DienThoaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lv;
    int iddt = 0;
    int page = 1;
    Dienthoaiadapter dienthoaiadapter;
    View footerView;
    mHandler handler = new mHandler();
    boolean isLoading = false;
    boolean limitdata = false;
    ArrayList<SanPham> dienthoailist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        init();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdMobile();
            getData(page);
            actionBar();
            loadMoreData();
        } else {
            Toast.makeText(this, "Vui lòng kiểm tra kết nối", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMoreData() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DienThoaiActivity.this, ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", dienthoailist.get(position));
                startActivity(intent);
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitdata == false) {
                    isLoading = true;
                    ThreadData thread = new ThreadData();
                    thread.start();
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.giohang:
                Intent intent = new Intent(DienThoaiActivity.this, GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(final int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.dienthoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        int idLoai = 0;
                        String tendienthoai = "";
                        int giadienthoai = 0;
                        String hinhdienthoai = "";
                        String motadienthoai = "";

                        if(response != null && response.length() != 2) {
                            lv.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                                    id = jsonObject.optInt("idSP");
                                    idLoai = jsonObject.optInt("idLoai");
                                    tendienthoai = jsonObject.optString("tenSP");
                                    giadienthoai = jsonObject.optInt("giaSP");
                                    hinhdienthoai = jsonObject.optString("hinhSP");
                                    motadienthoai = jsonObject.optString("motaSP");

                                    dienthoailist.add(new SanPham(id, idLoai, tendienthoai, giadienthoai, motadienthoai, hinhdienthoai));
                                    dienthoaiadapter = new Dienthoaiadapter(getApplicationContext(), dienthoailist);
                                    lv.setAdapter(dienthoaiadapter);
                                    dienthoaiadapter.notifyDataSetChanged();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitdata = true;
                            lv.removeFooterView(footerView);
                            Toast.makeText(DienThoaiActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
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
                HashMap<String, String> params = new HashMap<>();
                params.put("idLoai", String.valueOf(iddt));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getIdMobile() {
        iddt = getIntent().getIntExtra("idLoai", -1);
        Log.d("iddt", iddt + "");
    }

    private void init() {
        lv = (ListView) findViewById(R.id.listdienthoai);
        toolbar = (Toolbar) findViewById(R.id.toolbardienthoai);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lv.addFooterView(footerView);
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
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = handler.obtainMessage(1);
            handler.sendMessage(message);
            super.run();
        }
    }

}
