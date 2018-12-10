package android.nickrom.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ExpResActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_res);
    }

    public void enterText(View v) {
        startActivityForResult(
                new Intent(ExpResActivity.this, RetResActivity.class),
                0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                TextView tvResult = (TextView)findViewById(R.id.txtDisplayResult);
                tvResult.setText(data.getStringExtra("result"));
                Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
