package kitkatstudio.com.taskmanagement.fragments;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

import kitkatstudio.com.taskmanagement.MainActivity;
import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.connection.Connection;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    TextView task1,description1, start_date1, end_date1, percentage1,assigned_by1;

    EditText message;

    Button btn_submit;

    private RelativeLayout relativeLayout;


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        relativeLayout = view.findViewById(R.id.progressBar);

        task1 = view.findViewById(R.id.task);
        description1 = view.findViewById(R.id.description);
        start_date1 = view.findViewById(R.id.start_date);
        end_date1 = view.findViewById(R.id.end_date);
        percentage1 = view.findViewById(R.id.percentage);
        assigned_by1 = view.findViewById(R.id.assigned_by);

        message = view.findViewById(R.id.message);

        btn_submit = view.findViewById(R.id.btn_submit);

        final String strtext = getArguments().getString(Intent.EXTRA_TEXT);

        Log.e("strtext", strtext);

        String task  = getArguments().getString("task");
        task1.setText(task);

        String description  = getArguments().getString("description");
        description1.setText(description);

        String start_date  = getArguments().getString("start_date");
        start_date1.setText("Start Date : " + start_date);

        String end_date  = getArguments().getString("end_date");
        end_date1.setText("End Date : "  + end_date);

        final String percentage  = getArguments().getString("percentage");
        percentage1.setText(percentage);

        final String assigned_by  = getArguments().getString("assigned_by");
        assigned_by1.setText("Assigned By : " + assigned_by);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message1 = message.getText().toString();

                LoginData insertData = new LoginData(relativeLayout);
                insertData.execute(strtext, message1);
            }
        });

        return view;
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

                String link = Connection.API+"message.php";
                String data = URLEncoder.encode("task_id", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");

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

                if (jsonObject.getString("response").equals("success"))
                {
                    //prefManager.setUserEmailId("","","","","");
                    final Intent intent = new Intent(getContext(), MainActivity.class);
                    final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);

                    new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Message!")
                            .setMessage("Submit Successfully")
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
                    final Intent intent = new Intent(getContext(), MainActivity.class);
                    final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);

                    new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Message!")
                            .setMessage("Submit Successfully")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(intent, activityOptions.toBundle());
                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            })
                            .show();
//                    new AlertDialog.Builder(getContext())
//                            .setIcon(R.mipmap.ic_launcher_round)
//                            .setTitle("Alert!")
//                            .setMessage("Wrong Password \nPlease Try Again")
//                            .setPositiveButton("Ok",null)
//                            .show();

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

