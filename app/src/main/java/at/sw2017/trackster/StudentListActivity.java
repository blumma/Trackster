package at.sw2017.trackster;

/**
 * Created by Patrick on 26.04.2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    private static final String TAG = StudentListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getClasses();
        getStudentList();
    }

    private void getClasses() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getClasses();
        call.enqueue(new Callback<List<Student>>() {

            @Override
            public void onResponse(Call<List<Student>>call, Response<List<Student>> response) {
                if(response.isSuccessful()) {

                    List<Student> students = response.body();
                    populateClassList(students);
                }
                else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), "Not logged in!", Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(StudentListActivity.this, LoginActivity.class);
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

    private void getStudentList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {

            @Override
            public void onResponse(Call<List<Student>>call, Response<List<Student>> response) {
                if(response.isSuccessful()) {

                    List<Student> students = response.body();
                    populateStudentList(students);
                }
                else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), "Not logged in!", Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(StudentListActivity.this, LoginActivity.class);
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

    private void populateClassList(List<Student> students) {

        String[] strClasses = new String[students.size()];
        int i = 0;

        for (Student s: students) {
            strClasses[i++] = (s.getKlasse());
        }



        Spinner dropdown = (Spinner)findViewById(R.id.dpClass);
        String[] items = strClasses;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }

    private void populateStudentList(List<Student> students) {

        String[] strStudents = new String[students.size()];
        int i = 0;

        for (Student s: students) {
            strStudents[i++] = (s.getVorname() + " " + s.getNachname());
        }
        // @mblum TODO: implement custom adapter to support StudentsList & use layout student_list_item
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strStudents);
        ListView studentList = (ListView) findViewById(R.id.student_list);
        studentList.setAdapter(adapter);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // @mblum TODO: retreive id from custom adapter -> this will not work proberly!
                int selectedStudentId = (int)id + 1;

                Intent k = new Intent(StudentListActivity.this, TrackPerformanceActivity.class);
                k.putExtra("studentId", "" + selectedStudentId);
                startActivity(k);

                /*String currentStudent = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplication(), "Clicked Item: " + currentStudent, Toast.LENGTH_SHORT).show();*/
            }
        });
    }
}



