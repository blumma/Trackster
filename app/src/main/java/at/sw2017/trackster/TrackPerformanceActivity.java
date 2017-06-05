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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import at.sw2017.trackster.models.TimeConvert;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackPerformanceActivity extends AppCompatActivity {

    private static final String TAG = TrackPerformanceActivity.class.getSimpleName();
    private static final String METERS_1000 = "1000m";
    private static final String METERS_60 = "60m";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_performance);

        // @mblum TODO: get student id from previously selected student
        Intent myIntent = getIntent(); // gets the previously created intent
        String studentId = myIntent.getStringExtra("studentId");

        final int currentStudentId = Integer.parseInt(studentId);
        loadStudentById(currentStudentId);

        Button btnSavePerformance = (Button) findViewById(R.id.btn_save_performance);
        btnSavePerformance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Student student = null;
                try {
                    student = getStudentDataFromView(currentStudentId);
                    saveStudentPerformance(student);
                } catch (ParseException e) {
                    Toast.makeText(getApplication(), "Wrong date format! ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button stopwatch = (Button) findViewById(R.id.stopwatch1);
        stopwatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StopWatchActivity.class);
                myIntent.putExtra("type", METERS_60);
                startActivityForResult(myIntent, 0);
            }
        });
        Button stopwatch2 = (Button) findViewById(R.id.stopwatch2);
        stopwatch2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StopWatchActivity.class);
                myIntent.putExtra("type", METERS_1000);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(METERS_60);
            if (result != null) {
                EditText txtVorname = (EditText) findViewById(R.id.txt_60m);
                txtVorname.setText(TimeConvert.millisecToTime(Double.parseDouble(result)));
            }
            result = data.getStringExtra(METERS_1000);
            if (result != null) {
                EditText txtVorname = (EditText) findViewById(R.id.txt_1000m);
                txtVorname.setText(TimeConvert.millisecToTime(Double.parseDouble(result)));
            }
        }
    }

    private void loadStudentById(int studentId) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Student> call = apiService.getStudentById(studentId);
        call.enqueue(new Callback<Student>() {

            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Student student = response.body();
                    populateStudentView(student);
                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(TrackPerformanceActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), "Error while loading student data!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void populateStudentView(Student student) {

        EditText txtVorname = (EditText) findViewById(R.id.txt_vorname);
        txtVorname.setText(student.getVorname());

        EditText txtNachname = (EditText) findViewById(R.id.txt_nachname);
        Log.d(TAG, student.getNachname());
        txtNachname.setText(student.getNachname());

        EditText txtKlasse = (EditText) findViewById(R.id.txt_klasse);
        Log.d(TAG, student.getKlasse());
        txtKlasse.setText(student.getKlasse());

        EditText txtGeschlecht = (EditText) findViewById(R.id.txt_geschlecht);
        Log.d(TAG, student.getGeschlecht());
        txtGeschlecht.setText(student.getGeschlecht());

        EditText txtGebdatum = (EditText) findViewById(R.id.date_gebdatum);
        Log.d(TAG, student.getGeburtsdatum());
        txtGebdatum.setText(student.getGeburtsdatum());

        EditText txt60m = (EditText) findViewById(R.id.txt_60m);
        Log.d(TAG, String.valueOf(student.getPerformance60mRun()));
        txt60m.setText(TimeConvert.millisecToTime(student.getPerformance60mRun()));

        EditText txt1000m = (EditText) findViewById(R.id.txt_1000m);
        Log.d(TAG, String.valueOf(student.getPerformance1000mRun()));
        txt1000m.setText(TimeConvert.millisecToTime(student.getPerformance1000mRun()));

        EditText txtLongjump = (EditText) findViewById(R.id.txt_longjump);
        Log.d(TAG, String.valueOf(student.getPerformanceLongJump()));
        txtLongjump.setText(String.valueOf(student.getPerformanceLongJump()));

        EditText txtKugel = (EditText) findViewById(R.id.txt_kugel);
        txtKugel.setText(String.valueOf(student.getPerformanceShotPut()));

        EditText txtSchlag = (EditText) findViewById(R.id.txt_schlagball);
        txtSchlag.setText(String.valueOf(student.getPerformanceLongThrow()));

        EditText txtAll = (EditText) findViewById(R.id.txt_all);
        txtAll.setText(String.valueOf(student.getOverallScore()));

    }

    private Student getStudentDataFromView(int studentId) throws ParseException {

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
        student.setGeburtsdatum(txtGebdatum.getText().toString());

        EditText txt60m = (EditText) findViewById(R.id.txt_60m);
        student.setPerformance60mRun(TimeConvert.timeToMillisec(txt60m.getText().toString()));

        EditText txt1000m = (EditText) findViewById(R.id.txt_1000m);
        student.setPerformance1000mRun(TimeConvert.timeToMillisec(txt1000m.getText().toString()));

        EditText txtLongjump = (EditText) findViewById(R.id.txt_longjump);
        student.setPerformanceLongJump(Double.parseDouble(txtLongjump.getText().toString()));

        EditText txtSchlag = (EditText) findViewById(R.id.txt_schlagball);
        student.setPerformanceLongThrow(Double.parseDouble(txtSchlag.getText().toString()));

        EditText txtKugel = (EditText) findViewById(R.id.txt_kugel);
        student.setPerformanceShotPut(Double.parseDouble(txtKugel.getText().toString()));

        // Recalculate
        EditText txtAll = (EditText) findViewById(R.id.txt_all);
        txtAll.setText(String.valueOf(student.getOverallScore()));

        return student;
    }

    private void saveStudentPerformance(Student student) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.updateStudentById(student.getId(), student);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Successfully saved student performance!", Toast.LENGTH_SHORT).show();
                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(TrackPerformanceActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), "Error while loading student data!", Toast.LENGTH_SHORT).show();
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
