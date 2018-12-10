package android.nickrom.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RetResActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ret_res);
    }

    public void sendResult(View v) {
        String result = ((EditText) findViewById(R.id.txtRandomResultText)).getText().toString();
        Intent i = new Intent();
        i.putExtra("result", result);
        setResult(RESULT_OK, i);
        finish();
    }
}
