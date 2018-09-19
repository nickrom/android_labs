package android.nickrom.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView counter = (TextView) findViewById(R.id.textView2);
        final Button button = (Button) findViewById(R.id.button);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        counter.setText(String.valueOf(count++));
        textView.setText("CLICKER");
        editText.setHint("YOUR NAME");
        checkBox.setText("READY?");
        button.setText("STOP");

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("GO!");
                checkBox.setVisibility(View.INVISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText() == "GO!") {
                    counter.setText(String.valueOf(count++));
                    textView.setText("Hello, \n " + editText.getText() + "!");
                }
            }
        });
    }
}
