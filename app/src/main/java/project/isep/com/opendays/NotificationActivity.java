package project.isep.com.opendays;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Tharanipathy PC on 14-11-2015.
 */
public class NotificationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView text = (TextView)findViewById(R.id.notificationText);
        text.setText(getIntent().getStringExtra("message"));
    }
}
