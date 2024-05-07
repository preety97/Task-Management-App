package kitkatstudio.com.taskmanagement;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

import kitkatstudio.com.taskmanagement.connection.Connection;
import kitkatstudio.com.taskmanagement.network.NetworkUtils;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText text_email,text_password;
    private RelativeLayout relativeLayout;
    private PrefManager prefManager;
    String token;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        prefManager = new PrefManager(Objects.requireNonNull(this));

        if (!prefManager.getAdminEmailId().equals(""))
        {
            Intent intent = new Intent(LoginAdminActivity.this, MainAdminActivity.class);
            ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, activityOptions.toBundle());
            finish();
        }

        text_email = findViewById(R.id.input_roll);
        text_password = findViewById(R.id.input_password);
        relativeLayout = findViewById(R.id.progressBar);
        Button appCompatButton = findViewById(R.id.btn_login);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(View view)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        view = null;
        boolean flag = false;
        final String email = text_email.getText().toString();
        final String password = text_password.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            text_email.setError("Enter your Username");
            view = text_email;
            flag = true;
        }
        else if (TextUtils.isEmpty(password))
        {
            text_password.setError("Enter your Password");
            view = text_password;
            flag = true;
        }

        if (flag)
        {
            view.requestFocus();
        }
        else
        {
            if (NetworkUtils.isNetworkAvailable(Objects.requireNonNull(this)))
            {
                LoginData insertData = new LoginData(relativeLayout);
                insertData.execute(email, password, token);
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Alert!")
                        .setMessage("Turn on Internet Connection")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        Settings.ACTION_WIFI_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }


    class LoginData extends AsyncTask<String,Integer,String>
    {
        RelativeLayout bar;
        String user_email;

        LoginData(RelativeLayout rel)
        {
            bar = rel;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                user_email = strings[0];

                String link = Connection.API+"loginadmin.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                // read server response
                line = reader.readLine();
                builder.append(line);
                return builder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "{\"response\":\"error\"}";
            }
        }

        @Override
        protected void onPostExecute(String s) {

            bar.setVisibility(View.GONE);
            try
            {
                JSONObject jsonObject = new JSONObject(s);

//                if (jsonObject.has("response")) {

                if (jsonObject.getString("response").equals("success")) {

                    Log.e("msg", jsonObject.getString("name"));

                    prefManager.setAdminEmailId(user_email, jsonObject.getString("name"));
                    //prefManager.setUserEmailId(user_email, jsonObject.getString("name"));


                    Intent intent = new Intent(LoginAdminActivity.this, MainAdminActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "yes");
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(LoginAdminActivity.this, android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intent, activityOptions.toBundle());
                    finish();
                } else {
                    new AlertDialog.Builder(LoginAdminActivity.this).setTitle("Alert!").setIcon(R.mipmap.ic_launcher_round).setMessage("Wrong Username/Password \n Please Try Again").setPositiveButton("ok", null).show();
                }

//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
//                }

            }
            catch (JSONException e){
                Log.e("Error",e.getMessage());
                Toast.makeText(LoginAdminActivity.this, "Server Error\nPlease Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
