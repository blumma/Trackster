package at.sw2017.trackster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackPerformanceActivity extends AppCompatActivity {

    private static final String TAG = TrackPerformanceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_performance);

        // @mblum TODO: get student id from previously selected student
        final int currentStudentId = 1;
        loadStudentById(currentStudentId);

        Button btnSavePerformance = (Button) findViewById(R.id.btn_save_performance);
        btnSavePerformance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Student student = getStudentDataFromView(currentStudentId);
                saveStudentPerformance(student);
            }
        });
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

    private Student getStudentDataFromView(int studentId) {

        Student student = new Student(studentId);

        EditText txtVorname = (EditText) findViewById(R.id.txt_vorname);
        student.setVorname(txtVorname.getText().toString());

        EditText txtNachname = (EditText) findViewById(R.id.txt_nachname);
        student.setNachname(txtNachname.getText().toString());

        EditText txtKlasse = (EditText) findViewById(R.id.txt_klasse);
        student.setKlasse(txtKlasse.getText().toString());

        EditText txtGeschlecht = (EditText) findViewById(R.id.txt_geschlecht);
        student.setGeschlecht(txtGeschlecht.getText().toString());

        EditText txtGebdatum = (EditText) findViewById(R.id.date_gebdatum);
        student.setGeburtsdatum(new Date(txtGebdatum.getText().toString()));

        EditText txt60m = (EditText) findViewById(R.id.txt_60m);
        student.setPerformance60m(Double.parseDouble(txt60m.getText().toString()));

        EditText txt1000m = (EditText) findViewById(R.id.txt_1000m);
        student.setPerformance1000m(Double.parseDouble(txt1000m.getText().toString()));

        EditText txtLongjump = (EditText) findViewById(R.id.txt_longjump);
        student.setPerformanceLongJump(Double.parseDouble(txtLongjump.getText().toString()));

        return student;
    }

    private void saveStudentPerformance(Student student) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.updateStudentById(student.getId(), student);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody>call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Successfully saved student performance!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplication(), "Error while loading student data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

}
