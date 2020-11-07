package skalii.testjob.db2limited.data.remote;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RemoteServicePB extends RemoteHelper {

    public static RemoteServicePB instance;
    private final Retrofit retrofit;
    private static final String URL = "https://api.privatbank.ua/p24api/";


    public static RemoteServicePB getInstance() {
        if (instance == null) {
            instance = new RemoteServicePB();
        }
        return instance;
    }

    private RemoteServicePB() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    public RemoteApiPB getApi() {
        return retrofit.create(RemoteApiPB.class);
    }

}