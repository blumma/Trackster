package at.sw2017.trackster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("do i get here2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_screen);

        final Button button = (Button) findViewById(R.id.button_60m);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent myIntent = new Intent(v.getContext(), StudentActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
