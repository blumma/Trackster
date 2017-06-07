package at.sw2017.trackster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity {

    private String[] arrayAcademicYears;
    private String[] arraySubClass;
    private static final String TAG = StudentActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        this.initGUI();
    }

    private Student getStudentFromView(){
        Student student_to_add = new Student();

        String geburtsdatum = ((EditText)(findViewById(R.id.gebDateText))).getText().toString();
        String[] geb_parts = geburtsdatum.split("-");
        String year_geb = geb_parts[2];
        String month = geb_parts[1];
        String day = geb_parts[0];

        student_to_add.setGeburtsdatum(year_geb+"-"+month+"-"+day);

        if(((CheckBox)(findViewById(R.id.checkBox))).isChecked())
            student_to_add.setGeschlecht("m");

        else if(((CheckBox)(findViewById(R.id.checkBox2))).isChecked())
            student_to_add.setGeschlecht("w");

        else student_to_add.setGeschlecht("x");

        Spinner yearSpinner=(Spinner) findViewById(R.id.spinnerGrade);
        String year = yearSpinner.getSelectedItem().toString();

        Spinner subClassSpinner=(Spinner) findViewById(R.id.spinnerClass);
        String subClass = subClassSpinner.getSelectedItem().toString();

        student_to_add.setKlasse(year+subClass);

        student_to_add.setVorname(((EditText)(findViewById(R.id.editForename))).getText().toString());
        student_to_add.setNachname(((EditText)(findViewById(R.id.editSurename))).getText().toString());

        student_to_add.setPerformanceSumPoints(0);
        student_to_add.setPerformance60mRun(99.0);
        student_to_add.setPerformance1000mRun(3599.0);
        student_to_add.setPerformanceLongJump(0.0);
        student_to_add.setPerformanceLongThrow(0.0);
        student_to_add.setPerformanceShotPut(0.0);



        return  student_to_add;
    }

    private void initGUI(){

        arrayAcademicYears = new String[] {
                "1", "2", "3", "4", "5", "6", "7", "8"
        };

        arraySubClass = new String[] {
                "a", "b", "c", "d", "e", "f", "g"
        };

        Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerClass);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arraySubClass);
        adapterYear.setDropDownViewResource(R.layout.spinner_layout);
        spinnerYear.setAdapter(adapterYear);


        Spinner spinnerSubClasses = (Spinner) findViewById(R.id.spinnerGrade);
        ArrayAdapter<String> adapterSubClasses = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arrayAcademicYears);
        adapterYear.setDropDownViewResource(R.layout.spinner_layout);
        spinnerSubClasses.setAdapter(adapterSubClasses);

        Intent myIntent = getIntent();
        String class_string = myIntent.getStringExtra("classString");
        if(class_string.length() == 2) {
            spinnerSubClasses.setSelection(class_string.charAt(0)-49);
            spinnerYear.setSelection(class_string.charAt(1) - 97);
        }
        Button buttonAddStudent = (Button) findViewById(R.id.button_ok);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = getStudentFromView();
                //TODO: Insert student to database
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponseBody> call = apiService.addStudent(student);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplication(), "Successfully saved student!", Toast.LENGTH_SHORT).show();
                        } else {
                            switch (response.code()) {
                                case 401:
                                    Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                                    Intent k = new Intent(StudentActivity.this, LoginActivity.class);
                                    startActivity(k);
                                    break;
                                case 500:
                                default:
                                    System.out.println(response.toString());
                                    Toast.makeText(getApplication(), "Error while loading student data!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });



                finish();

            }
        });
    }
}
