package com.vankien96.mooview.data.service.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vankien96.mooview.BuildConfig;
import com.vankien96.mooview.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Init Retrofit and created API
 */

public final class ServiceGenerators  {

    private static final long TIMEOUT_CONNECTION = TimeUnit.MINUTES.toMillis(1);

    private ServiceGenerators() {
        //No-op
    }

    public static MoviesApi createApiService(@NonNull Context context) {
        Retrofit retrofit  = createRetrofit(context);
        return retrofit.create(MoviesApi.class);
    }

    private static Retrofit createRetrofit(@NonNull Context context) {
        Gson gson =
                new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        int cacheSize = 10 * 1024 * 1024; //10MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        httpClientBuilder.cache(cache);
        httpClientBuilder.readTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            httpClientBuilder.addInterceptor(logging);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient okHttpClient = httpClientBuilder.build();

        return new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
