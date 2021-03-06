package at.sw2017.trackster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.api.SessionCookieStore;
import at.sw2017.trackster.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        SessionCookieStore.getStore().clear();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.login(loginCredentials);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User>call, Response<User> response) {
                if(response.isSuccessful()) {
                    String cookies = response.headers().get("Set-Cookie");
                    SessionCookieStore.getStore().parseSessionCookie(cookies);

                    User user = response.body();
                    Toast.makeText(getApplication(), "Successful login!", Toast.LENGTH_SHORT).show();

                    Intent k = new Intent(LoginActivity.this, MenuActivity.class);
                    Gson gson = new Gson();
                    k.putExtra("User", gson.toJson(user));
                    startActivity(k);
                } else {
                    Toast.makeText(getApplication(), R.string.error_login, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<User>call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplication(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
