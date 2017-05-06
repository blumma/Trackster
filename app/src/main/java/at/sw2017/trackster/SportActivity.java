package at.sw2017.trackster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        setupSportsEventHandler();
    }

    private void setupSportsEventHandler() {

        final Button buttonThrow = (Button) findViewById(R.id.button_throw);
        buttonThrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StudentListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        final Button button60m = (Button) findViewById(R.id.button_60m);
        button60m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StudentListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        final Button buttonWeitsprung = (Button) findViewById(R.id.button_weitsprung);
        buttonWeitsprung.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StudentListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        final Button button1000m = (Button) findViewById(R.id.button_1000m);
        button1000m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StudentListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
