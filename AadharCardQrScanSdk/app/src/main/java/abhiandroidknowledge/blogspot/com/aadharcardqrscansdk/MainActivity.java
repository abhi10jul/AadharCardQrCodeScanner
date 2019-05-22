package abhiandroidknowledge.blogspot.com.aadharcardqrscansdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int GETAADHARDATA = 101;
    private String label = "Custom your label";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.aadharcardqrscan.AcqsActivity.class);
                if (!"".equals(label)) {
                    intent.putExtra("label", label);
                }
                startActivityForResult(intent, GETAADHARDATA);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GETAADHARDATA:
                if (resultCode == RESULT_OK) {
                    com.aadharcardqrscan.UidModel getAllAadharDetails = data.getParcelableExtra("result");
                    String getAadharNumber = getAllAadharDetails.AADHARNUMBER;
                    String getFullName = getAllAadharDetails.FULLNAME;
                    String getGender = getAllAadharDetails.GENDER; //M for Male F for Female T for Transgender
                    String getYearBIRTH = getAllAadharDetails.YEAROFBIRTH;
                    String getCareOf = getAllAadharDetails.CAREOF;
                    String getHouse = getAllAadharDetails.HOUSE;
                    String getStreet = getAllAadharDetails.STREET;
                    String getLandMark = getAllAadharDetails.LANDMARK;
                    String getLocality = getAllAadharDetails.LOCALITY;
                    String getVilageTownCity = getAllAadharDetails.VILLAGETOWNCITY;
                    String getPostOfficeName = getAllAadharDetails.POSTOFFICE;
                    String getDistrict = getAllAadharDetails.DISTRICT;
                    String getSubDistrict = getAllAadharDetails.SUBDISTRICT;
                    String getState = getAllAadharDetails.STATE;
                    String getPinCode = getAllAadharDetails.POSTALCODE;
                    String getDateOfBirth = getAllAadharDetails.DATEOFBIRTH;
                    String getCountry = getAllAadharDetails.COUNTRY;
                    Toast.makeText(this, "Name: " + getFullName
                            + "\n" + "Gender: " + getGender + "\n...check more details", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}