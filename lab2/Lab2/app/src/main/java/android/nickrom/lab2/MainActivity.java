package android.nickrom.lab2;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    AsyncTask<URL, Void, String> requestInternet;
    URL url = null;
    TextView page = null;
    TextView title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        page = (TextView) findViewById(R.id.contentView);
        title = (TextView) findViewById(R.id.titleView);

        ImageView image = (ImageView) findViewById(R.id.imageView);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();

        String news = getString(R.string.news);

        textView.setText(news);
        image.setImageResource(R.mipmap.ic_launcher);

        Uri buildUri = Uri.parse("https://newsapi.org/v1/sources").buildUpon()
                .appendQueryParameter("language", configuration.getLocales().get(0).getLanguage())
                .build();
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        requestInternet = new RequestInternet().execute(url);
        reload();

    }

    int count = 0;

    public void reload() {

        try {
            JSONObject text = new JSONObject(requestInternet.get());
            JSONArray news = text.getJSONArray("sources");
            JSONObject firstNew = news.getJSONObject(count++);
            String content = firstNew.getString("description");
            title.setText(firstNew.getString("name"));
            page.setText(content);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
            /*Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);*/
            Toast.makeText(this, "Language was changed", Toast.LENGTH_LONG).show();

            return true;
        } else if (item_id == R.id.reload) {
            requestInternet = new RequestInternet().execute(url);
            reload();
            Toast.makeText(this, "Page was reloaded", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
