package kitkatstudio.com.taskmanagement.fragments;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.TypeActivity;
import kitkatstudio.com.taskmanagement.connection.Connection;
import kitkatstudio.com.taskmanagement.network.NetworkUtils;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateAdminFragment extends Fragment {

    EditText old_pass,new_pass;
    AppCompatButton button;
    private RelativeLayout relativeLayout;
    PrefManager prefManager;

    public UpdateAdminFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_admin, container, false);

        old_pass = view.findViewById(R.id.input_password);
        new_pass = view.findViewById(R.id.input_password_new);
        button = view.findViewById(R.id.btn_login);

        prefManager = new PrefManager(Objects.requireNonNull(getContext()));

        relativeLayout = view.findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vv = null;
                boolean flag = false;
                String old = old_pass.getText().toString();
                String newn = new_pass.getText().toString();
                if (TextUtils.isEmpty(old))
                {
                    old_pass.setError("Enter Current Password");
                    vv = old_pass;
                    flag = true;
                }
                else if (TextUtils.isEmpty(newn))
                {
                    new_pass.setError("Enter new Password");
                    vv = new_pass;
                    flag = true;
                }
                else if (newn.length() < 6)
                {
                    new_pass.setError("Password should at least 6 char length");
                    vv = new_pass;
                    flag = true;
                }

                if (flag)
                {
                    vv.requestFocus();
                }
                else
                {
                    if (NetworkUtils.isNetworkAvailable(getContext()))
                    {
                        InsertData insertData = new InsertData(relativeLayout);
                        insertData.execute(prefManager.getAdminEmailId(),old, newn);
                    }
                    else
                    {
                        new AlertDialog.Builder(getContext())
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
        });

        return view;
    }

    class InsertData extends AsyncTask<String,Integer,String>
    {
        RelativeLayout bar;

        InsertData(RelativeLayout rel)
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
                String link = Connection.API+"updateadmin.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                data += "&" + URLEncoder.encode("old", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
                data += "&" + URLEncoder.encode("newp", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();

                //Log.e("Link",writer.toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();

                // read server response
                String line = reader.readLine();
                builder.append(line);

                return builder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "Error! "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

            bar.setVisibility(View.GONE);
            try
            {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("response").equals("success"))
                {
                    prefManager.setAdminEmailId("","");
                    final Intent intent = new Intent(getContext(), TypeActivity.class);
                    final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);

                    new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Message!")
                            .setMessage("Password Update Successfully")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(intent, activityOptions.toBundle());
                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            })
                            .show();

                }
                else
                {
                    new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Alert!")
                            .setMessage("Wrong Password \nPlease Try Again")
                            .setPositiveButton("Ok",null)
                            .show();

                }
            }
            catch (JSONException e){
                //Log.e("Error",e.getMessage());
                new AlertDialog.Builder(getContext())
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Alert!")
                        .setMessage("Please Try Again")
                        .setPositiveButton("Ok",null)
                        .show();
            }
        }
    }
}
