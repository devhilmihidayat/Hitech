package phonebase.hilmi.hitech.view.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import phonebase.hilmi.hitech.R;

public class ScreenDetilBarcode extends AppCompatActivity {

    final String TAG ="detilActivity";
    ImageView ivdetilimg;
    TextView tvdetilNameproduk;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_detil_barcode);


        Bundle b = new Bundle();
        getIntent().hasExtra("id");
        getIntent().hasExtra("nm_barang");
        b.putString("id", str);

        Toast.makeText(this, "" + b, Toast.LENGTH_SHORT).show();
        System.out.println("result" + b);
        Log.d(TAG, "resul2: " + b);

        tvdetilNameproduk = (TextView)findViewById(R.id.tvdetilproduk);

        tvdetilNameproduk.setText("nm_barang");


    }
}
