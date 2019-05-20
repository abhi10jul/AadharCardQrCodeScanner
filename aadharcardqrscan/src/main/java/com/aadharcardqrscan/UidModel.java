package com.aadharcardqrscan;

import android.os.Parcel;
import android.os.Parcelable;

public class UidModel implements Parcelable {
    public String AADHARNUMBER;
    public String FULLNAME;
    public String GENDER;
    public String YEAROFBIRTH;
    public String CAREOF;
    public String HOUSE;
    public String STREET;
    public String LANDMARK;
    public String LOCALITY;
    public String VILLAGETOWNCITY;
    public String POSTOFFICE;
    public String DISTRICT;
    public String SUBDISTRICT;
    public String STATE;
    public String POSTALCODE;
    public String DATEOFBIRTH;
    public String COUNTRY;

    public final static Parcelable.Creator<UidModel> CREATOR = new Creator<UidModel>() {
        @SuppressWarnings({"unchecked"})
        public UidModel createFromParcel(Parcel in) {
            return new UidModel(in);
        }

        public UidModel[] newArray(int size) {
            return (new UidModel[size]);
        }

    };

    protected UidModel(Parcel in) {
        this.AADHARNUMBER = ((String) in.readValue((String.class.getClassLoader())));
        this.FULLNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.GENDER = ((String) in.readValue((String.class.getClassLoader())));
        this.YEAROFBIRTH = ((String) in.readValue((String.class.getClassLoader())));
        this.CAREOF = ((String) in.readValue((String.class.getClassLoader())));
        this.HOUSE = ((String) in.readValue((String.class.getClassLoader())));
        this.STREET = ((String) in.readValue((String.class.getClassLoader())));
        this.LANDMARK = ((String) in.readValue((String.class.getClassLoader())));
        this.LOCALITY = ((String) in.readValue((String.class.getClassLoader())));
        this.VILLAGETOWNCITY = ((String) in.readValue((String.class.getClassLoader())));
        this.POSTOFFICE = ((String) in.readValue((String.class.getClassLoader())));
        this.DISTRICT = ((String) in.readValue((String.class.getClassLoader())));
        this.SUBDISTRICT = ((String) in.readValue((String.class.getClassLoader())));
        this.STATE = ((String) in.readValue((String.class.getClassLoader())));
        this.POSTALCODE = ((String) in.readValue((String.class.getClassLoader())));
        this.DATEOFBIRTH = ((String) in.readValue((String.class.getClassLoader())));
        this.COUNTRY = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UidModel() {
    }

    public UidModel(String AADHARNUMBER, String FULLNAME, String GENDER, String YEAROFBIRTH, String CAREOF, String HOUSE, String STREET, String LANDMARK, String LOCALITY, String VILLAGETOWNCITY, String POSTOFFICE, String DISTRICT, String SUBDISTRICT, String STATE, String POSTALCODE, String DATEOFBIRTH, String COUNTRY) {
        super();
        this.AADHARNUMBER = AADHARNUMBER;
        this.FULLNAME = FULLNAME;
        this.GENDER = GENDER;
        this.YEAROFBIRTH = YEAROFBIRTH;
        this.CAREOF = CAREOF;
        this.HOUSE = HOUSE;
        this.STREET = STREET;
        this.LANDMARK = LANDMARK;
        this.LOCALITY = LOCALITY;
        this.VILLAGETOWNCITY = VILLAGETOWNCITY;
        this.POSTOFFICE = POSTOFFICE;
        this.DISTRICT = DISTRICT;
        this.SUBDISTRICT = SUBDISTRICT;
        this.STATE = STATE;
        this.POSTALCODE = POSTALCODE;
        this.DATEOFBIRTH = DATEOFBIRTH;
        this.COUNTRY = COUNTRY;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(AADHARNUMBER);
        dest.writeValue(FULLNAME);
        dest.writeValue(GENDER);
        dest.writeValue(YEAROFBIRTH);
        dest.writeValue(CAREOF);
        dest.writeValue(HOUSE);
        dest.writeValue(STREET);
        dest.writeValue(LANDMARK);
        dest.writeValue(LOCALITY);
        dest.writeValue(VILLAGETOWNCITY);
        dest.writeValue(POSTOFFICE);
        dest.writeValue(DISTRICT);
        dest.writeValue(SUBDISTRICT);
        dest.writeValue(STATE);
        dest.writeValue(POSTALCODE);
        dest.writeValue(DATEOFBIRTH);
        dest.writeValue(COUNTRY);
    }

    public int describeContents() {
        return 0;
    }
}
