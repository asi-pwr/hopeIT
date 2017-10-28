package com.asi.hopeitapp.Network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.asi.hopeitapp.Main.SplashActivity;
import com.asi.hopeitapp.R;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_VIBRATE;

public class PusherConfig {

    private Context context;
    private int notifId = 0;

    public PusherConfig(Context context){
        this.context = context;
    }

    public void addPusher(){
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("e6245d3aaf9e8a803d2d", options);

        Channel channel = pusher.subscribe("user-notifications-1");

        channel.bind("new-message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                notification(eventName, data);
            }
        });

        pusher.connect();
    }

    private void notification(String eventName, String data){
        PendingIntent contentIntent;
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra("notifDisp", 1);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        contentIntent = PendingIntent.getActivity(context, 0, intent , 0);

        String title = data.substring(data.indexOf("\"title\":\"") + 9, data.indexOf("\",\"message"));
        String message = data.substring(data.indexOf("\"message\":\"") + 8, data.indexOf("\"}"));

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.ic_heart)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentIntent(contentIntent)
                .setContentText(message)
                .setAutoCancel(true);

        NotificationManager mNotificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notifId++;
        mNotificationManager.notify(notifId, mBuilder.build());
    }
}
