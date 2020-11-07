package skalii.testjob.db2limited.data.model;


import com.google.gson.annotations.SerializedName;

import skalii.testjob.db2limited.data.type.CurrencyType;


public class CurrencyPBDate implements CurrencyBase {

    public CurrencyPBDate(CurrencyType currencyType,
                          double buy,
                          double sale) {
        this.currencyType = currencyType;
        this.buy = buy;
        this.sale = sale;
    }

    @SerializedName("currency")
    private CurrencyType currencyType;

    @SerializedName("purchaseRate")
    private double buy;

    @SerializedName("saleRate")
    private double sale;

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public double getFirstValue() {
        return buy;
    }

    public void setFirstValue(double buy) {
        this.buy = buy;
    }

    public double getSecondValue() {
        return sale;
    }

    public void setSecondValue(double sale) {
        this.sale = sale;
    }

}