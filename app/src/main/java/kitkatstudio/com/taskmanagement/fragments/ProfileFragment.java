package kitkatstudio.com.taskmanagement.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.prefManager.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    PrefManager prefManager;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView profile_name = view.findViewById(R.id.profile_name);
        TextView name = view.findViewById(R.id.name);
        TextView email = view.findViewById(R.id.email);

        prefManager = new PrefManager(Objects.requireNonNull(getContext()));
        name.setText(prefManager.getName());
        email.setText(prefManager.getUserEmailId());

        char first = prefManager.getName().charAt(0);
        String pn = "  "+first+"  ";
        profile_name.setText(pn);


        return  view;
    }

}
