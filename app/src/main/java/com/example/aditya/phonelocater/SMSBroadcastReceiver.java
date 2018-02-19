package com.example.aditya.phonelocater;

/**
 * Created by aditya on 15/2/18.
 */
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.aditya.phonelocater.Database.DatabaseHandler;
import com.example.aditya.phonelocater.Database.Person;

import static com.example.aditya.phonelocater.PermissionManager.checkPermission;

public class SMSBroadcastReceiver extends BroadcastReceiver {

    //private DBHandler dbHandler;
    private LocationManager locationManager;
    private Context context;
    private String mR;
    private Person person;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;


        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            StringBuilder messageReceived = new StringBuilder();
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            String messageSender = messages[0].getDisplayOriginatingAddress();
            //dbHandler = new DBHandler(context);
            DatabaseHandler dbh=new DatabaseHandler(context);
            person=dbh.getPerson();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            try {
                for (int i = 0; i < messages.length; i++) {
                    messageReceived.append(messages[i].getMessageBody());
                }
               ProcessMessage(messageReceived.toString(), messageSender);
                //dbHandler.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void ProcessMessage(String message, String messageSender) {

        if (messageSender.length() >= 10) {
            if (messageSender.substring(0, 3).equals("+91")) {
                messageSender =  messageSender.substring(3,messageSender.length()).trim();
                messageSender = messageSender.replace(" ","");
            } else {
                messageSender= messageSender.replace(" ","");
            }
        }

        String contact;
        contact=person.cont;
        if ( contact.length() >= 10) {
            if ( contact.substring(0, 3).equals("+91")) {
                contact =   contact.substring(3, contact.length()).trim();
                contact =  contact.replace(" ","");
            } else {
                contact=  contact.replace(" ","");
            }
        }
        Log.e("TAG", "contact is: "+contact);
        Log.e("TAG", "contact.length() is: "+contact.length());


        // Format messageSender to retrieve Number from the TrustedDB

        Log.e("TAG", "messageSender is: "+messageSender);
        Log.e("TAG", "messageSender.length() is: "+messageSender.length());
        Log.e("TAG", "messageSender==contact is: "+(messageSender==contact));
        Log.e("TAG", "messageSender.equals(contact) is: "+(messageSender.equals(contact)));
        Log.e("TAG", "message.toLowerCase().contains(\"location\") is: "+(message.toLowerCase().contains("location")));
        Log.e("TAG", "Message is: "+message);

        if (messageSender.equals(contact) && message.toLowerCase().contains("location")) {
//        if(true) {
            Log.e("TAG", "All checks passed!");
            Location location = null;
            Log.e("TAG", "location = null");
            if (checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.e("TAG", "location is initialized");
                Log.e("TAG", "location != null is: "+(location!=null));
                SMSBroadcastReceiver smsbr=new SMSBroadcastReceiver();
                smsbr.sendLocationMessage(location, messageSender);
            }

        } else {
            Log.e("TAG", "Not a trusted contact or Keyword not found!");
        }
    }

    public String getMessage() {
        return mR;
    }


    public void sendLocationMessage(Location location, String messageSender) {
        Log.e("TAG", "Invoked");
        if (true) {
            SmsManager smsManager = SmsManager.getDefault();
            try {
                Log.e("TAG", location.getLatitude() + " " + location.getLongitude());
                String GPSCoordinatedToBeSent = "Current Coordinates is \nLatitude: " + Double.toString(location.getLatitude()) + "\n" +
                        "Longitude: " + Double.toString(location.getLongitude());
                smsManager.sendTextMessage(messageSender, null, Double.toString(location.getLatitude()) + ", " + Double.toString(location.getLongitude()), null, null);
                Log.e("TAG", "Message sent");
            }
            catch (java.lang.NullPointerException e) {
                
                Log.e("TAG", "Location Not Found");
            }
        }
    }

    public static boolean checkPermission(Context context,String permissionName) {
        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }
}

