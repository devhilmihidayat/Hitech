package phonebase.hilmi.hitech.view.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import phonebase.hilmi.hitech.R;
import phonebase.hilmi.hitech.models.ListDataBarang;
import phonebase.hilmi.hitech.adapter.AdapterBarangRecyler;
import phonebase.hilmi.hitech.config.ConfigVolley;
import phonebase.hilmi.hitech.config.GlobalVars;
import phonebase.hilmi.hitech.models.ListDataBarang;

public class ScreenLihatDataBarcode extends AppCompatActivity {

    final String TAG = "ScreenLihatData";
    private RecyclerView rv;
    private List<ListDataBarang> list_data;
    private AdapterBarangRecyler adapter;

    ImageView ivbarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lihat_data_barcode);

        rv=(RecyclerView)findViewById(R.id.recyler_barang);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new AdapterBarangRecyler(list_data,this);
        adapter.notifyDataSetChanged();

        ivbarcode = (ImageView) findViewById(R.id.ivProduk);


        getData();
    }
    public void getData(){
        String urlLihatData = GlobalVars.IP_LOKAL + "/api/view_barang.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, urlLihatData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++){
                        JSONObject o=array.getJSONObject(i);
                        ListDataBarang list_barang=new ListDataBarang();


                        list_barang.setNm_barang(o.getString("nm_barang"));
                        list_barang.setHrg_barang(o.getString("hrg_barang"));
                        list_barang.setFotoProduk(o.getString("foto_produk"));
                        list_barang.setUrlImage(o.getString("foto_barcode"));
                        list_barang.setCrdDate(o.getString("crtDate"));



//                        ListDataBarang list_barang=new ListDataBarang(o.getString("nm_barang"),o.getString("hrg_barang"), o.getString("foto_produk"),
//                                o.getString("foto_barcode"), o.getString("crtDate"));

                        //decode string Base64 to image
                        String  img = o.getString("foto_barcode");
                        byte [] decodeString64 = Base64.decode(img, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString64, 0, decodeString64.length);
                        bitmap.recycle();
//                        ivbarcode.setImageBitmap(bitmap);
                        list_data.add(list_barang);



                        Log.d(TAG, "onResponse: "  + o);
                        System.out.print("result" + o + list_barang);
                    }
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        ConfigVolley.getInstance().addToRequestQueue(stringRequest, TAG);
    }
    }

