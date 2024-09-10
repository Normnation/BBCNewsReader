package com.example.BBCNewsReader;


import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.BBCNewsReader.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
// Default comment.

public class ToolbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    Toolbar navToolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    AlertDialog.Builder alertDialogBuilder;
    ArrayList<FeedData> arrayListForFeed = new ArrayList<>();


    void setupToolbarAndNavigationDrawer() {
        navToolbar = findViewById(R.id.ToolbarID);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(navToolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,
                drawer, navToolbar, R.string.start_it_up, R.string.shut_it_down);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.FirstNavItem:
                Intent moveToFirstPage = new Intent(this, MainActivity.class);
                startActivity(moveToFirstPage);
                Toast.makeText(this, "This is the first page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.SecondNavItem:
                Intent moveToSecondActivity = new Intent(this, dbOperations_LV.class);
                startActivity(moveToSecondActivity);
                break;
            case R.id.ThirdNavItem:
                Intent moveToThirdActivity = new Intent(this, NewsPage.class);
                startActivity(moveToThirdActivity);
                break;


        }
        drawer.close();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.firstActivityIcon:
                Intent moveToFirstPage = new Intent(this, MainActivity.class);
                startActivity(moveToFirstPage);
                Toast.makeText(this, "This is the first page.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.secondActivityIcon:
                Toast.makeText(this, "You selected item 2", Toast.LENGTH_SHORT).show();
                Intent pageTwoMove = new Intent(this, dbOperations_LV.class);
                startActivity(pageTwoMove);
                break;
            case R.id.thirdActivityIcon:
                Toast.makeText(this, "You selected item 3", Toast.LENGTH_SHORT).show();
                Intent pageThreeMove = new Intent(this, NewsPage.class);
                startActivity(pageThreeMove);
                break;


            case R.id.pageDescriptionIcon:
                alertDialogBuilder.show();
                break;

        }
        return super.onOptionsItemSelected(item);


    }

}