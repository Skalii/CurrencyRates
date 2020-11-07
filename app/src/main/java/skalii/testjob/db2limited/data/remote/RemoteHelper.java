package skalii.testjob.db2limited.data.remote;


import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import skalii.testjob.db2limited.BuildConfig;


public abstract class RemoteHelper {

    protected OkHttpClient getClient() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            client.connectTimeout(Duration.ofSeconds(3));
        }

        client.addInterceptor(chain -> {
            Request request = chain.request();
            Response response = null;

            try {
                response = chain.proceed(request);
            } catch (IOException e) {
                if (e.getClass() == SocketTimeoutException.class
                        || e.getClass() == SocketException.class) {
                    throw new IOException("Відсутнє підключення до серверу");
                } else
                    Log.d("SERVER ERROR", "Server: " + (response != null ? response.code() : 0));
                throw new IOException("Помилка при з'єднані");
            }

            return response;
        });

        return client.build();
    }

    /*public static Boolean isConnectedToServer(String URL, Integer timeout) {
        try {
            URLConnection urlConnection = new URL(URL).openConnection();
            urlConnection.setConnectTimeout(timeout);
            urlConnection.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }*/

}