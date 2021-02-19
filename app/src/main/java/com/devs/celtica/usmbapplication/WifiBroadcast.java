package com.devs.celtica.usmbapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by celtica on 12/09/18.
 */

public class WifiBroadcast extends BroadcastReceiver {

    public static boolean conected=true;

    @Override
    public void onReceive(Context context, Intent intent) {


            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if(netInfo != null && netInfo.isConnected()){
                Log.e("Test Internet","Kayen");
                //Toast.makeText(context,"Kayen INternet ..",Toast.LENGTH_LONG).show();
                //context.startService(new Intent(context,NewNotification.class));

            }else {
                Log.e("Test Internet","Makach ..");
                //Toast.makeText(context,"Makach  ..",Toast.LENGTH_LONG).show();
                // context.stopService(new Intent(context,NewNotification.class));
            }











        /*

        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            // Do something


        }

         */


        /*

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            // Do your work.

            // e.g. To check the Network Name or other info:
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            Log.e("Test Internet:","Kayen Internet ..");
            context.startService(new Intent(context,NewNotification.class));

        }else {
            Log.e("Test Internet:","No Internet ..");
            context.startService(new Intent(context,NewNotification.class));
        }
        */



    }
}
