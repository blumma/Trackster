package at.sw2017.trackster.api;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mblum on 04.05.2017.
 */

public class ApiClient {

    // IMPORTANT: Retrofit need '/' at the end of the url!
    public static String BASE_URL = "http://trackster.mablum.at/public/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
