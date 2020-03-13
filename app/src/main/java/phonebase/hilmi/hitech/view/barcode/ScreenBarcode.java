package phonebase.hilmi.hitech.view.barcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import phonebase.hilmi.hitech.R;
import phonebase.hilmi.hitech.config.ConfigVolley;
import phonebase.hilmi.hitech.config.GlobalVars;

public class ScreenBarcode extends AppCompatActivity {

    final String TAG ="barode";
    TextView tv1;
    ImageView ivbarcode;
    Button btnGenerate, btnsaved, btnSimpan;

    Bitmap bitmap;

    //declared untuk membuat 2 atau lebih encoded barcode
    @BindView(R.id.ivBarcode)
    ImageView bindImageBarcode;
    @BindView(R.id.edtName)
    TextInputEditText bindName;
    @BindView(R.id.edtPrice)
    TextInputEditText bindPrice;


    //save png QrCode
    int counter = 0;
    String path = Environment.getExternalStorageDirectory().toString();
    String timeStamp = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss" ).format(new Date());
    File savedFilePng = new File(path, timeStamp+counter+".png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
    OutputStream fOut = null;

    ProgressDialog pd;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_barcode);

        tv1 = (TextView)findViewById(R.id.tvshowcode);
        bindPrice = (TextInputEditText) findViewById(R.id.edtPrice);
        ivbarcode = (ImageView) findViewById(R.id.ivBarcode);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        btnsaved = (Button) findViewById(R.id.btsaved);

        pd = new ProgressDialog(this);


        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btgenerate();
            }
        });
        btnsaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedGenerated();
            }
        });
        bindPrice.addTextChangedListener(new TextWatcher() {
            String current = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)){
                    bindPrice.removeTextChangedListener(this);

                    Locale local = new Locale("id", "id");
                    String replaceable = String.format("[Rp,.\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                        try {
                                parsed = Double.parseDouble(cleanString);
                                String formated = NumberFormat.getCurrencyInstance().format((parsed/100));
                            } catch (NumberFormatException e) {

                            parsed = 0.00;
                        }

                    NumberFormat formatter = NumberFormat
                            .getCurrencyInstance(local);
                    formatter.setMaximumFractionDigits(0);
                    formatter.setParseIntegerOnly(true);
                    String formatted = formatter.format((parsed));

                    String replace = String.format("[Rp\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String clean = formatted.replaceAll(replace, "");


                    current = formatted;
                    bindPrice.setText(clean);
                    bindPrice.setSelection(clean.length());
                    bindPrice.addTextChangedListener(this);
                }

            }
        });
        ButterKnife.bind(this);
    }


    //generate
    public void btgenerate(){
        final String strName = bindName.getText().toString();
        final String strPrice = bindPrice.getText().toString();

        StringBuilder textSendToBarcode = new StringBuilder();
        textSendToBarcode.append(strName+" | "+strPrice);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        if (strName.isEmpty() && strPrice.isEmpty()){
            bindPrice.setError("Tidak Boleh Kosong");
            return;
        }

        //random code
        Random r = new Random();
        int rando = (int) (Math.random() * 10);
        Integer randomNumber = r.nextInt(100 * 100);
        tv1.setText(String.valueOf(randomNumber));

        try {
            //multiple from 5 to generated

            Bitmap yourLogo = BitmapFactory.decodeResource(getResources(), R.drawable.icn_logo);
            BitMatrix bm = multiFormatWriter.encode(textSendToBarcode.toString(), BarcodeFormat.QR_CODE, 600, 600);
           final BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bm);


//            int convertPrice = Integer.parseInt(strPrice);
            //set
            //akhir
//            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
//            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//
//            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//
//            //Code128Write generate barcode
//            //codeWriter = new Code128Writer();
//            codeWriter = new QRCodeWriter();
//
////            BitMatrix byteMatrix =  codeWriter.encode(strName,strPrice, BarcodeFormat.CODE_128,400, 200, hintMap);
//            BitMatrix byteMatrix =  multiFormatWriter.encode(strName, BarcodeFormat.QR_CODE,400, 200, hintMap);
//
//            int width = byteMatrix.getWidth();
//            int height = byteMatrix.getHeight();
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap2 = barcodeEncoder.createBitmap(byteMatrix);
//
//
//            for (int i =0; i < width; i++){
//                for (int j = 0; j < height; j++){
//                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE
//                    );
//                }
//            }

            ivbarcode.setImageBitmap(bitmap);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void savedGenerated() {
            ivbarcode.setImageBitmap(bitmap);
            try {
                //saved Tofile
                int counter = 0;
                final String path = Environment.getExternalStorageDirectory().toString();
                final String timeStamp = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss" ).format(new Date());
                File savedFilePng = new File(path, timeStamp+counter+".png"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                OutputStream fOut = null;
                fOut = new FileOutputStream(savedFilePng);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // bmp is your Bitmap instance
                fOut.flush();
                fOut.close();


                //encodeToImage
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] imgbytes=byteArrayOutputStream.toByteArray();
                final String encodeimg= Base64.encodeToString(imgbytes,Base64.DEFAULT);

                //decoded
                imgbytes = Base64.decode(encodeimg, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(imgbytes, 0, imgbytes.length);
                ivbarcode.setImageBitmap(bitmap);


                Toast.makeText(this, "Gambar Berhasil Disimpan", Toast.LENGTH_LONG).show();
                Log.d(TAG, "btgenerate: " + bitmap + timeStamp + fOut);



                //save data editText
                String url_simpan = GlobalVars.IP_LOKAL + "/api/insert_barang.php";
                String tag_json = "tag_json";
                pd.setCancelable(false);
                pd.setMessage("Menyimpan...");
                showDialog();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_simpan, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        hideDialog();
                        try {
                            JSONObject jObject = new JSONObject(response);
                            String pesan = jObject.getString("pesan");
                            String hasil = jObject.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScreenBarcode.this, "Error JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("ERROR", error.getMessage());
                        Toast.makeText(ScreenBarcode.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();
                        String strName = bindName.getText().toString().trim();
                        String strPrice = bindPrice.getText().toString().trim();
                        String imgProduk = bindName.getText().toString().trim();
                        String strBarcode = encodeimg.toString().trim();

                        //post objek
                        param.put("nm_barang", strName);
                        param.put("hrg_barang", strPrice);
                        param.put("foto_produk", imgProduk);
                        param.put("foto_barcode", strBarcode);
                        param.put("crtDate", timeStamp);


                        Log.d(TAG, "getParams: " + strBarcode + encodeimg);
                        return param;
                    }
                };
                ConfigVolley.getInstance().addToRequestQueue(stringRequest, tag_json);
                //akhir
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(Toast.makeText(this, "gagal", Toast.LENGTH_LONG));
            }
    }

    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

}

