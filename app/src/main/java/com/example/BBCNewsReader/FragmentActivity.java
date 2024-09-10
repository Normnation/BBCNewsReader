package com.example.BBCNewsReader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentActivity extends Fragment {
    TextView titleVarTW;
    TextView descriptionVarTW;
    TextView linkVarTW;
    TextView dateVarTW;

    Bundle movingThingsOver;

    public FragmentActivity() {
        // Empty required constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movingThingsOver = getArguments();


        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        titleVarTW = view.findViewById(R.id.titleTW);
        descriptionVarTW = view.findViewById(R.id.descriptionTW);
        linkVarTW = view.findViewById(R.id.linkTW);
        dateVarTW = view.findViewById(R.id.dateTW);
        titleVarTW.setText(movingThingsOver.getString("titleVar"));
        descriptionVarTW.setText(movingThingsOver.getString("descriptionVar"));
        linkVarTW.setText(movingThingsOver.getString("linkVar"));
        dateVarTW.setText(movingThingsOver.getString("dateVar"));

        return view;

    }


}