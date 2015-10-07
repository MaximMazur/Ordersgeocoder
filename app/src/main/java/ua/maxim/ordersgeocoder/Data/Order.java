package ua.maxim.ordersgeocoder.Data;

public class Order {

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
}
