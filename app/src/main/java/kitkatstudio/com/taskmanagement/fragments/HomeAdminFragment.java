package kitkatstudio.com.taskmanagement.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.TypeActivity;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

public class HomeAdminFragment extends Fragment {

    LinearLayout profile, task, pending, completed, message, share, update, logout;

    private PrefManager prefManager;


    public HomeAdminFragment() {
        // Required empty public constructor
    }

    public void logout()
    {
        prefManager.setAdminEmailId("","");
        Intent intent = new Intent(getActivity(), TypeActivity.class);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getActivity(), android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent, activityOptions.toBundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

        profile = view.findViewById(R.id.profile);
        task = view.findViewById(R.id.task);
        update = view.findViewById(R.id.update);
        message = view.findViewById(R.id.message);
        share = view.findViewById(R.id.share);
        logout = view.findViewById(R.id.logout);
        pending = view.findViewById(R.id.pending);
        completed = view.findViewById(R.id.completed);

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        prefManager = new PrefManager(getContext());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new ProfileAdminFragment()).commit();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new AddTaskFragment()).commit();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new PendingAdminFragment()).commit();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String appLink = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, appLink);
                startActivity(Intent.createChooser(i, "Choose One"));
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new ShowMessageFragment()).commit();
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new CompletedAdminFragment()).commit();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.contentAdmin, new UpdateAdminFragment()).commit();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });

        return view;
    }


}
