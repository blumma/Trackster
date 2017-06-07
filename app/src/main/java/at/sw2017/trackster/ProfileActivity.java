package at.sw2017.trackster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.api.SessionCookieStore;
import at.sw2017.trackster.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sbuersch on 07.06.2017.
 */

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        String user_string = intent.getStringExtra("User");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(user_string);
            ((EditText) findViewById(R.id.edit_text_firstname)).setText(jsonObject.getString("firstName"));
            ((EditText) findViewById(R.id.edit_text_username)).setText(jsonObject.getString("email"));
            ((EditText) findViewById(R.id.edit_text_lastname)).setText(jsonObject.getString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Button buttonChange = (Button) findViewById(R.id.button_change);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                User u = new User();
                u.setEmail(((EditText) findViewById(R.id.edit_text_username)).getText().toString());
                u.setPwd(((EditText) findViewById(R.id.edit_text_old_password)).getText().toString());
                String new_pw = ((EditText) findViewById(R.id.edit_text_new_password)).getText().toString();

                ProfileActivity.this.actNewPwInDatabase(u, new_pw);
                finish();

            }
        });
    }

    private void actNewPwInDatabase(User user, String new_pw) {
        //TODO: Save in Database
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.changeUser(new_pw,user);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "PW changed!", Toast.LENGTH_SHORT).show();
                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), "Error while changing PW!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }


}
