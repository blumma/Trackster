package at.sw2017.trackster;

/**
 * Created by Patrick on 26.04.2017.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentActivity extends Activity {

    String[] mobileArray = {"Hias", "Helmut", "Jan", "Sven",
            "a", "Johann Germ", "Longjohn Silver with long name", "Max"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_student);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        final ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "You clicked: " + itemValue + "at position: " + position, Toast.LENGTH_LONG)
                        .show();


            }
        });

            final Button button = (Button) findViewById(R.id.start);
            button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Intent myIntent = new Intent(v.getContext(), StopWatchActivity.class);
            startActivityForResult(myIntent, 0);

            }
        });
    }
}



