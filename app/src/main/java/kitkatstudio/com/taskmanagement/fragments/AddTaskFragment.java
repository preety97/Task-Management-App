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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import kitkatstudio.com.taskmanagement.LoginActivity;
import kitkatstudio.com.taskmanagement.MainActivity;
import kitkatstudio.com.taskmanagement.MainAdminActivity;
import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.connection.Connection;
import kitkatstudio.com.taskmanagement.network.NetworkUtils;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {

    private EditText employee,task,description, start_date, end_date ;
    private RelativeLayout relativeLayout;
    private PrefManager prefManager;


    Spinner select_employee;

    Button btn_submit;


    public AddTaskFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

//        employee = view.findViewById(R.id.employee);
        task = view.findViewById(R.id.task);
        description = view.findViewById(R.id.description);
        start_date = view.findViewById(R.id.start_date);
        end_date = view.findViewById(R.id.end_date);

        //select_employee = view.findViewById(R.id.select_employee);

        prefManager = new PrefManager(Objects.requireNonNull(getContext()));



        select_employee= view.findViewById(R.id.select_employee);

        btn_submit = view.findViewById(R.id.btn_submit);

        relativeLayout = view.findViewById(R.id.progressBar);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                boolean cancel = false;
                View focus = null;

//                employee.setError(null);
                task.setError(null);
                description.setError(null);
                start_date.setError(null);
                end_date.setError(null);


                String employee1=select_employee.getSelectedItem().toString();

                //final String employee1 = employee.getText().toString();
                final String task1 = task.getText().toString();
                final String description1 = description.getText().toString();
                final String start_date1 = start_date.getText().toString();
                final String end_date1 = end_date.getText().toString();


                InsertData insertData = new InsertData(relativeLayout);

                if (!prefManager.getUserEmailId().equals(""))
                {
                    insertData.execute(employee1, task1, description1, start_date1, end_date1, prefManager.getUserEmailId());
                }
                else
                {
                    insertData.execute(employee1, task1, description1, start_date1, end_date1, prefManager.getAdminEmailId());
                }



            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new SpinnerData(relativeLayout).execute();
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

                String link = Connection.API+"addtask.php";

                String data = URLEncoder.encode("employee", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                data += "&" + URLEncoder.encode("task", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
                data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");
                data += "&" + URLEncoder.encode("start_date", "UTF-8") + "=" + URLEncoder.encode(strings[3], "UTF-8");
                data += "&" + URLEncoder.encode("end_date", "UTF-8") + "=" + URLEncoder.encode(strings[4], "UTF-8");
                data += "&" + URLEncoder.encode("assigned_by", "UTF-8") + "=" + URLEncoder.encode(strings[5], "UTF-8");

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


                    Intent intent = new Intent(getContext(), MainAdminActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "yes");
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intent, activityOptions.toBundle());
                    getActivity().finish();
                } else {
//                    Intent intent = new Intent(getContext(), MainAdminActivity.class);
//                    intent.putExtra(Intent.EXTRA_TEXT, "yes");
//                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);
//                    startActivity(intent, activityOptions.toBundle());
//
//                    new AlertDialog.Builder(getContext()).setTitle("Alert!").setIcon(R.mipmap.ic_launcher_round).setMessage("Success \nTask Add ").setPositiveButton("ok", null).show();

                    final Intent intent = new Intent(getContext(), MainAdminActivity.class);
                    final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);

                    new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Message!")
                            .setMessage("Successfully Added ")
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

//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
//                }

            }
            catch (JSONException e){
                Log.e("Error",e.getMessage());
                Toast.makeText(getContext(), "Server Error\nPlease Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SpinnerData extends AsyncTask<String,Integer,String>
    {
        RelativeLayout bar;

        SpinnerData(RelativeLayout rel)
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

                String link = Connection.API+"select_employee.php";

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

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

            ArrayList<String> names = new ArrayList<>();
            Log.e("USer names",s);
            try {
                JSONArray array = new JSONArray(s);

                for (int i=0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    names.add(object.getString("user_name"));
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,names);

                select_employee.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
