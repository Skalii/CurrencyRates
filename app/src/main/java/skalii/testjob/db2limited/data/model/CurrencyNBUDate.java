package skalii.testjob.db2limited.data.model;


import com.google.gson.annotations.SerializedName;

import skalii.testjob.db2limited.data.type.CurrencyType;


public class CurrencyNBUDate implements CurrencyBase {

    @SerializedName("CurrencyCodeL")
    private CurrencyType currencyType;

    @SerializedName("Amount")
    private double amount;

    @SerializedName("Units")
    private int units;

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public double getFirstValue() {
        return amount;
    }

    public void setFirstValue(double amount) {
        this.amount = amount;
    }

    public double getSecondValue() {
        return units;
    }

    public void setSecondValue(double units) {
        this.units = (int) units;
    }

}