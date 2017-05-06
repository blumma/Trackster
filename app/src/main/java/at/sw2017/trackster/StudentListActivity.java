package at.sw2017.trackster;

/**
 * Created by Patrick on 26.04.2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

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

                    Toast.makeText(getApplication(), "Successfully loaded students!", Toast.LENGTH_SHORT).show();
                    // @mblum TODO: add students to list view
                }
            }

            @Override
            public void onFailure(Call<List<Student>>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}



