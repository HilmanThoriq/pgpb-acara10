package com.example.retrofit10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.retrofit10.model.Data;
import com.example.retrofit10.network.ApiClient;
import com.example.retrofit10.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the ListView by ID
        ListView listView = findViewById(R.id.tv_nama);

        // Create Service
        ApiService client = ApiClient.getInstance();

        // Call the API to get users
        Call<List<Data>> response = client.getAllUsers();

        // List to hold users details
        List<String> dataUser = new ArrayList<>();

        // Enqueue the call
        response.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (response.isSuccessful() && response.body() != null){
                    for (Data data:response.body()){
                        String labelJenisKelamin = data.getJenisKelamin().equals("l") ? "Laki-laki" : "Perempuan" ;
                        String jenisPekerjaaan = data.getPekerjaan().equals("Pilih Pekerjaan") ? "Pekerjaan Tidak Diketahui" : data.getPekerjaan();
                        String userDetails = "Surveyor: " + data.getNamaSurveyor() + "\n" +
                                "Usia: " + (data.getUsia() != null ? data.getUsia() + " Tahun" : "Tidak Diketahui") + "\n" +
                                "Jenis Kelamin: " + labelJenisKelamin + "\n" +
                                "Pekerjaan: " + jenisPekerjaaan + "\n"  +
                                "ID Desa: " + data.getIdDesa() + "\n" +
                                "Nama Desa: " + data.getNamaDesa() + "\n" +
                                "Latitude: " + data.getLatitude() + "\n" +
                                "Longitude: " + data.getLongitude();
                        dataUser.add(userDetails);
                    }
                    SurveyAdapter adapter = new SurveyAdapter(MainActivity.this, dataUser);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }

    private class SurveyAdapter extends BaseAdapter {

        private final Context context;
        private final List<String> items;

        public SurveyAdapter(Context context, List<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_listsurvey, parent, false);
            }

            TextView textView = view.findViewById(R.id.item_text);
            textView.setText(items.get(position));

            return view;
        }
    }
}