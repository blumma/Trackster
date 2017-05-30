package at.sw2017.trackster.api;

import java.util.List;

import at.sw2017.trackster.models.Student;
import at.sw2017.trackster.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mblum on 04.05.2017.
 */

public interface ApiInterface {

    @GET("api/users")
    Call<List<User>> getUsers();

    @GET("api/usersFromDb")
    Call<List<User>> getUsersFromDb();

    @POST("api/login")
    Call<User> login(@Body User user);

    @POST("api/logout")
    Call<ResponseBody> logout();

    @GET("api/students")
    Call<List<Student>> getStudents();

    @GET("api/studentsClasses")
    Call<List<Student>> getClasses();

    @GET("api/student/{studentId}")
    Call<Student> getStudentById(@Path("studentId") int studentId);

    @GET("api/students/{class}")
    Call<List<Student>> getStudentsByClass(@Path("class") String clas);

    @POST("api/student/{studentId}")
    Call<ResponseBody> updateStudentById(@Path("studentId") int studentId, @Body Student student);
}
