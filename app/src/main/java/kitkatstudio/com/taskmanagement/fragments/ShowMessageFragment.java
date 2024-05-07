package kitkatstudio.com.taskmanagement.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.adapters.CompletedAdapter;
import kitkatstudio.com.taskmanagement.adapters.ShowMessageAdapter;
import kitkatstudio.com.taskmanagement.connection.Connection;
import kitkatstudio.com.taskmanagement.customclass.CompletedContent;
import kitkatstudio.com.taskmanagement.customclass.ShowMessageContent;
import kitkatstudio.com.taskmanagement.customclass.ShowMessageContent.ShowMessageItem;
import kitkatstudio.com.taskmanagement.network.NetworkUtils;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;
import kitkatstudio.com.taskmanagement.response.Response;


public class ShowMessageFragment extends Fragment implements Response {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    PrefManager prefManager;

    RecyclerView recyclerView;


    ProgressBar prgLoading;
    SwipeRefreshLayout swipeRefreshLayout = null;
    RelativeLayout layout;

    static boolean flag = false;

    int CAMERA_CODE=1;


    public ShowMessageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showmessage_list, container, false);

        this.prgLoading = view.findViewById(R.id.prgLoading);
        layout = view.findViewById(R.id.layout);

        prefManager = new PrefManager(getContext());
        // Set the adapter

        recyclerView = view.findViewById(R.id.list);


        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        if (NetworkUtils.isNetworkAvailable(getContext()))
        {
            FetchData insertData = new FetchData();
            insertData.response = this;

            if (!prefManager.getUserEmailId().equals("")) {
                insertData.execute(prefManager.getUserEmailId());
            }
            else
            {
                insertData.execute(prefManager.getAdminEmailId());
            }
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

        this.swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        FetchData insertData = new FetchData();
                        insertData.response = ShowMessageFragment.this;
                        insertData.execute(prefManager.getAdminEmailId());
                    }
                },3000);
            }
        });

        return view;
    }


    @Override
    public void processFinish(String result) {

        flag = false;

        try {
            ShowMessageContent.ITEMS.clear();
            JSONArray array = new JSONArray(result);
            for (int i=0;i<array.length();i++)
            {
                JSONObject object = array.getJSONObject(i);
                String task = object.getString("task");
                String description = object.getString("description");
                String message = object.getString("message");

                flag = true;
                ShowMessageItem item = new ShowMessageItem(task,description,message);
                ShowMessageContent.ITEMS.add(item);
            }


            if (flag) {
                layout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new ShowMessageAdapter(ShowMessageContent.ITEMS, getContext()));
            }
            else
            {
                layout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

        } catch (JSONException e) {

            recyclerView.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);

            //e.printStackTrace();
        }
    }


    class FetchData extends AsyncTask<String, Void, String>
    {

        Response response = null;

        @Override
        protected void onPreExecute() {
            prgLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                Log.e("Select Completed : ",strings[0]);
                String link = Connection.API+"show_message.php";
                String data = URLEncoder.encode("emp_email", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");

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

            prgLoading.setVisibility(View.GONE);
            try {
                response.processFinish(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}