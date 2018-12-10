package android.nickrom.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        TextView textView = (TextView) findViewById(R.id.textView);

        Intent parenIntent = getIntent();
        if (parenIntent.hasExtra("name")) {
            String name = parenIntent.getStringExtra("name");
            textView.setText(name);
        }
    }
}