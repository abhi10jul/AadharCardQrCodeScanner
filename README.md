# AadharCardQrCodeScanner
#### Check you aadhar card details from aadhar card qr code printed on aadhar card or e-aadhar card

# AadharCardQrCodeScanner

QR Scanning library based on Mobile Vision for android devices API 15 and up

![In action](https://github.com/abhi10jul/AadharCardQrCodeScanner/blob/master/ss.png)

#### adding as a dependency

### on JitPack

##### Step 1. 
Add the JitPack repository to your build file in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
##### Step 2. 
Add the dependency

    dependencies {
	        implementation 'com.github.abhi10jul:AadharCardQrCodeScanner:1.0.0'
	}
  
  
### on Kotlin language to use like

##### Step 3. 

	//set the request code change if you want
	val GETAADHARDATA: Int = 101
	//if you want add any label like above screenshot if you don't then don't pass any bundle value define below
    		var label = "Scan Your QR Code"
		//pass the activity like this
                intent = Intent(YOUR CONTEXT, com.aadharcardqrscan.AcqsActivity::class.java)
                if (label.isNotBlank()) {
                    intent.putExtra("textmsg", "Scan Your QR Code")
                }
                startActivityForResult(intent, GETAADHARDATA)


##### Step 4. 


	//get the result from QR code
	
	  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GETAADHARDATA -> {
                if (resultCode == Activity.RESULT_OK) {
                    val getAllAadharDetails: com.aadharcardqrscan.UidModel = data!!.getParcelableExtra("result")
		    //check before the null value 
                    if (getAllAadharDetails != null) {
                        var getAadharNumber = getAllAadharDetails.AADHARNUMBER
                        var getFullName = getAllAadharDetails.FULLNAME
                        var getGender = getAllAadharDetails.GENDER //M for Male F for Female T for Transgender
                        var getYearBIRTH = getAllAadharDetails.YEAROFBIRTH
                        var getCareOf = getAllAadharDetails.CAREOF
                        var getHouse = getAllAadharDetails.HOUSE
                        var getStreet = getAllAadharDetails.STREET
                        var getLandMark = getAllAadharDetails.LANDMARK
                        var getLocality = getAllAadharDetails.LOCALITY
                        var getVilageTownCity = getAllAadharDetails.VILLAGETOWNCITY
                        var getPostOfficeName = getAllAadharDetails.POSTOFFICE
                        var getDistrict = getAllAadharDetails.DISTRICT
                        var getSubDistrict = getAllAadharDetails.SUBDISTRICT
                        var getState = getAllAadharDetails.STATE
                        var getPinCode = getAllAadharDetails.POSTALCODE
                        var getDateOfBirth = getAllAadharDetails.DATEOFBIRTH
                        var getCountry = getAllAadharDetails.COUNTRY
                    }
                }
            }
        }
    }


### on Java language to use like

##### Step 3. 

	//set the request code change if you want
	final int GETAADHARDATA = 101;
	//if you want add any label like above screenshot if you don't then don't pass any bundle value define below
	String label = "Scan Your Aadhar Card QR code";
        Intent intent = new Intent(this, com.aadharcardqrscan.AcqsActivity.class);
        if (!"".equals(label)) {
            intent.putExtra("textmsg", label);
        }
        startActivityForResult(intent, GETAADHARDATA);


##### Step 4. 


	//get the result from QR code
	
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
                }
                break;
        }

    }
    
    
    
 
 
 Thank You
