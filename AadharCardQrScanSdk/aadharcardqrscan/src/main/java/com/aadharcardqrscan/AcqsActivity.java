package com.aadharcardqrscan;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class AcqsActivity extends AppCompatActivity implements View.OnClickListener, BarcodeRetriever {

    private MyHandler myHandler;
    private View view;
    private final int MY_PERMISSIONS_CAMERA = 126;
    private BarcodeCapture barcodeCapture;
    private FloatingActionButton fBtnFlash, fBtnFocus, ivClose;
    private String changeText = "";
    private final int FLASH = 101;
    private final int FOCUS = 102;
    private final int CLOSE = 103;
    private AlertDialog alert = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHandler = new MyHandler();
        view = LayoutInflater.from(AcqsActivity.this).inflate(R.layout.acqsactivity, null);
        setContentView(view);
    }


    private void init() throws NullPointerException, JSONException {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.changeText = bundle.containsKey("label") ? bundle.getString("label") : "";
        }
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(this.changeText);
        this.barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        this.fBtnFlash = (FloatingActionButton) findViewById(R.id.fbtnFlash);
        this.fBtnFocus = (FloatingActionButton) findViewById(R.id.fbtnFocus);
        this.ivClose = (FloatingActionButton) findViewById(R.id.fbtnClose);
        this.fBtnFlash.setOnClickListener(this);
        this.fBtnFocus.setOnClickListener(this);
        this.barcodeCapture.setRetrieval(this);
        this.ivClose.setOnClickListener(this);
        this.ivClose.setId(CLOSE);
        this.fBtnFlash.setId(FLASH);
        this.fBtnFocus.setId(FOCUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForCameraPermission();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                setBarcodeScanner();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.barcodeCapture == null) {
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        AcqsActivity.this.init();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        myHandler.showDialog(AcqsActivity.this, "Invalid Json Request");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        myHandler.showDialog(AcqsActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            checkForCameraPermission();
            resumeBarcodeScanner();
        }
    }

    private void setBarcodeScanner() {
        if (barcodeCapture != null) {
            barcodeCapture.setShowDrawRect(true)
                    .setSupportMultipleScan(false)
                    .setTouchAsCallback(false)
                    .shouldAutoFocus(true)
                    .setShowFlash(false)
                    .setBarcodeFormat(Barcode.QR_CODE)
                    .setShouldShowText(false);
            resumeBarcodeScanner();
        }
    }

    private void setFlashOnOff() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (barcodeCapture.isShowFlash()) {
                    barcodeCapture.setShowDrawRect(true)
                            .setShowFlash(false);
                    fBtnFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_on_black_24dp));
                } else {
                    barcodeCapture.setShowDrawRect(true)
                            .setShowFlash(true);
                    fBtnFlash.setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_off_black_24dp));
                }
                resumeBarcodeScanner();
            }
        });
    }

    private void setAutoFocusOnOff() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (barcodeCapture.isAutoFocus()) {
                    barcodeCapture.setShowDrawRect(true)
                            .shouldAutoFocus(false);
                    fBtnFocus.setImageDrawable(getResources().getDrawable(R.drawable.ic_filter_center_focus_black_24dp));
                } else {
                    barcodeCapture.setShowDrawRect(true)
                            .shouldAutoFocus(true);
                    fBtnFocus.setImageDrawable(getResources().getDrawable(R.drawable.ic_center_focus_weak_black_24dp));
                }
                resumeBarcodeScanner();
            }
        });
    }

    private void pauseBarcodeScanner() {
        if (barcodeCapture != null) {
            barcodeCapture.onPause();
            barcodeCapture.refresh();
        }
    }

    private void resumeBarcodeScanner() {
        if (barcodeCapture != null) {
            barcodeCapture.onResume();
            barcodeCapture.refresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseBarcodeScanner();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private boolean checkForCameraPermission() {
        final Context context = AcqsActivity.this;
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    try {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("Camera permission is necessary to scan QR code");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                            }
                        });
                        alert = alertBuilder.create();
                        alert.show();
                    } catch (WindowManager.BadTokenException e) {
                        Log.e("WindowManagerBad ", e.toString());
                    }
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                }
                return false;
            } else {
                if (alert != null && alert.isShowing()) {
                    alert.dismiss();
                }
                return true;
            }
        } else {
            if (alert != null && alert.isShowing()) {
                alert.dismiss();
            }
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                        setBarcodeScanner();
                    }
                } else {
                    myHandler.showSnackBar(view, "Please allow the camera permission for scan QR code");
                }
                return;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case FOCUS:
                setAutoFocusOnOff();
                break;

            case FLASH:
                setFlashOnOff();
                break;

            case CLOSE:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pauseBarcodeScanner();
                Log.d("Barcode read: ", barcode.displayValue + " raw value " + barcode.rawValue);
                String rawValue = barcode.rawValue;
                try {
                    JSONObject object = XML.toJSONObject(rawValue);
                    if (object.has("PrintLetterBarcodeData")) {
                        JSONObject printLetterBarcodeData = object.getJSONObject("PrintLetterBarcodeData");
                        if (printLetterBarcodeData.has("uid")) {
                            pauseBarcodeScanner();
                            String AadharNumber = "", FullName = "", Gender = "",
                                    yearOfBirth = "", careOf = "", house = "", street = "",
                                    landmark = "", locality = "", villageTownCity = "",
                                    postOfficeName = "", district = "", subdistrict = "",
                                    state = "", pinCode = "", dateOfBirth = "", country = "";
                            if (printLetterBarcodeData.has("uid")) {
                                AadharNumber = printLetterBarcodeData.getString("uid");
                            }
                            if (printLetterBarcodeData.has("gender")) {
                                Gender = printLetterBarcodeData.getString("gender");
                            }
                            if (printLetterBarcodeData.has("yob")) {
                                yearOfBirth = printLetterBarcodeData.getString("yob");
                            }
                            if (printLetterBarcodeData.has("co")) {
                                careOf = printLetterBarcodeData.getString("co");
                            }
                            if (printLetterBarcodeData.has("name")) {
                                FullName = printLetterBarcodeData.getString("name");
                            }
                            if (printLetterBarcodeData.has("house")) {
                                house = printLetterBarcodeData.getString("house");
                            }
                            if (printLetterBarcodeData.has("street")) {
                                street = printLetterBarcodeData.getString("street");
                            }
                            if (printLetterBarcodeData.has("lm")) {
                                landmark = printLetterBarcodeData.getString("lm");
                            }
                            if (printLetterBarcodeData.has("loc")) {
                                locality = printLetterBarcodeData.getString("loc");
                            }
                            if (printLetterBarcodeData.has("vtc")) {
                                villageTownCity = printLetterBarcodeData.getString("vtc");
                            }
                            if (printLetterBarcodeData.has("po")) {
                                postOfficeName = printLetterBarcodeData.getString("po");
                            }
                            if (printLetterBarcodeData.has("dist")) {
                                district = printLetterBarcodeData.getString("dist");
                            }
                            if (printLetterBarcodeData.has("subdist")) {
                                subdistrict = printLetterBarcodeData.getString("subdist");
                            }
                            if (printLetterBarcodeData.has("state")) {
                                state = printLetterBarcodeData.getString("state");
                            }
                            if (printLetterBarcodeData.has("pc")) {
                                pinCode = printLetterBarcodeData.getString("pc");
                            }
                            if (printLetterBarcodeData.has("dob")) {
                                dateOfBirth = printLetterBarcodeData.getString("dob");
                            }
                            if (printLetterBarcodeData.has("country")) {
                                country = printLetterBarcodeData.getString("country");
                            }
                            AadharNumber = AadharNumber.equals("") ? "" : AadharNumber;
                            FullName = FullName.equals("") ? "" : FullName;
                            dateOfBirth = dateOfBirth.equals("") ? "" : dateOfBirth;
                            yearOfBirth = yearOfBirth.equals("") ? "" : yearOfBirth;
                            locality = locality.equals("") ? "" : locality;
                            Gender = Gender.equals("") ? "" : Gender;
                            careOf = careOf.equals("") ? "" : careOf;
                            house = house.equals("") ? "" : house;
                            street = street.equals("") ? "" : street;
                            landmark = landmark.equals("") ? "" : landmark;
                            villageTownCity = villageTownCity.equals("") ? "" : villageTownCity;
                            postOfficeName = postOfficeName.equals("") ? "" : postOfficeName;
                            subdistrict = subdistrict.equals("") ? "" : subdistrict;
                            district = district.equals("") ? "" : district;
                            state = state.equals("") ? "" : state;
                            country = country.equals("") ? "" : country;
                            pinCode = pinCode.equals("") ? "" : pinCode;
                            UidModel uidModel = new UidModel(AadharNumber, FullName, Gender,
                                    yearOfBirth, careOf, house, street, landmark, locality,
                                    villageTownCity, postOfficeName, district, subdistrict,
                                    state, pinCode, dateOfBirth, country);
                            Intent intent = new Intent();
                            intent.putExtra("result", uidModel);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            resumeBarcodeScanner();
                            myHandler.showSnackBar(view, "Invalid QR format");
                        }
                    } else {
                        resumeBarcodeScanner();
                        myHandler.showSnackBar(view, "Invalid QR format");
                    }
                } catch (JSONException e) {
                    resumeBarcodeScanner();
                    e.printStackTrace();
                    myHandler.showSnackBar(view, "Invalid QR format");
                }
            }
        });

    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            Barcode barcode = sparseArray.valueAt(i);
            Log.d("value", barcode.displayValue);
        }
    }

    @Override
    public void onRetrievedFailed(String reason) {
        myHandler.showSnackBar(view, "Error occurred while scanning " + reason);
    }

    @Override
    public void onPermissionRequestDenied() {
        finish();
    }
}
