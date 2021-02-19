package com.devs.celtica.usmbapplication;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by celtica on 10/09/18.
 */

public class NewNotification extends Service {
    public  static boolean searchNewPub=true;
    NotificationManager nm;
    boolean cnx=true;
    int id=1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service"," Started .. ");

        /*
                    try {
                        Log.e("TRavail:"," testing ..");
                        Thread.sleep(10000);

                        nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                         NotificationCompat.Builder nb=new NotificationCompat.Builder(Accueil.me)
                                .setContentTitle("Usmb Infos")
                                 .setContentText("Il y a des publication que vous n avez pa lu.")
                                 .setSmallIcon(R.drawable.ic_app);

                         nm.notify(id,nb.build());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     */






        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
