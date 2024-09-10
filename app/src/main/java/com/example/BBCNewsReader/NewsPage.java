package com.example.BBCNewsReader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NewsPage extends ToolbarActivity {
    private ArrayAdapter adapter;
    View thirdActivityContext;
    public ListView thirdActivityListview;
    AlertDialog.Builder builder;
    DatabaseHelper dbHelper;
    private SQLiteDatabase theDatabase;

    List<FeedData> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        thirdActivityListview = findViewById(R.id.ListViewID2);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        thirdActivityListview.setAdapter(adapter);

        setupToolbarAndNavigationDrawer();
        alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Welcome to the favourites page!")
                .setMessage("Welcome to your favourites list. "
                        + "\n\n" + "Here you can select and view any items you've saved to your list" +
                        " or you can select any of the icons up top or use the navigation drawer on the left to move around."
                        + "\n\n" + "If you'd like to remove something from your list, left click and hold down until the prompt appears.");

        thirdActivityContext = findViewById(R.id.thirdActivityContext);
        builder = new AlertDialog.Builder(this);
        Snackbar snackbar = Snackbar.make(thirdActivityContext, "Welcome to the favourites page", Snackbar.LENGTH_LONG)
                .setAction("Notification",

                        view -> Toast.makeText(NewsPage.this, "This is the favourites page", Toast.LENGTH_LONG).show());

        snackbar.show();

        dbHelper = new DatabaseHelper(getApplicationContext());
        theDatabase = dbHelper.getReadableDatabase();
        readFromDatabase();

        thirdActivityListview.setOnItemClickListener((adapterView, view, position, l) -> {

            Bundle fullBundle = new Bundle();
            fullBundle.putString("titleVar", favList.get(position).getTitleVar());
            fullBundle.putString("descriptionVar", favList.get(position).getDescriptionVar());
            fullBundle.putString("linkVar", favList.get(position).getLinkVar());
            fullBundle.putString("dateVar", favList.get(position).getDateVar());


            Intent nextActivity = new Intent(NewsPage.this, emptyFragmentActivity.class);
            nextActivity.putExtras(fullBundle);
            startActivity(nextActivity);
        });

        thirdActivityListview.setOnItemLongClickListener((adapterView, view, position, l) -> {
            FeedData feedData = favList.get(position);

            AlertDialog.Builder newestBuilder = new AlertDialog.Builder(NewsPage.this)
                    .setTitle("Delete confirmation. ")
                    .setMessage("Would you like to delete this? " + "\n" + feedData.getTitleVar())
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        favList.remove(position);
                        adapter.remove(adapter.getItem(position));
                        deleteFromDatabase(feedData);

                        Toast.makeText(NewsPage.this, "Deleted selection from database", Toast.LENGTH_LONG).show();

                    });
            newestBuilder.setNegativeButton("No", (dialogInterface, i) -> {

            });
            AlertDialog someNewDialog = newestBuilder.create();
            someNewDialog.show();

            return false;
        });

    }


    private void readFromDatabase() {
        Cursor c = theDatabase.query(DatabaseHelper.TABLE, null, null, null, null, null, null);

        int indexOfCol1 = c.getColumnIndex(DatabaseHelper.COL_1);
        int indexOfCol2 = c.getColumnIndex(DatabaseHelper.COL_2);
        int indexOfCol3 = c.getColumnIndex(DatabaseHelper.COL_3);
        int indexOfCol4 = c.getColumnIndex(DatabaseHelper.COL_4);
        int indexOfCol5 = c.getColumnIndex(DatabaseHelper.COL_5);

        while (c.moveToNext()) {
            FeedData feedData = new FeedData();

            int id = c.getInt(indexOfCol1);
            String title = c.getString(indexOfCol2);
            String description = c.getString(indexOfCol3);
            String link = c.getString(indexOfCol4);
            String publicationDate = c.getString(indexOfCol5);

            feedData.setTitleVar(title);
            feedData.setDescriptionVar(description);
            feedData.setLinkVar(link);
            feedData.setDateVar(publicationDate);
            feedData.setId(id);
            favList.add(feedData);
            adapter.add(title);
        }
        c.close();
        adapter.notifyDataSetChanged();
    }


    public void deleteFromDatabase(FeedData deletingStuff) {
        theDatabase.delete(DatabaseHelper.TABLE, DatabaseHelper.COL_1 + " = ?",
                new String[]{String.valueOf(deletingStuff.getId())});


    }

}

