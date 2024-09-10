package com.example.finalprojectreader;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


public class MainActivity extends ToolbarActivity {

    Button nameConfirmation;
    EditText nameEditText;
    TextView nametextView;
    Button movingToNews;
    SharedPreferences sp;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nameConfirmation = findViewById(R.id.firstPageButton);
        nameEditText = findViewById(R.id.firstPageEditText);
        nametextView = findViewById(R.id.NameTextView);
        movingToNews = findViewById(R.id.moveToNews);

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 1;
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        sp = getSharedPreferences("PrefName", MODE_PRIVATE);

        nameConfirmation.setOnClickListener(view -> {
            String nameTextToMove = nameEditText.getText().toString();
            sp.edit().putString("editTextKey", nameTextToMove).apply();
            nametextView.setText("Welcome to the news reader, " + nameTextToMove + "\n" + "You can use the toolbar/Nav drawer above or select the button below to go to the news titles ");
            nameEditText.setText("");
        });
        nameEditText.setText(sp.getString("editTextKey", ""));
        movingToNews.setOnClickListener(view -> {
        Intent goToNews = new Intent(MainActivity.this, dbOperations_LV.class);
        startActivity(goToNews);
        });

        setupToolbarAndNavigationDrawer();
        alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Welcome to the main page!")
                .setMessage("This is the main page. Here you can select any of the icons up top or use the navigation drawer on the left to move around.  "
                        + "\n\n" + "You can select the button below to move to the current news feed or select the picture of the newspaper on the toolbar." + "\n\n" +
                        "Type in your name and click the button.");

    }


    }


