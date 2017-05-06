package at.sw2017.trackster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getApplication(), "Check login", Toast.LENGTH_SHORT).show();
                Intent k = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(k);

            }
        });

        fetchUsers();
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
