package at.sw2017.trackster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText editTextEmail = (EditText) findViewById(R.id.edit_text_username);
                EditText editTextPwd = (EditText) findViewById(R.id.edit_text_password);

                User loginCredentials = new User();
                loginCredentials.setEmail(editTextEmail.getText().toString());
                loginCredentials.setPwd(editTextPwd.getText().toString());

                login(loginCredentials);
            }
        });
    }

    private void login(User loginCredentials) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = apiService.login(loginCredentials);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User>call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(getApplication(), "Successful login!", Toast.LENGTH_SHORT).show();
                    // @mblum TODO: got to main menu
                    // @mblum TODO: session management
                } else {
                    Toast.makeText(getApplication(), "Login failed!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<User>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void fetchUsers() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>>call, Response<List<User>> response) {
                List<User> users = response.body();
                Log.d(TAG, "Number of user received: " + users.size());
            }

            @Override
            public void onFailure(Call<List<User>>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}
