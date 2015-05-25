package com.sleepy.smsApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
          Bundle bundle = intent.getExtras();
          SmsMessage[] messages=null;
          String str = "";
          if(bundle!= null)
          {

              Object[] pdus = (Object[]) bundle.get("pdus");
              messages = new SmsMessage[pdus.length];
              for (int i =0; i<messages.length; i++){
                  messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                  str+="Message from" + messages[i].getOriginatingAddress();
                  str+=": ";
                  str+=messages[i].getMessageBody().toString();
                  str+="\n";

              }
             //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
              String msgTmp = messages[0].getMessageBody().toString();
              if (msgTmp.length()<8){
                  return;
              }
              String secTag = msgTmp.substring(0, 8);
              Log.e("MyLog", secTag);
              if (secTag.equals("-secTag-")) {
                  Intent broadcastIntent = new Intent();
                  Bundle extras = new Bundle();
                  broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                  extras.putString("number", messages[0].getOriginatingAddress());
                  extras.putString("mssg", msgTmp.substring(8));
                  broadcastIntent.putExtras(extras);
                  context.sendBroadcast(broadcastIntent);
              }
          }

      }

}
