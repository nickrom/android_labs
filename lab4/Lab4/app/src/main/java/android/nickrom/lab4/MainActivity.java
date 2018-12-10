package android.nickrom.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView explicitIntent = (TextView) findViewById(R.id.expView);
        final TextView impicitIntent = (TextView) findViewById(R.id.impView);
        final TextView expResIntent = (TextView) findViewById(R.id.resView);

        explicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitActivityIntent = new Intent(MainActivity.this, ExplicitActivity.class);
                explicitActivityIntent.putExtra("name", R.string.name_exp);
                startActivity(explicitActivityIntent);
            }
        });

        impicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent implicitActivityIntent = new Intent(MainActivity.this, ImplicitActivity.class);
                startActivity(implicitActivityIntent);
            }
        });

        expResIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expResIntent = new Intent(MainActivity.this, ExpResActivity.class);
                startActivity(expResIntent);
            }
        });
    }
}
