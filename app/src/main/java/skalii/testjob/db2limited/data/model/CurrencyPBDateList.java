package skalii.testjob.db2limited.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CurrencyPBDateList {

    /*private String date;
    private String bank;
    private int baseCurrency;
    private String baseCurrencyLit;*/

    @SerializedName("exchangeRate")
    private List<CurrencyPBDate> list;

    public List<CurrencyPBDate> getList() {
        return list;
    }

    public void setList(List<CurrencyPBDate> list) {
        this.list = list;
    }

}