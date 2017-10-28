package com.asi.hopeitapp.Network;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.asi.hopeitapp.R;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_VIBRATE;

public class PusherConfig {

    private Context context;

    public PusherConfig(Context context){
        this.context = context;
    }

    public void addPusher(){
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("e6245d3aaf9e8a803d2d", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                notification(eventName, data);
            }
        });

        pusher.connect();
    }

    private void notification(String eventName, String data){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.ic_heart)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(eventName)
                .setContentText(data)
                .setAutoCancel(true);

        NotificationManager mNotificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }
}
