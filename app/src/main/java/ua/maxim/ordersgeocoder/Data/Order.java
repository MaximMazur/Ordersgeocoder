package ua.maxim.ordersgeocoder.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable{

    private String uuid;
    private String number;
    private Address departureAddress;
    private Address destinationAddress;
    private String good;
    private float actualWeight;
    private long appointmentFrom;
    private String note1;
    private float initialPrice;
    private String status;

    public Order() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Address getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(Address departureAddress) {
        this.departureAddress = departureAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public float getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(float actualWeight) {
        this.actualWeight = actualWeight;
    }

    public long getAppointmentFrom() {
        return appointmentFrom;
    }

    public void setAppointmentFrom(long appointmentFrom) {
        this.appointmentFrom = appointmentFrom;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public float getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(float initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getUuid());
        dest.writeString(getNumber());
        dest.writeParcelable(getDepartureAddress(), flags);
        dest.writeParcelable(getDestinationAddress(), flags);
        dest.writeString(getGood());
        dest.writeFloat(getActualWeight());
        dest.writeLong(getAppointmentFrom());
        dest.writeString(getNote1());
        dest.writeFloat(getInitialPrice());
        dest.writeString(getStatus());
    }

    public static Parcelable.Creator<Order> CREATOR = new Creator<Order>(){

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    private Order(Parcel source){
        setUuid(source.readString());
        setNumber(source.readString());
        setDepartureAddress((Address)source.readParcelable(Address.class.getClassLoader()));
        setDestinationAddress((Address)source.readParcelable(Address.class.getClassLoader()));
        setGood(source.readString());
        setActualWeight(source.readFloat());
        setAppointmentFrom(source.readLong());
        setNote1(source.readString());
        setInitialPrice(source.readFloat());
        setStatus(source.readString());
    }
}
