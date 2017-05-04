package at.sw2017.trackster;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getUsersTest() throws Exception {

        MockWebServer server = new MockWebServer();

        String message = "[{\"id\":1,\"firstName\":\"Mathias\",\"lastName\":\"Blum\",\"email\":\"mathias.blum@student.tugraz.at\",\"createdAt\":\"2017-05-01\"},{\"id\":2,\"firstName\":\"Max\",\"lastName\":\"Mustermann\",\"email\":\"max.mustermann@student.tugaz.at\",\"createdAt\":\"2017-05-03\"},{\"id\":3,\"firstName\":\"Ludwig\",\"lastName\":\"van Beethoven\",\"email\":\"ludwig.van.beethoven@mail.at\",\"createdAt\":\"2017-05-02\"}]";

        // Schedule some responses.
        server.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(message));

        // Start the server.
        server.start();

        HttpUrl baseUrl = server.url("/tracksterMockServer/");

        ApiInterface apiService = ApiClient.getClient(baseUrl).create(ApiInterface.class);

        Call<List<User>> call = apiService.getUsers();
        call.execute();

        // Optional: confirm that your app made the HTTP requests you were expecting.
        RecordedRequest request = server.takeRequest();
        assertEquals("/api/users", request.getPath());

        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }
}