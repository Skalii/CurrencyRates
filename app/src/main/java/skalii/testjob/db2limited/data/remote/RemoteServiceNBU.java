package skalii.testjob.db2limited.data.remote;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RemoteServiceNBU extends RemoteHelper {

    private static RemoteServiceNBU instance;
    private final Retrofit retrofit;
    private static final String URL = "https://bank.gov.ua/";


    public static RemoteServiceNBU getInstance() {
        if (instance == null) {
            instance = new RemoteServiceNBU();
        }
        return instance;
    }

    private RemoteServiceNBU() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    public RemoteApiNBU getApi() {
        return retrofit.create(RemoteApiNBU.class);
    }

}