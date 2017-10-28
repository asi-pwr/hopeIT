package com.asi.hopeitapp.Messages;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.hopeitapp.Main.BaseFragment;
import com.asi.hopeitapp.Model.Message;
import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.R;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_VIBRATE;

public class MessagesFragment extends BaseFragment {

    @BindView(R.id.messagesRecyclerView)
    RecyclerView recyclerView;

    private int lastMessageSize = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.messages_fragment, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    protected void loadContent() {
        super.loadContent();
        loadRecyclerView();
    }

    private void loadRecyclerView(){
        List<Message> messages;

        try {
            messages = Patient.listAll(Message.class);
        }
        catch (Exception e){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        if(messages.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            return;
        }

        Collections.reverse(messages);

        MessagesAdapter adapter = new MessagesAdapter(messages, Glide.with(this), getContext());
        recyclerView.setAdapter(adapter);

        checkIfGotNew(messages);
    }

    private boolean checkIfGotNew(List<Message> messages){

        SharedPreferences settings = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        lastMessageSize = settings.getInt("MessageCount", 0);

        if (lastMessageSize != messages.size()){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "default")
                    .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                    .setSmallIcon(R.drawable.ic_heart)
                    .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),
                            R.mipmap.ic_launcher))
                    .setContentTitle("Nowa wiadomość od hopeIt")
                    .setContentText(messages.get(0).getTitle())
                    .setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(0, mBuilder.build());

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("MessageCount", messages.size());
            editor.apply();
        }

        return false;
    }
}
