package com.example.finalprojectreader;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class dbOperations_LV extends ToolbarActivity {

    View secondActivityContext;
    AlertDialog.Builder builder;


    SQLiteDatabase theDatabase;

    final static String XML_URL = "https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

    private ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        DatabaseHelper db = new DatabaseHelper(this);
        theDatabase = db.getWritableDatabase();
        ParsingNews req = new ParsingNews();
        req.execute(XML_URL);
        setupToolbarAndNavigationDrawer();
        alertDialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Welcome to the news feed!")
                .setMessage("This is the news feed page. " +
                        "Here you can select any of the icons up top or use the navigation drawer on the left to move around."
                        + "\n\n" + "You can also click any article you like and it will retrieve the details of that article, along with a link "
                        + "\n\n" + "If you would like to save an item to your favourites, you can use a long click on one of the articles and you'll be prompted to save it!"
                        + "\n\n" + "This will also save your selection to a database for future viewing.");


        builder = new AlertDialog.Builder(this);
        secondActivityContext = findViewById(R.id.secondActivityContext);


        Snackbar snackbar = Snackbar.make(secondActivityContext, "Welcome to the news feed", Snackbar.LENGTH_LONG)
                .setAction("Notification",


                        view -> Toast.makeText(dbOperations_LV.this, "This is your news feed", Toast.LENGTH_LONG).show());

        snackbar.show();

        ListView listViewVariable = findViewById(R.id.ListViewID);

        adapter = new ListviewAdapter(getApplicationContext(), arrayListForFeed);
        listViewVariable.setAdapter(adapter);
        listViewVariable.setOnItemLongClickListener((adapterView, view, position, l) -> {

            FeedData feedData = arrayListForFeed.get(position);

            AlertDialog.Builder newestBuilder = new AlertDialog.Builder(dbOperations_LV.this)
                    .setTitle("Save confirmation. ")
                    .setMessage("Would you like to save this? " + "\n" + feedData.getTitleVar())
                    .setPositiveButton("Yes", (dialogInterface, i) -> {

                        addSomethingToTheDatabase(feedData);
                        Toast.makeText(dbOperations_LV.this, "Saved information to database", Toast.LENGTH_LONG).show();

                    });
            newestBuilder.setNegativeButton("No", (dialogInterface, i) -> {

            });
            AlertDialog someNewDialog = newestBuilder.create();
            someNewDialog.show();

            {
                return true;


            }


        });

        listViewVariable.setOnItemClickListener((adapterView, view, position, l) -> {

            Bundle fullBundle = new Bundle();
            fullBundle.putString("titleVar", arrayListForFeed.get(position).getTitleVar());
            fullBundle.putString("descriptionVar", arrayListForFeed.get(position).getDescriptionVar());
            fullBundle.putString("linkVar", arrayListForFeed.get(position).getLinkVar());
            fullBundle.putString("dateVar", arrayListForFeed.get(position).getDateVar());


            Intent nextActivity = new Intent(dbOperations_LV.this, emptyFragmentActivity.class);
            nextActivity.putExtras(fullBundle);
            startActivity(nextActivity);
        });

    }


    public void addSomethingToTheDatabase(FeedData puttingValuesIn) {

        ContentValues newValuesToDatabase = new ContentValues();
        newValuesToDatabase.put(DatabaseHelper.COL_2, puttingValuesIn.getTitleVar());
        newValuesToDatabase.put(DatabaseHelper.COL_3, puttingValuesIn.getDescriptionVar());
        newValuesToDatabase.put(DatabaseHelper.COL_4, puttingValuesIn.getLinkVar());
        newValuesToDatabase.put(DatabaseHelper.COL_5, puttingValuesIn.getDateVar());


        theDatabase.insert(DatabaseHelper.TABLE, null, newValuesToDatabase);

    }


    public class ParsingNews extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType();

                FeedData newsItem = new FeedData();

                while (eventType != xpp.END_DOCUMENT) {
                    String parameterValue;
                    switch (eventType) {

                        case XmlPullParser.START_TAG:
                            parameterValue = xpp.getName();

                            if (parameterValue.equalsIgnoreCase("item")) {
                                newsItem = new FeedData();

                            } else if (parameterValue.equalsIgnoreCase("title")) {
                                newsItem.setTitleVar(xpp.nextText());
                            } else if (parameterValue.equalsIgnoreCase("description")) {
                                newsItem.setDescriptionVar(xpp.nextText());
                            } else if (parameterValue.equalsIgnoreCase("link")) {
                                newsItem.setLinkVar(xpp.nextText());
                            } else if (parameterValue.equalsIgnoreCase("pubdate")) {
                                newsItem.setDateVar(xpp.nextText());
                            }


                            break;

                        case XmlPullParser.END_TAG:
                            parameterValue = xpp.getName();
                            if (parameterValue.equalsIgnoreCase("item")) {
                                arrayListForFeed.add(newsItem);

                            }
                            break;
                    }

                    eventType = xpp.next();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return String.valueOf(arrayListForFeed);
        }


        public void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }


        public void onPostExecute(String strings) {
            super.onPostExecute(strings);
            Log.d("MainActivity", "Size is: " + arrayListForFeed.size());
            adapter.notifyDataSetChanged();
        }

    }

}