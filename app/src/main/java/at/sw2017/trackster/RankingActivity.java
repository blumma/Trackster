package at.sw2017.trackster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = RankingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        getStudentList();

    }


    private void getStudentList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {


            @Override
            public void onResponse(Call<List<Student>>call, Response<List<Student>> response) {
                if(response.isSuccessful()) {

                    List<Student> students = response.body();
                }
                else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), "Not logged in!", Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(RankingActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), "Error while loading students list!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }




}
