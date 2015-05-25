package com.sleepy.smsApp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MyActivity extends Activity {

    EditText msgTxt;
    EditText numTxt;
    IntentFilter intentFilter;
    TextView inTxt;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            TextView inTxt = new TextView (MyActivity.this);
            inTxt.setText("Sms from " + extras.getString("number").substring(7,11) + ":\n" + /*decrypt(*/extras.getString("mssg")/*, sNumber)*/);
            //Toast.makeText(getBaseContext(), "Position = " + decrypt(extras.getString("mssg"), sNumber), Toast.LENGTH_SHORT).show();
            inTxt.setTextColor(Color.parseColor("#FFFFFF"));
            inTxt.setTextSize(20);
            LinearLayout ll = (LinearLayout) findViewById(R.id.linearl);
            ll.addView(inTxt);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 10, 10, 10);
            inTxt.setLayoutParams(layoutParams);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        Button sendSMS = (Button) findViewById(R.id.sendBtn);
        Button logSMS = (Button) findViewById(R.id.logBtn);
        msgTxt = (EditText) findViewById(R.id.message);
        numTxt = (EditText) findViewById(R.id.numberTxt);

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myMsg = msgTxt.getText().toString();
                String theNumber = numTxt.getText().toString();
                TextView inTxt = new TextView (MyActivity.this);
                inTxt.setText("Sms to " + theNumber + ":\n"+myMsg);
                inTxt.setTextColor(Color.parseColor("#FFFFFF"));
                inTxt.setTextSize(20);
                LinearLayout ll = (LinearLayout) findViewById(R.id.linearl);
                //ll.setBackground();
                ll.addView(inTxt);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);
                inTxt.setLayoutParams(layoutParams);
                inTxt.setGravity(Gravity.RIGHT);

                //inTxt.append();

                //int sNumber = spinner.getSelectedItemPosition()+1;
               sendMsg(theNumber, myMsg/*encrypt(myMsg, sNumber)*/);
            }
        });
        logSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this, MessageBox.class);
                startActivity(intent);
            }
        });
    }

    protected void sendMsg(String theNumber, String myMsg) {
        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";

        PendingIntent sentPI= PendingIntent.getBroadcast(this, 0, new Intent((SENT)), 0);
        PendingIntent deliveredPI= PendingIntent.getBroadcast(this, 0, new Intent((DELIVERED)), 0);

        registerReceiver(new BroadcastReceiver()
         {
            public void onReceive(Context arg0, Intent arg1)
            {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        //Toast.makeText(MyActivity.this, "SMS sent", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        //Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                       // Toast.makeText(getBaseContext(), "No Servicce", Toast.LENGTH_LONG).show();
                        break;
                }
                }
            }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver()
        {
            public void onReceive(Context arg0, Intent arg1)
            {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        //Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        //Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(theNumber, null, /*"-secTag-"+*/myMsg,sentPI, deliveredPI);
    }



    @Override
    protected void onResume(){
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause(){
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

}