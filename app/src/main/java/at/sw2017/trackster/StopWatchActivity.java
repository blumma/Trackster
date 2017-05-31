package at.sw2017.trackster;

/**
 * Created by Patrick on 26.04.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import at.sw2017.trackster.models.Student;

public class StopWatchActivity extends AppCompatActivity {

    TextView textView ;
    Button start, stop, reset, save;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    ListView listView ;
    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    boolean running = false;
    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        stop = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        save = (Button)findViewById(R.id.button4) ;
        listView = (ListView)findViewById(R.id.listview1);

        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                if(running){
                    ListElementsArrayList.add(textView.getText().toString());
                    System.out.println(textView.getText().toString());
                    adapter.notifyDataSetChanged();
                }
                else{
                    start.setText("Start/Lap");
                    running = true;
                }

                reset.setEnabled(false);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                start.setText("Start");

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                textView.setText("00:00:00");
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        /*lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                System.out.println(textView.getText().toString());
               // System.out.println(student.getPerformance60mRun());

                adapter.notifyDataSetChanged();

            }
        });*/

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

}