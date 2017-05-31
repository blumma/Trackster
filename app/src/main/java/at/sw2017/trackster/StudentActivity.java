package at.sw2017.trackster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StudentActivity extends AppCompatActivity {

    private String[] arrayAcademicYears;
    private String[] arraySubClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        this.initGUI();
    }

    private void initGUI(){
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
    }
}
