package com.example.zaki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.zaki.adapter.MakananAdapter;
import com.example.zaki.model.MakananModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuMakanan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MakananAdapter adapter;
    private ArrayList<MakananModel> makananModelArrayList = new ArrayList<>();


    private static final String TAG = MenuMakanan.class.getSimpleName();
    private static String URL_SELECT = "http://10.0.2.2:8081/penjualan/bacaMakanan.php";
    private static final String TAG_ID = "id";
    private static final String TAG_Nama_Makanan = "nama_makanan";
    private static final String TAG_Harga_Makanan = "harga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan);

        recyclerView = findViewById(R.id.recyclerMakanan);


        adapter = new MakananAdapter(android.R.layout.simple_spinner_item, makananModelArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MenuMakanan.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        bacaData();

    }

    private void bacaData() {
        makananModelArrayList.clear();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i =0; i < response.length(); i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        MakananModel item = new MakananModel();

                        item.setId(jsonObject.getString(TAG_ID));
                        item.setNama_makanan(jsonObject.getString(TAG_Nama_Makanan));
                        item.setHarga(jsonObject.getString(TAG_Harga_Makanan));

                        makananModelArrayList.add(item);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuMakanan.this, "GAGAL Menampilkan DAta", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}