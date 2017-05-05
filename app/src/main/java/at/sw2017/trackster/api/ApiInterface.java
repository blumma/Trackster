package at.sw2017.trackster;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by mblum on 04.05.2017.
 */

public interface ApiInterface {

    @GET("/api/users")
    Call<List<User>> getUsers();

    @GET("/api/usersFromDb")
    Call<List<User>> getUsersFromDb();

    @POST("/api/login")
    Call<User> login(@Body User user);
}
