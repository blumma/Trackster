package at.sw2017.trackster.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mblum on 14.05.2017.
 */

public class SessionCookieRequestInterceptor implements Interceptor  {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        String sessionCookie = SessionCookieStore.getStore().getSessionCookie();
        if (sessionCookie != null) {
            builder.addHeader("Cookie", sessionCookie);
        }

        return chain.proceed(builder.build());
    }
}
