package com.asi.hopeitapp.Messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asi.hopeitapp.Model.Message;
import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.R;
import com.bumptech.glide.RequestManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {

    private List<Message> messages;
    private RequestManager glide;
    private Context context;

    MessagesAdapter(List<Message> messages, RequestManager glide, Context context) {
        this.messages = messages;
        this.glide = glide;
        this.context = context;
    }

    @Override
    public MessagesAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_message, parent, false);

        return new MessagesAdapter.Holder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.Holder holder, int position) {
        Message message = messages.get(position);

        holder.title.setText(message.getTitle());
        holder.message.setText(message.getContent());
        holder.time.setText(message.getCreatedAt().substring(0, 10));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_hist_time)
        TextView time;
        @BindView(R.id.notification_hist_title)
        TextView title;
        @BindView(R.id.notification_hist_message)
        TextView message;


        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}