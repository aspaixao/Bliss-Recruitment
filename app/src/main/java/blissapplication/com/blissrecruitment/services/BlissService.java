package blissapplication.com.blissrecruitment.services;

import blissapplication.com.blissrecruitment.interfaces.IBlissService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Created by Alexandre Paixao on 3/17/2018.

public class BlissService {

    private final static String URL = "https://private-anon-af70ea24e6-blissrecruitmentapi.apiary-mock.com/";
    private Retrofit retrofit;
    private IBlissService blissService;

    public BlissService(){}

    public Retrofit getRetrofit() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public IBlissService getBlissService() {
        if (blissService == null) {
            blissService = getRetrofit().create(IBlissService.class);
        }
        return blissService;
    }


}
