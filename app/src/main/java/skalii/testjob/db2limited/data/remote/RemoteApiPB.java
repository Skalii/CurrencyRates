package skalii.testjob.db2limited.data.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import skalii.testjob.db2limited.data.model.CurrencyPBToday;
import skalii.testjob.db2limited.data.model.CurrencyPBDateList;


public interface RemoteApiPB {

    @GET("pubinfo?json&exchange&coursid=5")
    Call<List<CurrencyPBToday>> getExchangeRates();

    @GET("exchange_rates?json")
    Call<CurrencyPBDateList> getExchangeRates(@Query("date") String date);

}