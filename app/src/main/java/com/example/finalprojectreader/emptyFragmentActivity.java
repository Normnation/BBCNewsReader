package com.example.finalprojectreader;

import android.os.Bundle;

public class emptyFragmentActivity extends ToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_fragment);

        Bundle dataToPass = getIntent().getExtras();

        FragmentActivity fragActivity = new FragmentActivity();

        fragActivity.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutID, fragActivity)
                .commit();

    }

}
