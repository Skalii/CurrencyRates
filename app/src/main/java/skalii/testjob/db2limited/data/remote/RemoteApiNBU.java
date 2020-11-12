package skalii.testjob.db2limited.data.remote;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import skalii.testjob.db2limited.data.model.CurrencyNBUDate;
import skalii.testjob.db2limited.data.model.CurrencyNBUDateCC;


public interface RemoteApiNBU {

    @GET("NBU_Exchange/exchange?json")
    Call<List<CurrencyNBUDate>> getExchangeRates(@Query("date") String date);

    @GET("NBUStatService/v1/statdirectory/exchange?json")
    Call<List<CurrencyNBUDateCC>> getExchangeRates(@Query("date") String date, @Query("valcode") String valcode);

}