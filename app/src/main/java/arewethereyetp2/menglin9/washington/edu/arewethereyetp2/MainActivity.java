package arewethereyetp2.menglin9.washington.edu.arewethereyetp2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Button c1;
    Button c2;
    Button c3;
    EditText message;
    EditText tel;
    EditText time;
    Button start;
    PendingIntent alarmIntent = null;
    private AlarmManager manager;
    String alertMessage;

    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            alertMessage = tel.getText().toString() + ": " + message.getText().toString();
            sendSMS(tel.getText().toString(), message.getText().toString());
            Toast.makeText(MainActivity.this, alertMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = (Button) findViewById(R.id.clear);
        c2 = (Button) findViewById(R.id.clear2);
        c3 = (Button) findViewById(R.id.clear3);
        message = (EditText) findViewById(R.id.editText);
        tel = (EditText) findViewById(R.id.tel);
        time = (EditText) findViewById(R.id.time);
        start =(Button) findViewById(R.id.start);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("");
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel.setText("");
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setText("");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (start.getText().toString().equals("Start")) {

                    if ((!message.getText().toString().equals("")) && (!tel.getText().toString().equals("")) && (!time.getText().toString().equals(""))) {
                        //  Log.i("de", "" + message.getText().equals("") + "1" + tel.getText().equals("") + time.getText().equals(""));
                        start.setText("Stop");
                        int interval = Integer.parseInt(time.getText().toString());

                        registerReceiver(alarmReceiver, new IntentFilter("arewethere.menglin9.washington.edu.arewethere_p1"));
                        //registerReceiver(alarmReceiver, new IntentFilter());
                        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        Intent i = new Intent();
                        i.setAction("arewethere.menglin9.washington.edu.arewethere_p1");
                        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
                        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 5000, interval*60*1000, alarmIntent);
                    }
                } else if (start.getText().toString().equals("Stop")) {
                    start.setText("Start");
                    manager.cancel(alarmIntent);
                    alarmIntent.cancel();
                }
            }
        });
    }

    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
