package at.sw2017.trackster;

/**
 * Created by Patrick on 26.04.2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import at.sw2017.trackster.models.Student;
import at.sw2017.trackster.models.TimeConvert;

public class StopWatchActivity extends AppCompatActivity {

    TextView textView ;
    Button start, stop, reset, save, savebestlap;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    ListView listView ;
    String[] ListElements = new String[] {  };
    List<String> ListElementsArrayList ;
    boolean running = false;
    ArrayAdapter<String> adapter ;
    String valuetype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        stop = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        save = (Button)findViewById(R.id.button4) ;
        savebestlap = (Button)findViewById(R.id.button5);
        listView = (ListView)findViewById(R.id.listview1);

        handler = new Handler() ;

        valuetype = this.getIntent().getStringExtra("type");

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(running){
                    ListElementsArrayList.add(textView.getText().toString());
                    System.out.println(textView.getText().toString());
                    adapter.notifyDataSetChanged();
                }
                else{
                    StartTime = SystemClock.uptimeMillis();
                    start.setText("Start/Lap");
                    running = true;
                    handler.postDelayed(runnable, 0);
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
                ListElementsArrayList.add(textView.getText().toString());
                System.out.println(textView.getText().toString());
                adapter.notifyDataSetChanged();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra(valuetype, Double.toString(MillisecondTime));
                setResult(Activity.RESULT_OK, resultData);
                finish();
            }
        });

        savebestlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra(valuetype, Double.toString(getBestLap()));
                setResult(Activity.RESULT_OK, resultData);
                finish();
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

                textView.setText(R.string.stopwatch_starttime);
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

    public double getBestLap(){

        if(ListElementsArrayList.size() == 0)
            return 0;

        if(ListElementsArrayList.size() == 1)
            return TimeConvert.timeToMillisec(ListElementsArrayList.get(0));

        double bestLap = TimeConvert.timeToMillisec(ListElementsArrayList.get(0));

        for(int i=1; i<ListElementsArrayList.size(); i++){
            double actLap = TimeConvert.timeToMillisec(ListElementsArrayList.get(i))-TimeConvert.timeToMillisec(ListElementsArrayList.get(i-1));
            if(actLap < bestLap){
                bestLap = actLap;
            }
        }

        return bestLap;
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