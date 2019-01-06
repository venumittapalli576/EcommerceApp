package ecommerceapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import model.quiz.glarimy.com.ecommerceapp.R;

public class NoItems extends AppCompatActivity {

    TextView accountStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_items);

        accountStatus = findViewById(R.id.message);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        accountStatus.setText(b.getString("status"));

    }
}
