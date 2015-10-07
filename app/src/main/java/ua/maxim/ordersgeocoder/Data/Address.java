package ua.maxim.ordersgeocoder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Address implements Parcelable{

    private String country;
    private String zipCode;
    private String city;
    private String countryCode;
    private String street;
    private String houseNumber;
    private double latitude;
    private double longitude;

    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //It assumed, that fields: "City", "CountryCode" and "ZipCode" are always filled
    // unlike "HouseNumber" and "Street"
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        if (!getHouseNumber().isEmpty()) {
            builder.append(getHouseNumber());
        }

        if (!getStreet().isEmpty()){
            if(builder.length() != 0) builder.append(" ");

            builder.append(getStreet());
        }

        if (builder.length() != 0) builder.append(", ");

        builder.append(getCity()).append(", ")
                 /*Country code consists of 3 characters instead of two.
                 geocoder class not define in some cases coordinates for such situation
                .append(getCountryCode()).append(" ")*/
                .append(getZipCode());


        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getCountry());
        dest.writeString(getZipCode());
        dest.writeString(getCity());
        dest.writeString(getCountryCode());
        dest.writeString(getStreet());
        dest.writeString(getHouseNumber());
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
    }

    public static Parcelable.Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    private Address(Parcel source){
        setCountry(source.readString());
        setZipCode(source.readString());
        setCity(source.readString());
        setCountryCode(source.readString());
        setStreet(source.readString());
        setHouseNumber(source.readString());
        setLatitude(source.readDouble());
        setLongitude(source.readDouble());
    }

    public boolean isCoordinatesSet() {
        return (getLatitude() != 0 && getLongitude() != 0);
    }

    public LatLng getCoordinates() {
        return new LatLng(getLatitude(), getLongitude());
    }
}
