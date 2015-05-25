package com.sleepy.smsApp;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import java.util.ArrayList;

public class MessageBox extends Activity implements OnClickListener {

	// GUI Widget
	Button btnSent, btnInbox, btnDraft;
	TextView lblMsg, lblNo;
	ListView lvMsg;

	// Cursor Adapter
	ListAdapter adapter;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mess_box);

        btnInbox = (Button) findViewById(R.id.btnInbox);
		btnInbox.setOnClickListener(this);
		btnSent = (Button) findViewById(R.id.btnSentBox);
		btnSent.setOnClickListener(this);
		btnDraft = (Button) findViewById(R.id.btnDraft);
		btnDraft.setOnClickListener(this);
		lvMsg = (ListView) findViewById(R.id.lvMsg);



	}


	@Override
	public void onClick(View v) {

		if (v == btnSent) {
			Uri sentURI = Uri.parse("content://sms/sent");
			String[] reqCols = new String[] { "_id", "address", "body" };
            Cursor smsCursor = this.getContentResolver().query(sentURI, null, null, null, null);
            ArrayList<Object> smsArr = new ArrayList<Object>();
            while (smsCursor.moveToNext()) {
                try {
                    String smsAddr = smsCursor.getString(smsCursor.getColumnIndex("address"));
                    String smsBody = smsCursor.getString(smsCursor.getColumnIndex("body"));
                    Log.e("MyLog", smsAddr+" = "+smsBody);
                    Object pairSms = new Pair<String, String>(smsAddr, smsBody);
                    smsArr.add(pairSms);
                }
                catch (Exception e) {
                }


            }
            adapter = new MySimpleArrayAdapter(this, smsArr.toArray());
            lvMsg.setAdapter(adapter);

		}

		if (v == btnDraft) {
			Uri draftURI = Uri.parse("content://sms/draft");
			String[] reqCols = new String[] { "_id", "address", "body" };
            Cursor smsCursor = this.getContentResolver().query(draftURI, null, null, null, null);
            ArrayList<Object> smsArr = new ArrayList<Object>();
            while (smsCursor.moveToNext()) {
                try {
                    String smsAddr = smsCursor.getString(smsCursor.getColumnIndex("address"));
                    String smsBody = smsCursor.getString(smsCursor.getColumnIndex("body"));
                    Log.e("MyLog", smsAddr+" = "+smsBody);
                    Object pairSms = new Pair<String, String>(smsAddr, smsBody);
                    smsArr.add(pairSms);
                }
                catch (Exception e) {
                }


            }
            adapter = new MySimpleArrayAdapter(this, smsArr.toArray());
            lvMsg.setAdapter(adapter);
		}



        if (v == btnInbox) {
            Uri inboxURI = Uri.parse("content://sms/inbox");
            String[] reqCols = new String[] { "_id", "address", "body" };
            //ContentResolver cr = getContentResolver();
            //Cursor c = cr.query(inboxURI, null, null, null, null);
            Cursor smsCursor = this.getContentResolver().query(inboxURI, null, null, null, null);
            ArrayList<Object> smsArr = new ArrayList<Object>();
            while (smsCursor.moveToNext()) {
                try {
                //int smsId = smsCursor.getColumnIndex("_id");
                String smsAddr = smsCursor.getString(smsCursor.getColumnIndex("address"));
                String smsBody = smsCursor.getString(smsCursor.getColumnIndex("body"));
                Log.e("MyLog", smsAddr+" = "+smsBody);
                Object pairSms = new Pair<String, String>(smsAddr, smsBody);
                smsArr.add(pairSms);
                }
                catch (Exception e) {
                }


            }
            adapter = new MySimpleArrayAdapter(this, smsArr.toArray());
            lvMsg.setAdapter(adapter);
        }



    }
}
