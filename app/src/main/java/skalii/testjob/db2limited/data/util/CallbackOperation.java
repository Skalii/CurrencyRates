package skalii.testjob.db2limited.data.util;


import org.jetbrains.annotations.NotNull;

import retrofit2.Callback;
import retrofit2.Response;


public class CallbackOperation<Currency> implements Callback<Currency> {

    private final ExpressionData<Currency> ex;

    public CallbackOperation(ExpressionData<Currency> ex) {
        this.ex = ex;
    }

    @Override
    public void onResponse(@NotNull retrofit2.Call<Currency> call, @NotNull Response<Currency> response) {
        ex.setData(response.body());
    }

    @Override
    public void onFailure(@NotNull retrofit2.Call<Currency> call, @NotNull Throwable t) {
        t.printStackTrace();
    }

}