package ua.maxim.ordersgeocoder.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable{

    private String country;
    private String zipCode;
    private String city;
    private String countryCode;
    private String street;
    private String houseNumber;

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
    }
}
