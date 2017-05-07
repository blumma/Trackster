package at.sw2017.trackster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackPerformanceActivity extends AppCompatActivity {

    private static final String TAG = TrackPerformanceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_performance);

        loadStudentById(1);
    }

    private void loadStudentById(int studentId) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Student> call = apiService.getStudentById(studentId);
        call.enqueue(new Callback<Student>() {

            @Override
            public void onResponse(Call<Student>call, Response<Student> response) {
                if(response.isSuccessful()) {
                    Student student = response.body();
                    populateStudentView(student);
                }
                else {
                    Toast.makeText(getApplication(), "Error while loading student data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void populateStudentView(Student student) {

        EditText txtVorname = (EditText) findViewById(R.id.txt_vorname);
        txtVorname.setText(student.getVorname());

        EditText txtNachname = (EditText) findViewById(R.id.txt_nachname);
        txtNachname.setText(student.getNachname());

        EditText txtKlasse = (EditText) findViewById(R.id.txt_klasse);
        txtKlasse.setText(student.getKlasse());

        EditText txtGeschlecht = (EditText) findViewById(R.id.txt_geschlecht);
        txtGeschlecht.setText(student.getGeschlecht());

        EditText txtGebdatum = (EditText) findViewById(R.id.date_gebdatum);
        txtGebdatum.setText(student.getGeburtsdatum().toString());

        EditText txt60m = (EditText) findViewById(R.id.txt_60m);
        txt60m.setText(String.valueOf(student.getPerformance60m()));

        EditText txt1000m = (EditText) findViewById(R.id.txt_1000m);
        txt1000m.setText(String.valueOf(student.getPerformance1000m()));

        EditText txtLongjump = (EditText) findViewById(R.id.txt_longjump);
        txtLongjump.setText(String.valueOf(student.getPerformanceLongJump()));
    }

}
