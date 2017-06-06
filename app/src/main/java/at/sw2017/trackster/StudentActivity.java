package at.sw2017.trackster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import at.sw2017.trackster.models.Student;

public class StudentActivity extends AppCompatActivity {

    private String[] arrayAcademicYears;
    private String[] arraySubClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        this.initGUI();
    }

    private Student getStudentFromView(){
        Student student_to_add = new Student();
        student_to_add.setGeburtsdatum(((EditText)(findViewById(R.id.gebDateText))).getText().toString());

        if(((CheckBox)(findViewById(R.id.checkBox))).isChecked())
            student_to_add.setGeschlecht("M");

        else if(((CheckBox)(findViewById(R.id.checkBox2))).isChecked())
            student_to_add.setGeschlecht("F");

        else student_to_add.setGeschlecht("X");

        Spinner yearSpinner=(Spinner) findViewById(R.id.spinnerClass);
        String year = yearSpinner.getSelectedItem().toString();

        Spinner subClassSpinner=(Spinner) findViewById(R.id.spinnerGrade);
        String subClass = subClassSpinner.getSelectedItem().toString();

        student_to_add.setKlasse(year+subClass);

        student_to_add.setVorname(((EditText)(findViewById(R.id.editForename))).getText().toString());
        student_to_add.setNachname(((EditText)(findViewById(R.id.editSurename))).getText().toString());

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
                Student student_to_add = getStudentFromView();
                //TODO: Insert student to database
                finish();

            }
        });
    }
}
