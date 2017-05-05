package at.sw2017.trackster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = apiService.login(loginCredentials);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User>call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(getApplication(), "Successful login!", Toast.LENGTH_SHORT).show();
                    // @mblum TODO: got to main menu
                    Intent k = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(k);

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
}
