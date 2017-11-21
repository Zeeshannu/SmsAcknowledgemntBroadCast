package com.example.zeeshan.smsacknowledgemntbroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Zeeshan on 11/21/2017.
 */

public class IncomingSms extends BroadcastReceiver {


    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();


    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();







                    Intent intent1 = new Intent(context,MainActivity.class);
                    PendingIntent pI = PendingIntent.getActivity(context,(int)System
                            .currentTimeMillis(),intent1,0);

                    //Step 1 - Create Notification Builder
                    Notification noti;

                    //Step 2 - Setting Notification Properties
                    noti = new Notification.Builder(context).setContentTitle("New " +
                            "SMS Recieved  " +
                            " from   "+senderNum)
                            .setContentText("Subject  "+message)
                            .setSmallIcon(R.drawable.abc)
                            .setContentIntent(pI)
                            .addAction(R.drawable.abc,"Call",pI)
                            .addAction(R.drawable.abc,"More",pI)
                            .addAction(R.drawable.abc,"Extra",pI).build();




                    NotificationManager notificationManager;
                    notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    noti.flags |= Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(0,noti);



                    //sending acknowledgeent
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(senderNum, null, "SMS Recieved", null, null);
                    Toast.makeText(context, "SMS sent.",
                            Toast.LENGTH_LONG).show();









                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }


    public void createNotification(View v)
    {
//this,Activity2.class
//        Intent in = new Intent();
//        PendingIntent pI = PendingIntent.getActivity(this,(int)System.currentTimeMillis(),in,0);
//        Notification noti = new Notification.Builder(this).setContentTitle("New Email from xyz@gmail.com")
//                .setContentText("Subject")
//                .setSmallIcon(R.drawable.abc)
//                .setContentIntent(pI)
//                .addAction(R.drawable.abc,"Call",pI)
//                .addAction(R.drawable.abc,"More",pI)
//                .addAction(R.drawable.abc,"Extra",pI).build();
//        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        noti.flags |= Notification.FLAG_AUTO_CANCEL;
//        notificationManager.notify(0,noti);

    }

}

