package com.example.dangm.appbanhang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangm.appbanhang.R;
import com.example.dangm.appbanhang.Util.CheckConnection;
import com.example.dangm.appbanhang.Util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtSDT;
    private Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        init();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            eventButton();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối !!!");
        }
    }

    private void eventButton() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String name = edtName.getText().toString().trim();
                    final String sdt = edtSDT.getText().toString().trim();
                    final String email = edtEmail.getText().toString().trim();

                    if(name.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
                        Toast.makeText(ThongTinActivity.this, "Vui lòng nhập liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.donhang,

                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String madonhang) {
                                        Log.d("Madonhang", madonhang.toString());
                                        if(Integer.parseInt(madonhang) > 0) {
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                            StringRequest request = new StringRequest(Request.Method.POST, Server.chitiet,

                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            if(response.equals("success")) {
                                                                MainActivity.manggiohang.clear();
                                                                Toast.makeText(ThongTinActivity.this, "Thanh toán thành công !!!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(intent);
                                                            } else {
                                                                Toast.makeText(ThongTinActivity.this, "Thanh toán thất bại !!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    },

                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    }
                                            ){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    JSONArray jsonArray = new JSONArray();
                                                    for(int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                                        JSONObject jsonObject = new JSONObject();
                                                        try {
                                                            jsonObject.put("madonhang", madonhang);
                                                            jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getId());
                                                            jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                                            jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiapsp());
                                                            jsonObject.put("soluong", MainActivity.manggiohang.get(i).getSoluong());
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        jsonArray.put(jsonObject);
                                                    }
                                                    HashMap<String, String> array = new HashMap<String, String>();
                                                    array.put("json", jsonArray.toString());
                                                    return array;
                                                }
                                            };
                                            requestQueue.add(request);
                                        }
                                    }

                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error", error.toString());
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("tenkhachhang", name);
                                params.put("email", email);
                                params.put("sodienthoai", sdt);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
            }
        });
    }

    private void init() {
        edtName = (EditText) findViewById(R.id.edtTen);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSDT = (EditText) findViewById(R.id.edtsoDT);
        btnSubmit = (Button) findViewById(R.id.btnXacNhan);
        btnCancel = (Button) findViewById(R.id.btnHuy);
    }
}
