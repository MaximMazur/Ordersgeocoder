package ua.maxim.ordersgeocoder.Data;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Address{

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

    public boolean isCoordinatesSet() {
        return (getLatitude() != 0 && getLongitude() != 0);
    }

    public LatLng getCoordinates() {
        return new LatLng(getLatitude(), getLongitude());
    }
}
