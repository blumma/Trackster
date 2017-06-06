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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import at.sw2017.trackster.models.TimeConvert;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    private static final String TAG = StudentListActivity.class.getSimpleName();
    private int number_of_students = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        getStudentList("1a");

        final ImageButton buttonAddStudent = (ImageButton) findViewById(R.id.button_add_student);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(StudentListActivity.this, StudentActivity.class);
                final Spinner dropdown = (Spinner)findViewById(R.id.dpClass);
                k.putExtra("classString", dropdown.getSelectedItem().toString());
                startActivity(k);
            }
        });

        getClasses();

    }

    private void getClasses() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getClasses();
        call.enqueue(new Callback<List<Student>>() {

            @Override
            public void onResponse(Call<List<Student>>call, Response<List<Student>> response) {
                if(response.isSuccessful()) {

                    List<Student> students = response.body();
                    number_of_students = students.size();
                    populateClassList(students);
                }
                else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(StudentListActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), R.string.error_student_list, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>>call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }

    private void getStudentList(String clas) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getStudentsByClass(clas);
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
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(StudentListActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), R.string.error_student_list, Toast.LENGTH_SHORT).show();
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

        String[] strClasses = new String[students.size() + 1];
        strClasses[0] = "";
        int i = 1;

        for (Student s: students) {
            strClasses[i++] = (s.getKlasse());
        }



        final Spinner dropdown = (Spinner)findViewById(R.id.dpClass);
        String[] items = strClasses;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(1);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String clas = dropdown.getSelectedItem().toString();
                if(clas != "")
                {
                    getStudentList(clas);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
           /* @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





            }*/
        });



    }

    private void populateStudentList(final List<Student> students) {

        // @mblum TODO: implement custom adapter to support StudentsList & use layout student_list_item
        //ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strStudents);
        final ListView studentList = (ListView) findViewById(R.id.student_list);
        CustomAdapter adapter =  new CustomAdapter(students,getApplicationContext());
        studentList.setAdapter(adapter);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Student student = students.get(position);

                // @mblum TODO: retreive id from custom adapter -> this will not work proberly!
                //int selectedStudentId = (int)id + 1;

                

                Intent k = new Intent(StudentListActivity.this, TrackPerformanceActivity.class);
                k.putExtra("studentId", "" + student.getId());
                startActivity(k);

                /*String currentStudent = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplication(), "Clicked Item: " + currentStudent, Toast.LENGTH_SHORT).show();*/
            }
        });
    }
}



