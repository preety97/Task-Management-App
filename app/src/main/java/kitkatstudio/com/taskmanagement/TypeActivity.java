package kitkatstudio.com.taskmanagement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

public class TypeActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radiobutton;

    private PrefManager prefManager;

    Button button;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        prefManager = new PrefManager(Objects.requireNonNull(this));

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                radiobutton = (RadioButton) findViewById(selectedId);

                String value= radiobutton.getText().toString();

                if(value.equals("Employee"))
                {
                    Intent intent = new Intent(TypeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(TypeActivity.this, LoginAdminActivity.class);
                    startActivity(intent);
                }
            }
        });


        if (!prefManager.getUserEmailId().equals(""))
        {
            Intent intent = new Intent(TypeActivity.this, MainActivity.class);
            ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, activityOptions.toBundle());
            finish();
        }
        else if (!prefManager.getAdminEmailId().equals(""))
        {
            Intent intent = new Intent(TypeActivity.this, MainAdminActivity.class);
            ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, activityOptions.toBundle());
            finish();
        }


    }
}
