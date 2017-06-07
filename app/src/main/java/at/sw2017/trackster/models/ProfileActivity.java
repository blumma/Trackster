package at.sw2017.trackster.models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import at.sw2017.trackster.R;

/**
 * Created by sbuersch on 07.06.2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final Button buttonChange = (Button) findViewById(R.id.button_change);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User u = new User();
                u.setEmail(((EditText) findViewById(R.id.edit_text_username)).getText().toString());
                u.setPwd(((EditText) findViewById(R.id.edit_text_old_password)).getText().toString());
                String new_pw = ((EditText) findViewById(R.id.edit_text_new_password)).getText().toString();

                ProfileActivity.this.actNewPwInDatabase(u, new_pw);

            }
        });
    }

    private void actNewPwInDatabase(User user, String new_pw) {
        //TODO: Save in Database
    }


}
