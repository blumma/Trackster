package at.sw2017.trackster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.api.SessionCookieStore;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initMenuButtonHandler();

        final Button buttonLogout = (Button) findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.logout();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody>call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Successful logout!", Toast.LENGTH_SHORT).show();
                    SessionCookieStore.getStore().clear();
                    Intent k = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(k);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void initMenuButtonHandler() {

        final Button buttonInput = (Button) findViewById(R.id.button_input);
        buttonInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), SportActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });


        final Button buttonRanking = (Button) findViewById(R.id.button_1000m);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ViewPageActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
