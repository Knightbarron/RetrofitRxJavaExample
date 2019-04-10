package com.example.retrofittry.apiservice;

public class UtilsApi {
    private static final String BASE_URL_API = "https://api.github.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
