package skalii.testjob.db2limited.data.model;


import com.google.gson.annotations.SerializedName;

import skalii.testjob.db2limited.data.type.CurrencyType;


public class CurrencyNBUDateCC implements CurrencyBase {

    @SerializedName("cc")
    private CurrencyType currencyType;

    @SerializedName("rate")
    private double rate;

    @SerializedName("exchangedate")
    private String date;

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public double getFirstValue() {
        return rate;
    }

    public void setFirstValue(double amount) {
        this.rate = amount;
    }

    public double getSecondValue() {
        return Double.parseDouble(date.substring(3, 5));
    }

    public void setSecondValue(double units) {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}