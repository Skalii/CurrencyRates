package skalii.testjob.db2limited.data.model;


import com.google.gson.annotations.SerializedName;

import skalii.testjob.db2limited.data.type.CurrencyType;


public class CurrencyPBToday implements CurrencyBase {

    public CurrencyPBToday(CurrencyType currencyType,
                           double buy,
                           double sale) {
        this.currencyType = currencyType;
        this.buy = buy;
        this.sale = sale;
    }

    @SerializedName("ccy")
    private CurrencyType currencyType;

    @SerializedName("buy")
    private double buy;

    @SerializedName("sale")
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