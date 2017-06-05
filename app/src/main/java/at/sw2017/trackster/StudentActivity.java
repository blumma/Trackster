package at.sw2017.trackster;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import at.sw2017.trackster.models.Student;

public class StudentActivity extends AppCompatActivity {

    private String[] arrayAcademicYears;
    private String[] arraySubClass;
    private int numberStudents = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        this.initGUI();
    }

    private void initGUI(){

        Intent myIntent = getIntent();
        numberStudents = Integer.parseInt(myIntent.getStringExtra("numberStudents"));

        arrayAcademicYears = new String[] {
                "1", "2", "3", "4", "5", "6", "7", "8"
        };

        arraySubClass = new String[] {
                "A", "B", "C", "D", "E", "F", "G"
        };

        Spinner spinnerYear = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arraySubClass);
        adapterYear.setDropDownViewResource(R.layout.spinner_layout);
        spinnerYear.setAdapter(adapterYear);


        Spinner spinnerSubClasses = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<String> adapterSubClasses = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, arrayAcademicYears);
        adapterYear.setDropDownViewResource(R.layout.spinner_layout);
        spinnerSubClasses.setAdapter(adapterSubClasses);

        final ImageButton buttonAddStudent = (ImageButton) findViewById(R.id.button_add_student);
        buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Student student_to_add = new Student();
                student_to_add.setId(numberStudents + 1);
                student_to_add.setGeburtsdatum(((EditText)(findViewById(R.id.gebDateText))).getText().toString());

                if(((CheckBox)(findViewById(R.id.checkBox))).isChecked())
                    student_to_add.setGeschlecht("M");

                else if(((CheckBox)(findViewById(R.id.checkBox2))).isChecked())
                    student_to_add.setGeschlecht("F");

                else student_to_add.setGeschlecht("X");

                Spinner yearSpinner=(Spinner) findViewById(R.id.spinner3);
                String year = yearSpinner.getSelectedItem().toString();

                Spinner subClassSpinner=(Spinner) findViewById(R.id.spinner4);
                String subClass = subClassSpinner.getSelectedItem().toString();

                student_to_add.setKlasse(year+subClass);

                student_to_add.setVorname(((EditText)(findViewById(R.id.textView4))).getText().toString());
                student_to_add.setNachname(((EditText)(findViewById(R.id.textView3))).getText().toString());

                /*String student_json = new Gson().toJson(student_to_add);
                Intent resultData = new Intent();
                resultData.putExtra("returnStudent", student_json);
                setResult(Activity.RESULT_OK, resultData);*/

                //TODO: Insert student to database


                finish();

            }
        });
    }
}
