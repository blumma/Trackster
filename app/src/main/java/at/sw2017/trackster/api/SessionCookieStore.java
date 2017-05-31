package at.sw2017.trackster.api;

/**
 * Created by mblum on 14.05.2017.
 */

public class SessionCookieStore {

    private static SessionCookieStore store = null;
    private String sessionCookie = null;

    private SessionCookieStore() {

    }

    public static SessionCookieStore getStore() {
        if (store == null) {
            store = new SessionCookieStore();
        }
        return store;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public void parseSessionCookie(String cookies) {
        // @mblum TODO: support for multiple cookies. Split string by ';'
        // for now we only have one cookie...
        this.sessionCookie = cookies;
    }

    public void clear() {
        this.sessionCookie = null;
    }
}
