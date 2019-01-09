package android.nickrom.lab5;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static ArrayList<String> static_titles = null;
    private static ArrayList<String> static_contents = null;
    private String TITLES = "titles";
    private String CONTENTS = "contents";
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> contents = new ArrayList<>();
    private static final int UNIQUE_LOADER_ID = 734;
    private ListAdapter adapter = new ListAdapter(titles, contents);
    private TextView logView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TITLES))
                titles.addAll(savedInstanceState.getStringArrayList(TITLES));
            if (savedInstanceState.containsKey(CONTENTS))
                contents.addAll(savedInstanceState.getStringArrayList(CONTENTS));
            Log.i("data", "Data was successfully loaded from bundle");
        }
        /*if (static_titles != null) {
            titles.addAll(static_titles);
            contents.addAll(static_contents);
            Log.i("data", "Data was successfully loaded from static lists");
        }*/

        TextView textView = (TextView) findViewById(R.id.textView);
        logView = (TextView) findViewById(R.id.logView);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        textView.setText(getString(R.string.news));
        image.setImageResource(R.mipmap.ic_launcher);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();

        Uri buildUri = Uri.parse("https://newsapi.org/v1/sources").buildUpon()
                .appendQueryParameter("language", configuration.getLocales().get(0).getLanguage())
                .build();

        Bundle asyncTaskLoaderParams = new Bundle();
        asyncTaskLoaderParams.putString("URL", buildUri.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(UNIQUE_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(UNIQUE_LOADER_ID, asyncTaskLoaderParams, this);
        } else {
            loaderManager.restartLoader(UNIQUE_LOADER_ID, null, this);
        }

        //recycler
        RecyclerView mItemsList = (RecyclerView) findViewById(R.id.rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mItemsList.setLayoutManager(linearLayoutManager);
        mItemsList.setAdapter(adapter);
        mItemsList.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logView.setText(logView.getText() + " onStart ");
        Log.i("app", "App is starting");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logView.setText(logView.getText() + " onResume ");
        Log.i("app", "App is resuming");
        Log.i("app", "<--------------------------------->");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logView.setText(logView.getText() + " onPause ");
        Log.i("app", "App is pausing");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logView.setText(logView.getText() + " onStop ");
        Log.i("app", "App is stoping");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logView.setText(logView.getText() + " onRestart ");
        Log.i("app", "App is restarting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logView.setText(logView.getText() + " onDestroy ");
        Log.i("app", "App is destroying");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList(TITLES, titles);
        outState.putStringArrayList(CONTENTS, contents);

        static_titles = titles;
        static_contents = contents;

        Log.i("data", "Data was successfully saved");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.action_settings) {
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            if (configuration.getLocales().get(0).getLanguage().equals("de"))
                configuration.setLocale(new Locale("en"));
            else
                configuration.setLocale(new Locale("de"));

            resources.updateConfiguration(configuration, this.getResources().getDisplayMetrics());
            recreate();
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            Toast.makeText(this, "Language was changed", Toast.LENGTH_LONG).show();

            return true;
        } else if (item_id == R.id.reload) {
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            Toast.makeText(this, "Page was reloaded", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                if (args == null)
                    return;
                forceLoad();
            }

            @Override
            public String loadInBackground() {

                HttpURLConnection urlConnection;
                try {
                    URL url = new URL(args.getString("URL"));
                    urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = urlConnection.getInputStream();

                        Scanner scanner = new Scanner(in);
                        scanner.useDelimiter("\\A");

                        boolean hasInput = scanner.hasNext();
                        if (hasInput) {
                            return scanner.next();
                        } else {
                            return null;
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            JSONObject text = new JSONObject(data);
            Log.i("data", "Data was successfully downloaded from net");
            JSONArray news = text.getJSONArray("sources");
            for (int i=0; i<news.length();i++) {
                titles.add(news.getJSONObject(i).getString("name"));
                contents.add(news.getJSONObject(i).getString("description"));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        logView.setText("");
    }
}
