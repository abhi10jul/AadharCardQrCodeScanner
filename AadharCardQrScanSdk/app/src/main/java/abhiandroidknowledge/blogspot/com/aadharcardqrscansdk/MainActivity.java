package abhiandroidknowledge.blogspot.com.aadharcardqrscansdk;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aadharcardqrscan.AcqsActivity;
import com.aadharcardqrscan.UidModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, com.aadharcardqrscan.AcqsActivity.class);
        intent.putExtra("textmsg", "Scan Your Aadhar Card QR code");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // UidModel uidModel = null;
                    UidModel uidModel = data.getParcelableExtra("result");
                    Log.e("Abhi Result", uidModel.AADHARNUMBER);
                } else {
                    Log.e("Abhi", "cancel");
                }
                break;
        }

    }
}
