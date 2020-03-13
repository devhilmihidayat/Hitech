package phonebase.hilmi.hitech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import phonebase.hilmi.hitech.view.barcode.ScreenBarcode;
import phonebase.hilmi.hitech.view.barcode.ScreenLihatDataBarcode;

public class Index extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG ="Index";
    private Button btnTambahData, btnLihatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnTambahData = (Button) findViewById(R.id.btnInputData);
        btnLihatData = (Button) findViewById(R.id.btnLihatData);

        btnTambahData.setOnClickListener(this);
        btnLihatData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnInputData:
                Intent i = new Intent(this, ScreenBarcode.class);
                startActivity(i);
                break;
            case R.id.btnLihatData:
                Intent i2 = new Intent(this, ScreenLihatDataBarcode.class);
                startActivity(i2);

        }

    }

    //optionsMenu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_1:
                Intent iMenu1 = new Intent(this, MenuAbout.class);
                startActivity(iMenu1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
